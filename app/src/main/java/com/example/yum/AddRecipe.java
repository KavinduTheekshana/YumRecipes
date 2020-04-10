package com.example.yum;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecipe extends Fragment {

    private static final int RESULT_CODE_REQUEST =101;
    private MaterialCardView btn_submit,add_new_recipes_image;
    private ImageView add_recipes_image;
    private EditText recipes_name,recipes_ingrediants,recipes_description;
    private TextView alert_box;

    //image part
    private Uri imageuri;
    private Boolean isImageAdded=false;

    public AddRecipe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        //Declare UI
        add_recipes_image = v.findViewById(R.id.add_recipes_add_new_img);
        add_new_recipes_image = v.findViewById(R.id.add_recipes_new_imageadd);
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

        add_new_recipes_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,RESULT_CODE_REQUEST);
            }
        });


        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==RESULT_CODE_REQUEST && data!=null)
        {
            imageuri=data.getData();
            isImageAdded=true;

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),imageuri);
                add_recipes_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
