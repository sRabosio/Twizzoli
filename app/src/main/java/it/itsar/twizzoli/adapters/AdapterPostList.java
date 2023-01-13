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

import it.itsar.twizzoli.PostActivity;
import it.itsar.twizzoli.ProfileActivity;
import it.itsar.twizzoli.R;
import it.itsar.twizzoli.data.ResultHandler;
import it.itsar.twizzoli.data.UserRepo;
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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post, parent, false);

        return new ViewHolderPostList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPostList holder, int position) {
        Post post = postList.get(position);

        holder.bind(post);
    }

    public ArrayList<Post> getPostList() {
        return postList;
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class ViewHolderPostList extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView username;
        private final ImageView userIcon;
        private final TextView date;
        private final TextView textContent;
        private final View infoContainer;
        private final CollectionReference userRef = FirebaseFirestore.getInstance().collection("users");

        public ViewHolderPostList(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            username = itemView.findViewById(R.id.username);
            userIcon = itemView.findViewById(R.id.user_icon);
            date = itemView.findViewById(R.id.date);
            textContent = itemView.findViewById(R.id.text_content);
            infoContainer = itemView.findViewById(R.id.info_container);
        }

        public void bind(Post post) {
            title.setText(post.title);
            username.setText(post.creator);
            if (post.creationDate != null)
                date.setText(post.creationDate.toString());

            textContent.setText(
                    post.text.length() > 50 ?
                            post.text.substring(0, 50).concat("...") :
                            post.text
            );

            setUserData(post);
        }

        private void setUserData(Post post) {
            userRef.document(post.creator).get()
                    .addOnSuccessListener(snap -> {
                        User user = snap.toObject(User.class);
                        if (user == null) return;
                        infoContainer.setOnClickListener(v -> {
                            Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                            intent.putExtra("profileUser", user);
                            v.getContext().startActivity(intent);
                        });

                        itemView.setOnClickListener(view -> {
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

