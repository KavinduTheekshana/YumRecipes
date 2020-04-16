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
import com.example.yum.Dashboard;
import com.example.yum.R;
import com.example.yum.models.Categories;
import com.example.yum.models.MyCookBook;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>{
    Context mContext;
    private List<Categories> categoriesList;
    Dashboard dashboard;

    public CategoriesAdapter(Context mContext, List<Categories> categoriesList, Dashboard dashboard) {
        this.mContext = mContext;
        this.categoriesList = categoriesList;
        this.dashboard = dashboard;
    }

    public class CategoriesViewHolder extends RecyclerView.ViewHolder {
        ImageView category_item_img;
        TextView category_item_name;
        MaterialCardView category_item_card;
        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            category_item_img = itemView.findViewById(R.id.category_item_img);
            category_item_name = itemView.findViewById(R.id.category_item_name);
            category_item_card = itemView.findViewById(R.id.category_item_card);
        }
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new CategoriesAdapter.CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, final int position) {

        final Categories categories = categoriesList.get(position);


        //animation
        holder.category_item_card.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        holder.category_item_name.setText(categories.getName());
        Picasso.get().load(Stables.baseUrl+ categories.getImage()).into(holder.category_item_img);


    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }


}
