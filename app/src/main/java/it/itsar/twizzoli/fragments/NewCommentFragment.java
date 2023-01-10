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

import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.data.CommentRepo;
import it.itsar.twizzoli.databinding.FragmentNewCommentBinding;
import it.itsar.twizzoli.models.Comment;
import it.itsar.twizzoli.models.User;

public class NewCommentFragment extends Fragment {

    private FragmentNewCommentBinding binding;
    private User loggedUser = null;
    private Integer fatherPost = null;
    private Integer fatherComment = null;

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
        if(args == null) return;
        fatherPost = (Integer) args.getSerializable("fatherPost");
        fatherComment = (Integer) args.getSerializable("fatherComment");
        if(fatherPost == null && fatherComment == null) return;

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

        sendButton.setOnClickListener(v ->{
            if(loggedUser == null) return;
            String commentText = binding.commentText.getText().toString();

            Comment comment = new Comment(commentText, loggedUser.id, fatherPost, fatherComment);
            new CommentRepo().write(comment);
            binding.commentText.setText("");
            binding.commentText.clearFocus();
        });
    }
}