package it.itsar.twizzoli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.List;

import it.itsar.twizzoli.adapters.AdapterCommentList;
import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.databinding.ActivityCommentBinding;
import it.itsar.twizzoli.fragments.CommentFragment;
import it.itsar.twizzoli.fragments.NewCommentFragment;
import it.itsar.twizzoli.fragments.SearchBarFragment;
import it.itsar.twizzoli.models.Comment;
import it.itsar.twizzoli.models.User;

public class CommentActivity extends AppCompatActivity {

    private final AppController controller = AppController.getInstance();
    private User loggedUser = null;
    private ActivityCommentBinding binding;
    private Comment comment = null;
    private User creator;
    private ListenerRegistration commListener;
    private final CollectionReference commRef = FirebaseFirestore.getInstance().collection("post");
    private final AdapterCommentList adapterCommentList = new AdapterCommentList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment);
        loggedUser = controller.getLoggedUser();
        if (loggedUser == null) finish();

        comment = (Comment) getIntent().getSerializableExtra("comment");
        creator = (User) getIntent().getSerializableExtra("creator");
        binding.subcomments.setAdapter(adapterCommentList);

        if (creator == null || comment == null) return;
        mainComment();
        commentList();
        newcomment();
        switchFragment(SearchBarFragment.class, R.id.appbar, null);

    }

    private void newcomment() {
        if (comment == null) finish();
        Bundle args = new Bundle();
        args.putString("parentId", comment.id);
        args.putSerializable("adapter", adapterCommentList);
        switchFragment(NewCommentFragment.class, R.id.newcomment, args);


    }

    //TODO: sort by date
    private void commentList() {
        commListener = commRef.whereEqualTo("parent", comment.id)
                .orderBy("creationDate", Query.Direction.DESCENDING)
                .limit(50)
                .addSnapshotListener((snap, err) -> {
                    if(err != null || snap == null) return;
                    adapterCommentList.getComments().clear();
                    List<Comment> comments = snap.toObjects(Comment.class);
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

    private void mainComment() {
        if (comment == null || creator == null) return;
        Bundle args = new Bundle();
        args.putSerializable("comment", comment);
        args.putSerializable("creator", creator);
        switchFragment(CommentFragment.class, R.id.main_comment, args);
    }

    private <T extends Fragment> void switchFragment(Class<T> fragClass, int fragLayout, Bundle fragArgs) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(fragLayout, fragClass, fragArgs)
                .addToBackStack(fragClass.getName())
                .setReorderingAllowed(true)
                .commit();
    }
}