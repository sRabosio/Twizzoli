package it.itsar.twizzoli.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.itsar.twizzoli.CommentActivity;
import it.itsar.twizzoli.ProfileActivity;
import it.itsar.twizzoli.databinding.FragmentCommentBinding;
import it.itsar.twizzoli.models.Comment;
import it.itsar.twizzoli.models.User;



public class AdapterCommentList extends RecyclerView.Adapter<AdapterCommentList.ViewHolderCommentList> implements Serializable {

    private final ArrayList<Comment> comments = new ArrayList<>();

    public AdapterCommentList(List<Comment> comments) {
        this.comments.addAll(comments);
    }

    public AdapterCommentList() {
    }

    @NonNull
    @Override
    public ViewHolderCommentList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        FragmentCommentBinding binding = FragmentCommentBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderCommentList(binding);
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCommentList holder, int position) {
        holder.bind(comments.get(position));


    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    static class ViewHolderCommentList extends RecyclerView.ViewHolder {

        private final FragmentCommentBinding binding;
        private final CollectionReference userRef = FirebaseFirestore
                .getInstance()
                .collection("users");
        private final CollectionReference comRef = FirebaseFirestore.getInstance().collection("post");


        public ViewHolderCommentList(@NonNull FragmentCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Comment comment) {
            binding.textContent.setMaxLines(4);
                binding.setComment(comment);
                setData(comment);

        }

        private void setData(Comment comment) {
            userRef.document(comment.creator)
                    .get()
                    .addOnSuccessListener(snap -> {
                        User creator = snap.toObject(User.class);
                        if (creator == null) return;
                        binding.setUser(creator);
                        binding.infoContainer.setOnClickListener(v -> {
                            Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                            intent.putExtra("profileUser", creator);
                            v.getContext().startActivity(intent);
                        });


                        binding.getRoot().setOnClickListener(view -> {
                            Context context = view.getContext();
                            Intent intent = new Intent(context, CommentActivity.class);
                            intent.putExtra("comment", comment);
                            intent.putExtra("creator", creator);
                            context.startActivity(intent);
                        });
                    });


        };
    }

}

