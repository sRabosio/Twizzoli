package it.itsar.twizzoli.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

import it.itsar.twizzoli.Homepage;
import it.itsar.twizzoli.adapters.AdapterPostList;
import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.data.PostRepo;
import it.itsar.twizzoli.databinding.FragmentNewPostBinding;
import it.itsar.twizzoli.models.Post;
import it.itsar.twizzoli.models.User;

public class NewPost extends Fragment {

    private User loggedUser = null;
    private final AppController controller = AppController.getInstance();
    private FragmentNewPostBinding binding;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference posts = db.collection("post");
    private AdapterPostList adapterPostList = null;

    public NewPost() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewPostBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void submit() {
        binding.newpostSubmit.setOnClickListener(view -> {
            String title = binding.newpostTitle.getText().toString();
            String content = binding.newpostContent.getText().toString();
            if (isValid()) return;
            loggedUser = controller.getLoggedUser();

            Post post = new Post(title, content, loggedUser.path);
            post.creationDate = new Date();
            post.username = loggedUser.username;
            posts.add(post)
                    .addOnSuccessListener(succ->{
                        Snackbar.make(view, "Post created succesfully", 3000)
                                .show();
                    })
                    .addOnFailureListener(e->{
                        Snackbar.make(view, "Error during post creation", 3000)
                                .show();
                    });

            binding.newpostTitle.setText("");
            binding.newpostContent.setText("");
            adapterPostList.getPostList().add(post);
            adapterPostList.notifyDataSetChanged();
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if(getArguments() == null) return;
        adapterPostList = (AdapterPostList) getArguments().getSerializable("adapter");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private boolean isValid(){
        String title = binding.newpostTitle.getText().toString();
        String content = binding.newpostContent.getText().toString();
        return title.isEmpty() || content.isEmpty();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        submit();
    }
}