package com.example.yum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yum.Common.Stables;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingleRecipe extends Fragment {

    private TextView recipes_name,recipes_ingredients,recipies_descriptions,goback_btn,recipes_category;
    private ImageView recipes_image,recipes_image_user;

    public SingleRecipe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_single_recipe, container, false);


        System.out.println(getArguments().getString("id"));
        System.out.println(getArguments().getString("name"));
        System.out.println(getArguments().getString("category"));
        System.out.println(getArguments().getString("ingredients"));
        System.out.println(getArguments().getString("description"));
        System.out.println(getArguments().getString("image"));
        System.out.println(getArguments().getString("user_image"));

        //UI Declare
        recipes_name = v.findViewById(R.id.single_recipes_recipes_name);
        recipes_category = v.findViewById(R.id.single_recipes_recipes_ingredients_category);
        recipes_ingredients = v.findViewById(R.id.single_recipes_recipes_ingredients);
        recipies_descriptions = v.findViewById(R.id.single_recipes_recipes_description);
//        goback_btn = v.findViewById(R.id.single_recipes_back_btn);
        recipes_image = v.findViewById(R.id.single_recipes_recipes_image);
        recipes_image_user = v.findViewById(R.id.recipes_image_user);


        //Go to single category
//        goback_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Menu menu = new Menu();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
//                fragmentTransaction.replace(R.id.container,menu).commit();
//            }
//        });


        SetData();
        // Inflate the layout for this fragment
        return v;
    }

    private void SetData() {
        Picasso.get().load(Stables.baseUrl+ getArguments().getString("image")).into(recipes_image);
        Picasso.get().load(Stables.baseUrl+ getArguments().getString("user_image")).into(recipes_image_user);
        recipes_name.setText(getArguments().getString("name"));
        recipes_category.setText(getArguments().getString("category"));
        recipes_ingredients.setText(getArguments().getString("ingredients"));
        recipies_descriptions.setText(getArguments().getString("description"));
    }
}
