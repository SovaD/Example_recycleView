package com.sova.example_recycleview;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList ids, names, emails, phones, images;

    public MyAdapter(Context context, ArrayList ids, ArrayList names, ArrayList emails, ArrayList phones, ArrayList images) {
        this.context = context;
        this.names = names;
        this.ids = ids;
        this.emails = emails;
        this.phones = phones;
        this.images = images;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).
                inflate(R.layout.userentry, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.id.setText(String.valueOf(ids.get(position)));
        holder.name.setText(String.valueOf(names.get(position)));
        holder.email.setText(String.valueOf(emails.get(position)));
        holder.phone.setText(String.valueOf(phones.get(position)));

        try {
            Glide.with(holder.iView.getContext())
                    .load(Uri.parse(String.valueOf(images.get(position))))
                    .into(holder.iView);
//            holder.iView.setImageURI(Uri.parse(String.valueOf(images.get(position))));
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return ids.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, email, phone;
        ImageView iView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tvId);
            name = itemView.findViewById(R.id.tvName);
            email = itemView.findViewById(R.id.tvEmail);
            phone = itemView.findViewById(R.id.tvPhone);
            iView = itemView.findViewById(R.id.iView);
        }
    }
}
