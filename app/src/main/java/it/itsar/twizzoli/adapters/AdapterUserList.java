package it.itsar.twizzoli.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import it.itsar.twizzoli.ProfileActivity;
import it.itsar.twizzoli.R;
import it.itsar.twizzoli.databinding.RowUsersBinding;
import it.itsar.twizzoli.models.User;

public class AdapterUserList extends RecyclerView.Adapter<AdapterUserList.ViewHolderUserList> {

    private final List<User> userList = new ArrayList<>();

    public AdapterUserList() {
    }

    public List<User> getUserList() {
        return userList;
    }

    @NonNull
    @Override
    public ViewHolderUserList onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RowUsersBinding binding = RowUsersBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ViewHolderUserList(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderUserList viewHolderUserList, int i) {
        viewHolderUserList.bind(userList.get(i));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class ViewHolderUserList extends RecyclerView.ViewHolder {
        private final RowUsersBinding binding;

        public ViewHolderUserList(@NonNull RowUsersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(User user) {
            binding.setUser(user);

            itemView.setOnClickListener(v->{
                Intent intent = new Intent(itemView.getContext(), ProfileActivity.class);
                intent.putExtra("profileUser", user);
                itemView.getContext().startActivity(intent);
            });


        }
    }
}
