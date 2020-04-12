package com.example.yum.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yum.Account;
import com.example.yum.Common.Stables;
import com.example.yum.R;
import com.example.yum.models.MyCookBook;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyCookBookAdapter extends RecyclerView.Adapter<MyCookBookAdapter.MyCookBookViewHolder> {
    Account account;
    private List<MyCookBook> myCookBooks;
    Context mContext;

    public MyCookBookAdapter(Account account, List<MyCookBook> myCookBooks, Context mContext) {
        this.account = account;
        this.myCookBooks = myCookBooks;
        this.mContext = mContext;
    }

    public class MyCookBookViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cookbook_card;
        ImageView cookbook_item_img;
        TextView cookbook_tv_name,cookbook_tv_description,cookbook_tv_date,cookbook_tv_like_count,cookbook_tv_status;
        public MyCookBookViewHolder(@NonNull View itemView) {
            super(itemView);
            cookbook_card = itemView.findViewById(R.id.cookbook_card);
            cookbook_item_img = itemView.findViewById(R.id.cookbook_item_img);
            cookbook_tv_name = itemView.findViewById(R.id.cookbook_tv_name);
            cookbook_tv_description = itemView.findViewById(R.id.cookbook_tv_description);
            cookbook_tv_date = itemView.findViewById(R.id.cookbook_tv_date);
            cookbook_tv_like_count = itemView.findViewById(R.id.cookbook_tv_like_count);
            cookbook_tv_status = itemView.findViewById(R.id.cookbook_tv_status);

        }
    }

    @NonNull
    @Override
    public MyCookBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cookbook_item,parent,false);
        return new MyCookBookAdapter.MyCookBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCookBookViewHolder holder, int position) {

        //animation
        holder.cookbook_card.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        MyCookBook myCookBook = myCookBooks.get(position);
        holder.cookbook_tv_name.setText(myCookBook.getName());
        holder.cookbook_tv_description.setText(myCookBook.getDescription()+"...");
        holder.cookbook_tv_date.setText(myCookBook.getDate());
        holder.cookbook_tv_like_count.setText(myCookBook.getLikes());
        Picasso.get().load(Stables.baseUrl+ myCookBook.getImage()).into(holder.cookbook_item_img);
        if (Integer.parseInt(myCookBook.getStatus())==1){
            holder.cookbook_tv_status.setText("Approved");
            holder.cookbook_tv_status.setBackgroundResource(R.color.approved_color);
        }else {
            holder.cookbook_tv_status.setText("Pending");
            holder.cookbook_tv_status.setBackgroundResource(R.color.alert_colour);
        }


    }

    @Override
    public int getItemCount() {
        return myCookBooks.size();
    }


}
