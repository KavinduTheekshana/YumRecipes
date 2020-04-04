package com.example.yum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Menu extends Fragment {
    MaterialCardView single_recipe;

    public Menu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_menu, container, false);

        single_recipe = v.findViewById(R.id.single_recipe);
        single_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleRecipe singleRecipe = new SingleRecipe();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                fragmentTransaction.replace(R.id.container,singleRecipe).commit();
            }
        });






        return v;
    }
}
