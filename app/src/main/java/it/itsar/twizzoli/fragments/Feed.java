package it.itsar.twizzoli.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import it.itsar.twizzoli.R;
import it.itsar.twizzoli.adapters.AdapterPostList;
import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.models.Post;
import it.itsar.twizzoli.models.User;


public class Feed extends Fragment {

    private final AppController controller = AppController.getInstance();
    private User loggedUser = null;
    private RecyclerView postList;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference posts = db.collection("post");
    private AdapterPostList adapterPostList = null;
    private Snackbar postsSnack;
    private ListenerRegistration postReg;


    public Feed() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loggedUser = controller.getLoggedUser();


    }


    private void arguments() {
        if (getArguments() == null) return;
        adapterPostList = (AdapterPostList) getArguments().getSerializable("adapter");
        if (adapterPostList == null) return;
        getFeed();
        postList.setAdapter(adapterPostList);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (loggedUser == null) return;
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
        postReg = posts
                .whereIn("creator", feedQueryParam)
                .whereEqualTo("parent", false)
                .addSnapshotListener((value, error) -> {
                    if(value == null) return;
                    List<Post> result = value.toObjects(Post.class);
                    if(result.isEmpty()) return;

                    result.sort((e1, e2) -> {
                        if (e1 == null || e2 == null) return 0;

                        return (int) (e1.creationDate.getTime() - e2.creationDate.getTime()) * -1;
                    });
                    if (result.size() > 50)
                        result = result.subList(0, 50);
                    adapterPostList.getPostList().clear();
                    adapterPostList.getPostList().addAll(result);
                    adapterPostList.notifyDataSetChanged();
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(postsSnack != null)
            postsSnack.dismiss();
        if(postReg != null)
            postReg.remove();
    }
}