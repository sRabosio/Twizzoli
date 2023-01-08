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
import android.widget.ImageView;
import android.widget.TextView;

import it.itsar.twizzoli.R;
import it.itsar.twizzoli.adapters.AdapterPostList;
import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.data.PostRepo;
import it.itsar.twizzoli.databinding.FragmentProfiloBinding;
import it.itsar.twizzoli.models.Post;
import it.itsar.twizzoli.models.User;

public class Profilo extends Fragment {
    private FragmentProfiloBinding binding;
    private final PostRepo postRepo = new PostRepo();
    private final AppController controller = AppController.getInstance();
    private User loggedUser = null;
    private User userProfile = null;
    private RecyclerView postList;
    private ImageView avatar;
    private TextView nomeprofilo, followerprofilo;

    public Profilo(){

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
        if(args == null) return;
        userProfile = (User) args.getSerializable("user");
        if(userProfile == null) return;

        binding.nameprofile.setText(userProfile.nickname);
        String followerText = userProfile.followers.size() + " followers";
        binding.followerprofile.setText(followerText);
        binding.postList.setAdapter(new AdapterPostList(
            postRepo.searchByUser(userProfile.id).toArray(new Post[0])
        ));
    }

    @Override
    public void onResume() {
        super.onResume();
        loggedUser = controller.getLoggedUser();
        binding.postList.setAdapter(new AdapterPostList(
                postRepo.searchByUser(userProfile.id).toArray(new Post[0])
        ));
    }
}