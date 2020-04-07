package com.example.yum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecipe extends Fragment {

    private MaterialCardView btn_submit;
    private ImageView add_recipes_image;
    private EditText recipes_name,recipes_ingrediants,recipes_description;
    private TextView alert_box;

    public AddRecipe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        //Declare UI
        add_recipes_image = v.findViewById(R.id.add_recipes_add_new_img);
        recipes_name = v.findViewById(R.id.add_recipes_et_recipe_name);
        recipes_ingrediants = v.findViewById(R.id.add_recipes_et_recipe_ingredients);
        recipes_description = v.findViewById(R.id.add_recipes_et_recipe_description);
        btn_submit = v.findViewById(R.id.add_recipes_btn_submit);
        alert_box = v.findViewById(R.id.addrecipes_tv_alert);

        //Add Recipes
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = recipes_name.getText().toString().trim();
                String ingredients = recipes_ingrediants.getText().toString().trim();
                String description = recipes_description.getText().toString().trim();

                if (ValidateUserData.add_recipes_validate(name,ingredients,description)){

                }else {
                    alert_box.setText("Please Complete Details");
                }
            }
        });


        // Inflate the layout for this fragment
        return v;
    }
}
