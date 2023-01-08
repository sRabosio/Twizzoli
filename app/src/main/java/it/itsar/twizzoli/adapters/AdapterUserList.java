package it.itsar.twizzoli.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import it.itsar.twizzoli.R;
import it.itsar.twizzoli.data.ResultHandler;
import it.itsar.twizzoli.data.UserRepo;
import it.itsar.twizzoli.models.User;

public class AdapterUserList extends RecyclerView.Adapter<AdapterUserList.ViewHolderUserList> {

    Context context;
    List<User> userList;

    public AdapterUserList(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolderUserList onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_users, viewGroup);
        return new ViewHolderUserList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderUserList viewHolderUserList, int i) {
        viewHolderUserList.bind(userList.get(i));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolderUserList extends RecyclerView.ViewHolder {
        private final ImageView avatarIv;
        private final TextView nNameTv;
        private final TextView followers;

        public ViewHolderUserList(@NonNull View itemView) {
            super(itemView);

            avatarIv = itemView.findViewById(R.id.avatarIv);
            nNameTv = itemView.findViewById(R.id.name);
            followers = itemView.findViewById(R.id.followerrow);
        }

        public void bind(User user) {
            avatarIv.setImageResource(user.iconId);
            nNameTv.setText(user.nickname);
            followers.setText(user.followers.size());

            itemView.setOnClickListener(view ->
                    Toast.makeText(itemView.getContext(), user.nickname, Toast.LENGTH_SHORT).show());
        }
    }
}
