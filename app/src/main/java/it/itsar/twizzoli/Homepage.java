package it.itsar.twizzoli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import it.itsar.twizzoli.data.ResultHandler;
import it.itsar.twizzoli.data.UserRepo;
import it.itsar.twizzoli.databinding.ActivityHomepageBinding;
import it.itsar.twizzoli.models.Post;
import it.itsar.twizzoli.models.User;

public class Homepage extends AppCompatActivity {

    private User loggedUser = null;
    private ActivityHomepageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_homepage);

        binding.postList.setAdapter(new HomepageAdapter());

    }

    private void loggedUserChecks() {
        if(loggedUser != null) return;
        Intent intent = new Intent(Homepage.this, LoginActivity.class);
        startActivity(intent);
    }

    static class HomepageAdapter extends RecyclerView.Adapter<HomepageAdapter.HomepageViewHolder> {

        private UserRepo userRepo;
        private Post[] postList = {};


        @NonNull
        @Override
        public HomepageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_post, parent, false);

            return new HomepageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HomepageViewHolder holder, int position) {
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

        static class HomepageViewHolder extends RecyclerView.ViewHolder{

            private TextView title;
            private TextView username;
            private ImageView userIcon;
            private TextView date;
            private TextView textContent;

            public HomepageViewHolder(@NonNull View itemView) {
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
                 textContent.setText(post.text);
            }
        }
    }
}