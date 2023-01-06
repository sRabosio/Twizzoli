package it.itsar.twizzoli.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.itsar.twizzoli.R;
import it.itsar.twizzoli.data.ResultHandler;
import it.itsar.twizzoli.data.UserRepo;
import it.itsar.twizzoli.models.Post;
import it.itsar.twizzoli.models.User;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostListViewHolder> {

    private final UserRepo userRepo = new UserRepo();
    private Post[] postList;

    public PostListAdapter(Post[] postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post, parent, false);

        return new PostListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostListViewHolder holder, int position) {
        Post post = postList[position];

        userRepo.getElementById(post.creator, new ResultHandler() {
            @Override
            public <T> void success(T result) {
                holder.bind(post, (User) result);
            }

            @Override
            public void failed(int code, String message) {
                Log.d(String.valueOf(code), message);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.length;
    }

    public void setPostList(Post[] postList) {
        this.postList = postList;
    }

    static class PostListViewHolder extends RecyclerView.ViewHolder{

        private final TextView title;
        private final TextView username;
        private final ImageView userIcon;
        private final TextView date;
        private final TextView textContent;

        public PostListViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            username = itemView.findViewById(R.id.username);
            userIcon = itemView.findViewById(R.id.user_icon);
            date = itemView.findViewById(R.id.date);
            textContent = itemView.findViewById(R.id.text_content);

        }

        public void bind(Post post, User creator){
            title.setText(post.title);
            username.setText(creator.nickname);


            textContent.setText(
                    post.text.length() > 50 ?
                            post.text.substring(0,50).concat("...") :
                            post.text
            );
        }
    }
}
