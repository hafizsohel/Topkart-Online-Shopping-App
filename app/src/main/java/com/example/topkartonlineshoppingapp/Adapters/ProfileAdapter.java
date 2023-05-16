package com.example.topkartonlineshoppingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.topkartonlineshoppingapp.R;
import com.example.topkartonlineshoppingapp.models.ProfileModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder>{

    Context context;
    List<ProfileModel> list;

    public ProfileAdapter(Context context, List<ProfileModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_profile, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.email.setText(list.get(position).getEmail());



        Picasso.get().load(list.get(position).getPhotoUrl()).into(holder.photo);


       // Picasso.get().load(list.getPhotoUrl()).placeholder(R.drawable.boy).into(holder.photo);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EditText name, email, number,address;
        ImageView photo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.profile_name);
          //  email=itemView.findViewById(R.id.profile_email);
            number=itemView.findViewById(R.id.profile_number);
           // address=itemView.findViewById(R.id.profile_address);



            photo=itemView.findViewById(R.id.profile_img);
        }
    }
}
