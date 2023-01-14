package it.itsar.twizzoli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
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
    private String commentId = null;
    private User creator;
    private final CollectionReference commRef = FirebaseFirestore.getInstance().collection("post");
    private final AdapterCommentList adapterCommentList = new AdapterCommentList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment);
        loggedUser = controller.getLoggedUser();
        if (loggedUser == null) finish();

        commentId = getIntent().getStringExtra("commentId");
        creator = (User) getIntent().getSerializableExtra("creator");
        binding.subcomments.setAdapter(adapterCommentList);

        if (creator == null || commentId == null) return;
        mainComment();
        commentList();
        newcomment();
        switchFragment(SearchBarFragment.class, R.id.appbar, null);

    }

    private void newcomment() {
        if (commentId == null) finish();
        Bundle args = new Bundle();
        args.putString("parent", commentId);
        args.putSerializable("adapter", adapterCommentList);
        switchFragment(NewCommentFragment.class, R.id.newcomment, args);


    }

    private void commentList() {
        commRef.whereEqualTo("parent", commentId)
                .get().addOnSuccessListener(snap -> {
                    List<DocumentSnapshot> comments = snap.getDocuments();
                    comments.forEach(e -> adapterCommentList.getComments().add(e.getId()));
                    adapterCommentList.notifyDataSetChanged();
                });
    }

    private void mainComment() {
        commRef.document(commentId).get()
                .addOnSuccessListener(snap->{
                    Comment comment = snap.toObject(Comment.class);
                    if(comment == null) return;
                    Bundle args = new Bundle();
                    args.putSerializable("comment", comment);
                    args.putSerializable("creator", creator);
                    switchFragment(CommentFragment.class, R.id.main_comment, args);
                });
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