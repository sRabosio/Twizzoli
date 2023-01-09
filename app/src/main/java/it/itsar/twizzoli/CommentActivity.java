package it.itsar.twizzoli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import it.itsar.twizzoli.adapters.AdapterCommentList;
import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.data.CommentRepo;
import it.itsar.twizzoli.data.ResultHandler;
import it.itsar.twizzoli.data.UserRepo;
import it.itsar.twizzoli.databinding.ActivityCommentBinding;
import it.itsar.twizzoli.fragments.CommentFragment;
import it.itsar.twizzoli.fragments.NewCommentFragment;
import it.itsar.twizzoli.models.Comment;
import it.itsar.twizzoli.models.User;

public class CommentActivity extends AppCompatActivity {

    private final AppController controller = AppController.getInstance();
    private User loggedUser = null;
    private ActivityCommentBinding binding;
    private Comment mainComment = null;
    private User creator;
    private ArrayList<Comment> subComments;
    private final CommentRepo commentRepo = new CommentRepo();
    private final UserRepo userRepo = new UserRepo();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment);
        loggedUser = controller.getLoggedUser();
        if(loggedUser == null) finish();
        mainComment = (Comment) getIntent().getSerializableExtra("comment");
        fetchUser();
        mainComment();
        commentList();
        newcomment();

    }

    private void newcomment(){
        if(mainComment == null) finish();
        Bundle args = new Bundle();
        args.putSerializable("fatherId", mainComment.id);
        switchFragment(NewCommentFragment.class, R.id.newcomment, args);


    }

    private void fetchUser(){
        userRepo.getElementById(mainComment.creator, new ResultHandler() {
            @Override
            public <T> void success(T result) {
                creator = (User) result;
            }

            @Override
            public void failed(int code, String message) {
                Log.d("ERROR IN COMMENT ACTIVITY", "No creator found!");
                finish();
            }
        });
    }

    private void commentList(){
        subComments = commentRepo.getContentChildren(mainComment.id);
        binding.subcomments.setAdapter(
                new AdapterCommentList(subComments.toArray(new Comment[0]))
        );
    }

    private void mainComment(){
        Bundle args = new Bundle();
        args.putSerializable("comment", mainComment);
        args.putSerializable("creator", creator);
        switchFragment(CommentFragment.class, R.id.main_comment, args);
    }

    private <T extends Fragment> void switchFragment(Class<T> fragClass, int fragLayout, Bundle fragArgs){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(fragLayout, fragClass, fragArgs)
                .addToBackStack(fragClass.getName())
                .setReorderingAllowed(true)
                .commit();
    }
}