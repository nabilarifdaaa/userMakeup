package com.android.nabila.usermakeup.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.nabila.usermakeup.Model.User;
import com.android.nabila.usermakeup.Profile;
import com.android.nabila.usermakeup.R;
import com.android.nabila.usermakeup.Rest.ApiClient;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    List<User> listUser;
    Context mContext;

    public UserAdapter(List<User> listUser) {
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_profile, parent, false);
        UserViewHolder mHolder = new UserViewHolder(view);
        return mHolder;
//        return null;
    }

    @Override
    public void onBindViewHolder(UserAdapter.UserViewHolder holder, final int position) {
        holder.tvIdUser.setText(listUser.get(position).getId_user());
        holder.tvNama.setText(listUser.get(position).getNama());
        holder.tvUsername.setText(listUser.get(position).getUsername());
        holder.tvUmur.setText(listUser.get(position).getUmur());
        holder.tvUmur.setText(listUser.get(position).getUmur());
        holder.tvUmur.setText(listUser.get(position).getUmur());
        if (listUser.get(position).getPhotoUrlUser() != null ){
            Glide.with(holder.itemView.getContext()).load(ApiClient.BASE_URL + listUser.get
                    (position).getPhotoUrlUser())
                    .into(holder.mPhotoURL);
        } else {
            Glide.with(holder.itemView.getContext()).load(R.drawable.default_user).into(holder
                    .mPhotoURL);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Profile.class);
                intent.putExtra("idUser",listUser.get(position).getId_user());
                intent.putExtra("nama",listUser.get(position).getNama());
                intent.putExtra("umur",listUser.get(position).getUmur());
                intent.putExtra("jenisKulit",listUser.get(position).getJenis_kulit());
                intent.putExtra("warnaKulit",listUser.get(position).getWarna_kulit());
                intent.putExtra("username",listUser.get(position).getUsername());
                intent.putExtra("password",listUser.get(position).getPassword());
                intent.putExtra("photo_url",listUser.get(position).getPhotoUrlUser());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView mPhotoURL;
        TextView tvIdUser, tvNama,tvUsername,tvUmur,tvJenisKulit,tvWarnaKulit;

        public UserViewHolder(View itemView) {
            super(itemView);
            mPhotoURL =  itemView.findViewById(R.id.fotoprofil);
            tvIdUser=  itemView.findViewById(R.id.tvIdUser);
            tvNama =  itemView.findViewById(R.id.tvNama);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvUmur = itemView.findViewById(R.id.tvUmur);
            tvJenisKulit = itemView.findViewById(R.id.tvJenis);
            tvWarnaKulit = itemView.findViewById(R.id.tvWarna);
        }
        SharedPreferences pref = mContext.getSharedPreferences
                ("LoginData", Context.MODE_PRIVATE);
        String idUser = pref.getString("idUser", "");


    }

}
