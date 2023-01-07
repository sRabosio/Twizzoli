package it.itsar.twizzoli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import it.itsar.twizzoli.data.CommentRepo;
import it.itsar.twizzoli.data.ResultHandler;
import it.itsar.twizzoli.data.UserRepo;
import it.itsar.twizzoli.databinding.ActivityPostBinding;
import it.itsar.twizzoli.fragments.PostFragment;
import it.itsar.twizzoli.models.Post;
import it.itsar.twizzoli.models.User;

public class PostActivity extends AppCompatActivity {

    private CommentRepo commentRepo = new CommentRepo();
    private UserRepo userRepo = new UserRepo();
    private Post post = null;
    private User creator = null;
    private ActivityPostBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        post = (Post) getIntent().getSerializableExtra("post");
        if (post == null) {
            Log.d("ERROR IN POST ACTIVITY", "NO POST FOUND");
            finish();
            return;
        }
        userRepo.getElementById(post.creator, new ResultHandler() {
            @Override
            public <T> void success(T result) {
                creator = (User) result;
                bindPost();
            }

            @Override
            public void failed(int code, String message) {
                Log.d("ERROR IN POST ACTVITY", "NO CREATOR FOUND");
                finish();
            }
        });

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post);
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