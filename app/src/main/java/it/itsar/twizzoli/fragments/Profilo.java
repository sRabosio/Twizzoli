package it.itsar.twizzoli.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import it.itsar.twizzoli.adapters.AdapterPostList;
import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.data.PostRepo;
import it.itsar.twizzoli.data.UserRepo;
import it.itsar.twizzoli.databinding.FragmentProfiloBinding;
import it.itsar.twizzoli.models.Post;
import it.itsar.twizzoli.models.User;

public class Profilo extends Fragment {
    private FragmentProfiloBinding binding;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference users = db.collection("users");
    private final CollectionReference postRef = db.collection("post");
    private final AppController controller = AppController.getInstance();
    private User loggedUser = null;
    private User userProfile = null;
    private ObservableInt followerCount = null;
    private AdapterPostList adapterPostList = null;

    public Profilo() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfiloBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loggedUser = controller.getLoggedUser();
        Bundle args = getArguments();
        if (args == null) return;
        userProfile = (User) args.getSerializable("user");
        adapterPostList = (AdapterPostList) args.getSerializable("adapter");
        if (userProfile == null) return;
        if (adapterPostList == null) adapterPostList = new AdapterPostList();

        followerCount = new ObservableInt(userProfile.getFollowers().size());
        binding.setProfileUser(userProfile);
        binding.setFollowerCount(followerCount);


        setAdapterData();
        binding.postList.setAdapter(adapterPostList);
        binding.avatarIv.setImageResource(userProfile.iconId);

        followButton();
        refreshButton();
    }

    private void setAdapterData() {
        postRef
                .whereEqualTo("creator", userProfile.username)
                .whereEqualTo("parent", false)
                .get().addOnSuccessListener(snap->{
                    List<Post> result = snap.toObjects(Post.class);
                    adapterPostList.getPostList().clear();
                    adapterPostList.getPostList().addAll(result);
                    adapterPostList.notifyDataSetChanged();
                });
    }

    private void followButton() {

        binding.buttonFollow.setOnClickListener(v -> {
            if (isFollowing()) {
                loggedUser.getFollowing().remove(userProfile.username);
                userProfile.getFollowers().remove(loggedUser.username);
                followerCount.set(followerCount.get() - 1);
            } else {
                loggedUser.getFollowing().add(userProfile.username);
                userProfile.getFollowers().add(loggedUser.username);
                followerCount.set(followerCount.get() + 1);
            }
            users.document(loggedUser.username).set(loggedUser);
            users.document(userProfile.username).set(userProfile);
            refreshButton();
        });
    }

    private boolean isFollowing() {
        return loggedUser.getFollowing().contains(userProfile.username);
    }

    private void refreshButton() {
        loggedUser = controller.getLoggedUser();
        String buttonText;
        if (isFollowing())
            buttonText = "following";
        else
            buttonText = "follow";
        if (userProfile.username.equals(loggedUser.username))
            binding.buttonFollow.setVisibility(View.GONE);
        binding.buttonFollow.setText(buttonText);
    }
}