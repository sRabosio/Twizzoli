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
    private final AppController controller = AppController.getInstance();
    private User loggedUser = null;
    private User userProfile = null;
    private RecyclerView postList;
    private ObservableInt followerCount = null;

    public Profilo() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfiloBinding.inflate(inflater, container, false);
        //auth database
        //binding con dati presi dal database
        //chiamate database per ottenere dati

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loggedUser = controller.getLoggedUser();
        Bundle args = getArguments();
        if (args == null) return;
        userProfile = (User) args.getSerializable("user");
        AdapterPostList adapterPostList = (AdapterPostList) args.getSerializable("adapter");
        if (userProfile == null) return;

        followerCount = new ObservableInt(userProfile.getFollowers().size());
        binding.setProfileUser(userProfile);
        binding.setFollowerCount(followerCount);
        adapterPostList.getPostList().clear();
        binding.postList.setAdapter(adapterPostList);
        binding.avatarIv.setImageResource(userProfile.iconId);

        followButton();
        refreshButton();
    }

    private void followButton() {

        binding.buttonFollow.setOnClickListener(v -> {
            if (isFollowing()) {
                loggedUser.getFollowing().remove(userProfile.path);
                userProfile.getFollowers().remove(loggedUser.path);
                followerCount.set(followerCount.get() - 1);
            } else {
                loggedUser.getFollowing().add(userProfile.path);
                userProfile.getFollowers().add(loggedUser.path);
                followerCount.set(followerCount.get() + 1);
            }
            users.document(loggedUser.path).set(loggedUser);
            users.document(userProfile.path).set(userProfile);
            refreshButton();
        });
    }

    private boolean isFollowing() {
        return loggedUser.getFollowing().contains(userProfile.path);
    }

    private void refreshButton() {
        loggedUser = controller.getLoggedUser();
        String buttonText;
        if (isFollowing())
            buttonText = "following";
        else
            buttonText = "follow";
        if (userProfile.path.equals(loggedUser.path))
            binding.buttonFollow.setVisibility(View.GONE);
        binding.buttonFollow.setText(buttonText);
    }
}