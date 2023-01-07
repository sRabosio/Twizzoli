package it.itsar.twizzoli.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import it.itsar.twizzoli.R;
import it.itsar.twizzoli.adapters.PostListAdapter;
import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.data.PostRepo;
import it.itsar.twizzoli.models.Post;
import it.itsar.twizzoli.models.User;


public class Feed extends Fragment {

    private PostRepo postRepo = new PostRepo();
    private AppController controller = AppController.getInstance();
    private User loggedUser = null;
    private RecyclerView postList;

    public Feed() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loggedUser = controller.getLoggedUser();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(loggedUser == null) return;
        postList = view.findViewById(R.id.post_list);
        refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(loggedUser == null) return;
        refresh();
    }

    public void refresh(){
        //TODO: use feed instead of sample
        postList.setAdapter(new PostListAdapter(sample()));
    }

    private Post[] sample(){
        return new Post[]{
            new Post("title", "contenuto", 0),
                new Post("title", "contenuto", 0),
                new Post("title", "contenuto", 0),
                new Post("title", "contenuto", 0),
        };
    }

    //TODO: use this
    private it.itsar.twizzoli.models.Post[] getFeed() {
        if(loggedUser == null) return null;
        return postRepo.userFeed(controller.getLoggedUser()).toArray(new Post[0]);
    }
}