package it.itsar.twizzoli.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.itsar.twizzoli.R;
import it.itsar.twizzoli.databinding.FragmentCommentBinding;
import it.itsar.twizzoli.models.Comment;
import it.itsar.twizzoli.models.User;

public class CommentFragment extends Fragment {

    private FragmentCommentBinding binding;
    private Comment comment = null;
    private User creator = null;

    public CommentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCommentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if(args == null) return;

        comment = (Comment) args.getSerializable("comment");
        creator = (User) args.getSerializable("creator");
        binding.textContent.setText(comment.text);
        binding.userIcon.setImageResource(creator.iconId);
        binding.username.setText(creator.nickname);
    }
}