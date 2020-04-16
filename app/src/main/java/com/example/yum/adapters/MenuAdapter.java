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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder>{
    Menu menu;
    private List<Menus> menuslist;
    Context mContext;

    public MenuAdapter(Menu menu, List<Menus> menuslist, Context mContext) {
        this.menu = menu;
        this.menuslist = menuslist;
        this.mContext = mContext;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView menu_card;
        ImageView menu_item_img;
        TextView menu_tv_name,menu_tv_description,menu_tv_date,menu_tv_like_count;
        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            menu_card = itemView.findViewById(R.id.menu_card);
            menu_item_img = itemView.findViewById(R.id.menu_item_img);
            menu_tv_name = itemView.findViewById(R.id.menu_tv_name);
            menu_tv_description = itemView.findViewById(R.id.menu_tv_description);
            menu_tv_date = itemView.findViewById(R.id.menu_tv_date);
            menu_tv_like_count = itemView.findViewById(R.id.menu_tv_like_count);
        }
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item,parent,false);
        return new MenuAdapter.MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, final int position) {


        //animation
        holder.menu_card.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        final Menus menus = menuslist.get(position);
        holder.menu_tv_name.setText(menus.getRecipename());
        holder.menu_tv_description.setText(menus.getRecipedescription()+"...");
        holder.menu_tv_date.setText(menus.getDate());
        holder.menu_tv_like_count.setText(menus.getRecipeslikes());
        Picasso.get().load(Stables.baseUrl+ menus.getRecipeimage()).into(holder.menu_item_img);


        holder.menu_card.setOnClickListener(new View.OnClickListener() {
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
        return menuslist.size();
    }


}
