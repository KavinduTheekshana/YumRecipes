package com.example.yum.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yum.Account;
import com.example.yum.Common.Stables;
import com.example.yum.Menu;
import com.example.yum.R;
import com.example.yum.SingleRecipe;
import com.example.yum.models.Menus;
import com.example.yum.models.MyCookBook;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyCookBookAdapter extends RecyclerView.Adapter<MyCookBookAdapter.MyCookBookViewHolder> {
    Account account;
    private List<Menus> menusList;
    Context mContext;

    public MyCookBookAdapter(Account account, List<Menus> menusList, Context mContext) {
        this.account = account;
        this.menusList = menusList;
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

        final Menus menus = menusList.get(position);
        holder.cookbook_tv_name.setText(menus.getRecipename());
        holder.cookbook_tv_description.setText(menus.getRecipedescription()+"...");
        holder.cookbook_tv_date.setText(menus.getDate());
        holder.cookbook_tv_like_count.setText(menus.getRecipeslikes());
        Picasso.get().load(Stables.baseUrl+ menus.getRecipeimage()).into(holder.cookbook_item_img);
        if (Integer.parseInt(menus.getRecipestatus())==1){
            holder.cookbook_tv_status.setText("Approved");
            holder.cookbook_tv_status.setBackgroundResource(R.color.approved_color);
        }else {
            holder.cookbook_tv_status.setText("Pending");
            holder.cookbook_tv_status.setBackgroundResource(R.color.alert_colour);
        }

        holder.cookbook_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id",menus.getId());
                bundle.putString("name",menus.getRecipename());
                bundle.putString("category",menus.getCategoryname());
                bundle.putString("ingredients",menus.getRecipeingredients());
                bundle.putString("description",menus.getRecipedescription());
                bundle.putString("image",menus.getRecipeimage());
                bundle.putString("user_image",menus.getUserimage());

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new SingleRecipe();
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return menusList.size();
    }


}
