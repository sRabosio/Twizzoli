package it.itsar.twizzoli.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import it.itsar.twizzoli.R;
import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.data.PostRepo;
import it.itsar.twizzoli.databinding.FragmentProfiloBinding;
import it.itsar.twizzoli.models.User;

public class Profilo extends Fragment {
    private FragmentProfiloBinding binding;
    private PostRepo postRepo = new PostRepo();
    private AppController controller = AppController.getInstance();
    private User loggedUser = null;
    private RecyclerView postList;

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
        binding.putUserName.setText(loggedUser.nickname);
        //   binding.putUserBio.setText(controller.getLoggedUser().bio);
        //   binding.putUserLocation.setText(controller.getLoggedUser().location);
        binding.FollowerCount.setText(String.valueOf(loggedUser.following.size()));
    }

    @Override
    public void onResume() {
        super.onResume();
        loggedUser = controller.getLoggedUser();
    }
}