package it.itsar.twizzoli.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import it.itsar.twizzoli.R;
import it.itsar.twizzoli.databinding.FragmentPostBinding;
import it.itsar.twizzoli.models.Post;
import it.itsar.twizzoli.models.User;

public class PostFragment extends Fragment {

    private FragmentPostBinding binding;
    private Post post = null;
    private User creator = null;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPostBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments() == null) return;
        post = (Post) getArguments().getSerializable("post");
        creator = (User) getArguments().getSerializable("creator");
        binding.title.setText(post.title);
        binding.textContent.setText(post.text);
        binding.userIcon.setImageResource(creator.iconId);
        binding.username.setText(creator.nickname);
    }
}