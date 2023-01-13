package it.itsar.twizzoli.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import it.itsar.twizzoli.ProfileActivity;
import it.itsar.twizzoli.databinding.FragmentPostBinding;
import it.itsar.twizzoli.models.Post;
import it.itsar.twizzoli.models.User;

public class PostFragment extends Fragment {

    private FragmentPostBinding binding;
    private Post post = null;
    private User creator = null;
    private String postId;
    private final CollectionReference postsRef = FirebaseFirestore.getInstance().collection("post");

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPostBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments() == null) return;
        postId = getArguments().getString("postId");
        creator = (User) getArguments().getSerializable("creator");
        if(creator == null || postId == null) return;
        binding.setUser(creator);
        binding.setPost(post);


        postsRef.document(postId).get().addOnSuccessListener(snap->{
            Post result = snap.toObject(Post.class);
            if(result == null) return;
            post = result;
        });

        infoContainer();
    }

    private void infoContainer() {
        binding.infoContainer.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            intent.putExtra("profileUser", creator);
            startActivity(intent);
        });
    }
}