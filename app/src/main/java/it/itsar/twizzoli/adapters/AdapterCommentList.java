package it.itsar.twizzoli.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.itsar.twizzoli.CommentActivity;
import it.itsar.twizzoli.ProfileActivity;
import it.itsar.twizzoli.R;
import it.itsar.twizzoli.data.ResultHandler;
import it.itsar.twizzoli.data.UserRepo;
import it.itsar.twizzoli.models.Comment;
import it.itsar.twizzoli.models.Post;
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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post, parent, false);

        return new ViewHolderCommentList(view);
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCommentList holder, int position) {
        final Comment comment = comments.get(position);

        holder.bind(comment);

        holder.itemView.setOnClickListener(view -> {
            Context context = view.getContext();
            Intent intent = new Intent(context, CommentActivity.class);
            intent.putExtra("comment", comment);
            context.startActivity(intent
            );
        });

    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    static class ViewHolderCommentList extends RecyclerView.ViewHolder {

        private final TextView commentText;
        private final ImageView userIcon;
        private final TextView username;
        private final View userInfo;
        private final CollectionReference userRef = FirebaseFirestore
                .getInstance()
                .collection("users");


        public ViewHolderCommentList(@NonNull View itemView) {
            super(itemView);
            commentText = itemView.findViewById(R.id.text_content);
            username = itemView.findViewById(R.id.username);
            userIcon = itemView.findViewById(R.id.user_icon);
            userInfo = itemView.findViewById(R.id.info_container);
        }

        public void bind(Comment comment) {
            commentText.setText(comment.text);
            setCreatorData(comment.creator);
        }

        private void setCreatorData(String username) {
            userRef.document(username)
                    .get()
                    .addOnSuccessListener(snap -> {
                        User creator = snap.toObject(User.class);
                        if (creator == null) return;
                        this.username.setText(creator.username);
                        userIcon.setImageResource(creator.iconId);
                        userInfo.setOnClickListener(v -> {
                            Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                            intent.putExtra("profileUser", creator);
                            v.getContext().startActivity(intent);
                        });
                    });
        }

    }
}
