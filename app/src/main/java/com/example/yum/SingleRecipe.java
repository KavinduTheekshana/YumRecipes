package com.example.yum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingleRecipe extends Fragment {

    private EditText recipes_name,recipes_ingredients,recipies_descriptions,goback_btn;
    private ImageView recipes_image;

    public SingleRecipe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_single_recipe, container, false);

        //UI Declare
        recipes_name = v.findViewById(R.id.single_recipes_recipes_name);
        recipes_ingredients = v.findViewById(R.id.single_recipes_recipes_ingredients);
        recipies_descriptions = v.findViewById(R.id.single_recipes_recipes_description);
        goback_btn = v.findViewById(R.id.single_recipes_back_btn);
        recipes_image = v.findViewById(R.id.single_recipes_recipes_image);


        //Go to single category
        goback_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SingleCategory singleCategory = new SingleCategory();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                fragmentTransaction.replace(R.id.container,singleCategory).commit();
            }
        });


        // Inflate the layout for this fragment
        return v;
    }
}
