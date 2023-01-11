package it.itsar.twizzoli.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import it.itsar.twizzoli.R;
import it.itsar.twizzoli.adapters.AdapterPostList;
import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.data.PostRepo;
import it.itsar.twizzoli.models.Post;
import it.itsar.twizzoli.models.User;


public class Feed extends Fragment {

    private final PostRepo postRepo = new PostRepo();
    private final AppController controller = AppController.getInstance();
    private User loggedUser = null;
    private RecyclerView postList;
    private AdapterPostList adapterPostList = null;

    public Feed() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loggedUser = controller.getLoggedUser();

    }


    private void arguments(){
        if(getArguments() == null) return;
        adapterPostList = (AdapterPostList) getArguments().getSerializable("adapter");
        if(adapterPostList == null) return;
        adapterPostList.getPostList().clear();
        adapterPostList.getPostList().addAll(getFeed());
        postList.setAdapter(adapterPostList);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(loggedUser == null) return;
        postList = view.findViewById(R.id.post_list);
        arguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }


    private ArrayList<Post> getFeed() {
        if(loggedUser == null) return new ArrayList<>();
        return postRepo.userFeed(controller.getLoggedUser());
    }
}