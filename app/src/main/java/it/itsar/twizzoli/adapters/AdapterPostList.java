package it.itsar.twizzoli.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.itsar.twizzoli.PostActivity;
import it.itsar.twizzoli.ProfileActivity;
import it.itsar.twizzoli.databinding.FragmentPostBinding;
import it.itsar.twizzoli.models.Post;
import it.itsar.twizzoli.models.User;

public class AdapterPostList extends RecyclerView.Adapter<AdapterPostList.ViewHolderPostList> implements Serializable {

    private final ArrayList<Post> postList = new ArrayList<>();

    public AdapterPostList(List<Post> postList) {
        this.postList.addAll(postList);
    }


    public AdapterPostList() {
    }

    @NonNull
    @Override
    public ViewHolderPostList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentPostBinding binding = FragmentPostBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderPostList(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPostList holder, int position) {
        holder.bind(postList.get(position));
    }

    public ArrayList<Post> getPostList() {
        return postList;
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class ViewHolderPostList extends RecyclerView.ViewHolder {

        private final CollectionReference userRef = FirebaseFirestore.getInstance().collection("users");
        private final CollectionReference postRef = FirebaseFirestore.getInstance().collection("post");
        private final FragmentPostBinding binding;

        public ViewHolderPostList(@NotNull FragmentPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Post post) {
            binding.title.setMaxLines(2);
            binding.setPost(post);
            setUserData(post);
            binding.textContent.setMaxLines(3);

        }

        private void setUserData(Post post) {
            userRef.document(post.creator).get()
                    .addOnSuccessListener(snap -> {
                        User user = snap.toObject(User.class);
                        if (user == null) return;
                        binding.setUser(user);
                        binding.infoContainer.setOnClickListener(v -> {
                            Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                            intent.putExtra("profileUser", user);
                            v.getContext().startActivity(intent);
                        });

                        binding.containerPost.setOnClickListener(view -> {
                            Context context = view.getContext();
                            Intent intent = new Intent(context, PostActivity.class);
                            intent.putExtra("post", post);
                            intent.putExtra("creator", user);
                            context.startActivity(intent);
                        });
                    });
        }
    }
}

