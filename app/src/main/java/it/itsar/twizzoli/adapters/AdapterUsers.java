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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import it.itsar.twizzoli.R;
import it.itsar.twizzoli.models.User;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder>{

    Context context;
    List<User> userList;

    public AdapterUsers(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_users,viewGroup);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        String username = userList.get(i).nickname;
        ArrayList<Integer> userFollower = userList.get(i).following;
        MyHolder.nNameTv.setText(username);
        MyHolder.followers.setText((CharSequence) userFollower);
        //ottenimento immagine


        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,""+username,Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return 0;
    }

    static class MyHolder extends RecyclerView.ViewHolder{
    ImageView avatarIv;
    static TextView nNameTv;
        static TextView followers;

    public MyHolder(@NonNull View itemView) {
        super(itemView);

        avatarIv = itemView.findViewById(R.id.avatarIv);
        nNameTv = itemView.findViewById(R.id.name);
        followers = itemView.findViewById(R.id.followerrow);
    }
}

}
