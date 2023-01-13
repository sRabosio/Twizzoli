package it.itsar.twizzoli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import it.itsar.twizzoli.adapters.AdapterCommentList;
import it.itsar.twizzoli.data.CommentRepo;
import it.itsar.twizzoli.data.ResultHandler;
import it.itsar.twizzoli.data.UserRepo;
import it.itsar.twizzoli.databinding.ActivityPostBinding;
import it.itsar.twizzoli.fragments.NewCommentFragment;
import it.itsar.twizzoli.fragments.PostFragment;
import it.itsar.twizzoli.fragments.SearchBarFragment;
import it.itsar.twizzoli.models.Comment;
import it.itsar.twizzoli.models.Post;
import it.itsar.twizzoli.models.User;

public class PostActivity extends AppCompatActivity {

    private final CommentRepo commentRepo = new CommentRepo();
    private final UserRepo userRepo = new UserRepo();
    private ArrayList<Comment> comments;
    private Post post = null;
    private User creator = null;
    private ActivityPostBinding binding;
    private final AdapterCommentList adapterCommentList = new AdapterCommentList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        post = (Post) getIntent().getSerializableExtra("post");
        if(!checkPost()) return;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post);

        fetchCreator();
        fetchComments();
        Bundle newPostArgs = new Bundle();
        newPostArgs.putSerializable("fatherPost", 0);
        newPostArgs.putSerializable("adapter", adapterCommentList);
        switchFragment(NewCommentFragment.class, R.id.newcomment, newPostArgs);
        switchFragment(SearchBarFragment.class, R.id.appbar, null);
    }


    private boolean checkPost(){
        if(post == null)
        {
            Log.d("ERROR IN POST ACTIVITY", "NO POST FOUND");
            finish();
            return false;
        }
        return true;
    }

    private void fetchComments(){
        if(comments == null) return;
        adapterCommentList.getComments().addAll(comments);
        binding.comments.setAdapter(adapterCommentList);
    }

    private void fetchCreator(){
       /* userRepo.getElementById(post.creatorPath, new ResultHandler() {
            @Override
            public <T> void success(T result) {
                creatorPath = (User) result;
                bindPost();
            }

            @Override
            public void failed(int code, String message) {
                Log.d("ERROR IN POST ACTVITY", "NO CREATOR FOUND");
                finish();
            }
        });*/
    }

    private void bindPost() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("post", post);
        bundle.putSerializable("creatorPath", creator);
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