package com.example.yum.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yum.Common.Stables;
import com.example.yum.Dashboard;
import com.example.yum.Menu;
import com.example.yum.R;
import com.example.yum.models.Categories;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MenuCategoryAdapter extends RecyclerView.Adapter<MenuCategoryAdapter.MenuCategoryViewHolder>{
    Context mContext;
    private List<Categories> categoriesList;
    Menu menu;

    public MenuCategoryAdapter(Context mContext, List<Categories> categoriesList, Menu menu) {
        this.mContext = mContext;
        this.categoriesList = categoriesList;
        this.menu = menu;
    }

    public class MenuCategoryViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView menu_category_button;
        TextView menu_category_button_text;
        public MenuCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            menu_category_button = itemView.findViewById(R.id.menu_category_button);
            menu_category_button_text = itemView.findViewById(R.id.menu_category_button_text);
        }
    }

    int index;
    int fir=0;


    @NonNull
    @Override
    public MenuCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_category_button,parent,false);
        return new MenuCategoryAdapter.MenuCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuCategoryViewHolder holder,final int position) {

        final Categories categories = categoriesList.get(position);


        //animation
        holder.menu_category_button.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        holder.menu_category_button_text.setText(categories.getName());


        holder.menu_category_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index=position;
                notifyDataSetChanged();
            }
        });


        if(position==0){
            holder.menu_category_button.setCardBackgroundColor(0xFFffb107);
            holder.menu_category_button_text.setTextColor(Color.WHITE);
        }

        if(fir!=0){
            if(index==position){
                holder.menu_category_button.setCardBackgroundColor(0xFFffb107);
                holder.menu_category_button_text.setTextColor(Color.WHITE);

                menu.menuslist=new ArrayList<>();
                menu.filter  = categories.getId();
                menu.LoadMenuItems();
            }else{
                holder.menu_category_button.setCardBackgroundColor(Color.WHITE);
                holder.menu_category_button_text.setTextColor(0xFFffb107);
            }
        }else{
            fir++;
        }


    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }


}
