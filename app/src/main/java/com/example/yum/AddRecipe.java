package com.example.yum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yum.Common.Stables;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecipe extends Fragment {

    private static final int RESULT_CODE_REQUEST =101;
    private MaterialCardView btn_submit,add_new_recipes_image;
    private ImageView add_recipes_image;
    private EditText recipes_name,recipes_ingrediants,recipes_description;
    private TextView alert_box;

    private Spinner spinner;

    //image part
    private Uri imageuri;
    private Boolean isImageAdded=false;

    Bitmap bitmap = null;

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

                    AddRecipeToDB();
                    recipes_name.setText(null);
                    recipes_description.setText(null);
                    recipes_ingrediants.setText(null);

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

    private void AddRecipeToDB() {

       try {

           SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
           RequestQueue requestQueue= Volley.newRequestQueue(getContext());

           JSONObject jsonBody = new JSONObject();

           jsonBody.put("recipes_name", recipes_name.getText().toString().trim());
           jsonBody.put("recipes_ingrediants", recipes_ingrediants.getText().toString().trim());
           jsonBody.put("recipes_description", recipes_description.getText().toString().trim());
           jsonBody.put("uid", sharedPreferences.getString("user_id","0"));

           if(bitmap!=null){
               System.out.println("img");
               ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
               bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
               String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
               System.out.println(encodedImage);
               jsonBody.put("image", encodedImage);
           }


           JsonObjectRequest jsonOblectReq = new JsonObjectRequest(new Stables().AddRecipesToDBUrl(), jsonBody, new Response.Listener<JSONObject>() {

               @Override
               public void onResponse(JSONObject response) {
                    try {

                        Toast.makeText(getActivity(), "Recipe Added Successfully", Toast.LENGTH_SHORT).show();

                    }catch(Exception ex){}
               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {

               }
           });
           requestQueue.add(jsonOblectReq);
       }catch(Exception e){}






//        StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().AddRecipesToDB(sharedPreferences.getString("user_id","0"),
//                recipes_name.getText().toString().trim(),recipes_ingrediants.getText().toString().trim(),recipes_description.getText().toString().trim()), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //hide loading
//                try {
//                    JSONObject jsonObject=new JSONObject(response);
//
//                    if(jsonObject.getString("code").equals("1")){
//
//                        Toast.makeText(getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
//
//                    }
//                    else{
//                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener(){
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==RESULT_CODE_REQUEST && data!=null)
        {
            imageuri=data.getData();
            isImageAdded=true;
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
