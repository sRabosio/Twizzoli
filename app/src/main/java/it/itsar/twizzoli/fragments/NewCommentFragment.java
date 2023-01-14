package it.itsar.twizzoli.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

import it.itsar.twizzoli.CommentActivity;
import it.itsar.twizzoli.adapters.AdapterCommentList;
import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.data.CommentRepo;
import it.itsar.twizzoli.databinding.FragmentNewCommentBinding;
import it.itsar.twizzoli.models.Comment;
import it.itsar.twizzoli.models.Content;
import it.itsar.twizzoli.models.Post;
import it.itsar.twizzoli.models.User;

public class NewCommentFragment extends Fragment {

    private FragmentNewCommentBinding binding;
    private User loggedUser = null;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference comments = db.collection("post");
    private String parentId = null;
    private AdapterCommentList commentAdapter = null;

    public NewCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loggedUser = AppController.getInstance().getLoggedUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewCommentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args == null) return;
        commentAdapter = (AdapterCommentList) args.getSerializable("adapter");
        parentId = args.getString("parentId");

        Button sendButton = binding.buttonSend;

        binding.commentText.setOnFocusChangeListener((v, hasFocus) -> {
            sendButton.setVisibility(
                    hasFocus ? View.VISIBLE : View.GONE);
        });

        binding.commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                sendButton.setEnabled(!text.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sendButton.setOnClickListener(v -> {
            if (loggedUser == null) return;
            String commentText = binding.commentText.getText().toString();

            Comment comment = new Comment(commentText, loggedUser.username);
            comment.parent = parentId;
            comment.creationDate = new Date();
            binding.commentText.setText("");
            binding.commentText.clearFocus();
            comments.add(comment).addOnSuccessListener(snap->{
                commentAdapter.getComments().add(snap.getId());
                commentAdapter.notifyDataSetChanged();
            });

        });
    }
}