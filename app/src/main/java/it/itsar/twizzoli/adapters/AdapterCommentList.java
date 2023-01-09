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

import it.itsar.twizzoli.CommentActivity;
import it.itsar.twizzoli.PostActivity;
import it.itsar.twizzoli.ProfileActivity;
import it.itsar.twizzoli.R;
import it.itsar.twizzoli.data.ResultHandler;
import it.itsar.twizzoli.data.UserRepo;
import it.itsar.twizzoli.models.Comment;
import it.itsar.twizzoli.models.User;

public class AdapterCommentList extends RecyclerView.Adapter<AdapterCommentList.ViewHolderCommentList> {

    private final Comment[] comments;

    public AdapterCommentList(Comment[] comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolderCommentList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post, parent, false);

        return new ViewHolderCommentList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCommentList holder, int position) {
        final Comment comment = comments[position];
        new UserRepo().getElementById(comment.creator, new ResultHandler() {
            @Override
            public <T> void success(T result) {
                User creator = (User) result;
                holder.bind(comment, creator);
            }

            @Override
            public void failed(int code, String message) {
                Log.d("ERROR IN BIND VIEW HOLDER", "Creator not found");
            }
        });

        holder.itemView.setOnClickListener(view->{
            Context context = view.getContext();
            Intent intent = new Intent(context, CommentActivity.class);
            intent. putExtra("comment", comment);
            context.startActivity(intent
            );
        });

    }

    @Override
    public int getItemCount() {
        return comments.length;
    }

    static class ViewHolderCommentList extends RecyclerView.ViewHolder {

        private final TextView commentText;
        private final ImageView userIcon;
        private final TextView username;
        private final View userInfo;


        public ViewHolderCommentList(@NonNull View itemView) {
            super(itemView);
            commentText = itemView.findViewById(R.id.text_content);
            username = itemView.findViewById(R.id.username);
            userIcon = itemView.findViewById(R.id.user_icon);
            userInfo = itemView.findViewById(R.id.info_container);
        }

        public void bind(Comment comment, User creator){
            commentText.setText(comment.text);
            username.setText(creator.nickname);
            userIcon.setImageResource(creator.iconId);
            userInfo.setOnClickListener(v->{
                Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                intent.putExtra("profileUser", creator);
                v.getContext().startActivity(intent);
            });
        }

    }
}
