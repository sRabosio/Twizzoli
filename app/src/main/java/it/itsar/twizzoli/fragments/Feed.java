package it.itsar.twizzoli.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import it.itsar.twizzoli.R;
import it.itsar.twizzoli.adapters.AdapterPostList;
import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.models.User;


public class Feed extends Fragment {

    private final AppController controller = AppController.getInstance();
    private User loggedUser = null;
    private RecyclerView postList;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference posts = db.collection("post");
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
        getFeed();
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


    private void getFeed() {
        List<String> feedQueryParam = new ArrayList<>(loggedUser.getFollowing());
        feedQueryParam.add(loggedUser.username);
        posts
                .whereIn("creator", feedQueryParam)
                .whereEqualTo("parent", false)
                //TODO: .orderBy("creation_date", Query.Direction.DESCENDING)
                .limit(50)
                .get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) return;
                    task.getResult().getDocuments().get(0).getId();
                    List<DocumentSnapshot> result = task.getResult().getDocuments();
                    adapterPostList.getPostList().clear();
                    result.forEach(e->adapterPostList.getPostList().add(e.getId()));
                    adapterPostList.notifyDataSetChanged();
                });
    }
}