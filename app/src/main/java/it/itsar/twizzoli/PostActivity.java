package it.itsar.twizzoli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.List;

import it.itsar.twizzoli.adapters.AdapterCommentList;
import it.itsar.twizzoli.databinding.ActivityPostBinding;
import it.itsar.twizzoli.fragments.NewCommentFragment;
import it.itsar.twizzoli.fragments.PostFragment;
import it.itsar.twizzoli.fragments.SearchBarFragment;
import it.itsar.twizzoli.models.Comment;
import it.itsar.twizzoli.models.Post;
import it.itsar.twizzoli.models.User;

public class PostActivity extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference postCollectionRef = db.collection("post");
    private Post post = null;
    private User creator = null;
    private ListenerRegistration commListener;
    private ActivityPostBinding binding;
    private final AdapterCommentList adapterCommentList = new AdapterCommentList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        post = (Post) getIntent().getSerializableExtra("post");
        creator = (User) getIntent().getSerializableExtra("creator");
        if(!checkPost()) return;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post);


        binding.comments.setAdapter(adapterCommentList);
        fetchComments();
        Bundle newPostArgs = new Bundle();
        newPostArgs.putString("parentId", post.id);
        newPostArgs.putSerializable("adapter", adapterCommentList);
        switchFragment(NewCommentFragment.class, R.id.newcomment, newPostArgs);
        switchFragment(SearchBarFragment.class, R.id.appbar, null);
        bindPost();
    }


    private boolean checkPost(){
        if(post == null || creator == null)
        {
            Log.d("ERROR IN POST ACTIVITY", "NO POST FOUND");
            finish();
            return false;
        }
        return true;
    }

    private void fetchComments(){
        commListener = postCollectionRef.whereEqualTo("parent", post.id)
                .orderBy("creationDate", Query.Direction.DESCENDING)
                .limit(50)
                .addSnapshotListener((snap, err)->{
                    if(err != null || snap == null) return;
                    List<Comment> comments = snap.toObjects(Comment.class);
                    adapterCommentList.getComments().clear();
                    adapterCommentList.getComments().addAll(comments);
                    adapterCommentList.notifyDataSetChanged();
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(commListener != null)
            commListener.remove();
    }

    private void bindPost() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("post", post);
        bundle.putSerializable("creator", creator);
        switchFragment(PostFragment.class, R.id.post, bundle);
    }

    private <T extends Fragment> void switchFragment(Class<T> fragmentClass, int layoutId, Bundle bundle){
        getSupportFragmentManager().beginTransaction()
                .replace(layoutId, fragmentClass, bundle)
                .setReorderingAllowed(true)
                .addToBackStack(fragmentClass.getName())
                .commit();
    }
}