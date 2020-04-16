package com.example.yum;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileDetails extends Fragment {
    MaterialCardView profile_details_update_password,profile_details_update_details,profile_details_change_image,profile_details_edit_logout;

    private EditText user_name,user_email;
    private TextView go_back,alert_box;
    private ImageView user_profile_pic;
    private static final int PERMISSION_REQUEST = 1;
    private static final int IMAGE_REQUEST = 2;


    //imagepart
    private static final int RESULT_CODE_REQUEST =101;
    private Uri imageuri;
    private Boolean isImageAdded=false;
    Bitmap bitmap = null;

    public ProfileDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_details, container, false);

        //UI Declare
        profile_details_update_password=v.findViewById(R.id.profile_details_update_password);
        profile_details_edit_logout = v.findViewById(R.id.profile_details_edit_logout);
        user_name = v.findViewById(R.id.profile_details_edit_user_name);
        user_email = v.findViewById(R.id.profile_details_edit_user_email);
        go_back = v.findViewById(R.id.profile_details_edit_back);
        alert_box = v.findViewById(R.id.profile_details_tv_alert);

        //----------------------------------------------------------------------------------------------------
        profile_details_update_details = v.findViewById(R.id.profile_details_edit_edit_details);
        user_profile_pic = v.findViewById(R.id.profile_details_edit_user_name_image);
        profile_details_change_image = v.findViewById(R.id.profile_details_change_pro_pic);

        //-----------------------------------------------------------------------------------------------

        //Current Password
        profile_details_update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentPassword currentPassword = new CurrentPassword();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                fragmentTransaction.replace(R.id.container,currentPassword).commit();
            }
        });

        //Account
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = new Account();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                fragmentTransaction.replace(R.id.container,account).commit();

            }
        });

        profile_details_change_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,RESULT_CODE_REQUEST);
            }
        });



        //BUTTON----------------------------------------------------------------------------------
        profile_details_update_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = user_name.getText().toString().trim();
                String email = user_email.getText().toString().trim();

                if (ValidateUserData.update_profile_validate(name,email)){

                    ChangeProfileDetails();

                }else{
                    alert_box.setText("Please Fill All Details");
                }
            }
        });




        profile_details_edit_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });


        LoadMainData();

        return v;

    }



    private void ChangeProfileDetails() {


        try {

            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
            RequestQueue requestQueue= Volley.newRequestQueue(getContext());

            JSONObject jsonBody = new JSONObject();

            jsonBody.put("name", user_name.getText().toString().trim());
            jsonBody.put("uid", sharedPreferences.getString("user_id","0"));

            if(bitmap!=null){
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                System.out.println(encodedImage);
                jsonBody.put("image", encodedImage);
            }


            JsonObjectRequest jsonOblectReq = new JsonObjectRequest(new Stables().ChangeProfileDetailsURL(), jsonBody, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {

                        Toast.makeText(getActivity(), "Recipe Added Successfully", Toast.LENGTH_SHORT).show();

                            JSONObject userObj = response.getJSONObject("user");
                            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("user",Context.MODE_PRIVATE);;
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("name",userObj.getString("name"));
                            editor.putString("profile_pic",userObj.getString("profile_pic"));
                            editor.commit();
                            LoadMainData();

                    }catch(Exception ex){}
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(jsonOblectReq);
        }catch(Exception e){}






//        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
//            RequestQueue requestQueue= Volley.newRequestQueue(getContext());
//            StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().ChangeProfileDetails(sharedPreferences.getString("user_id","0"),user_name.getText().toString().trim()), new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    //hide loading
//                    try {
//                        JSONObject jsonObject=new JSONObject(response);
//
//                        if(jsonObject.getString("code").equals("1")){
//
//                            Toast.makeText(getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
//
//                            JSONObject userObj = jsonObject.getJSONObject("user");
//                            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("user",Context.MODE_PRIVATE);;
//                            SharedPreferences.Editor editor=sharedPreferences.edit();
//                            editor.putString("name",userObj.getString("name"));
//                            editor.commit();
//                            LoadMainData();
//
//
//
//
//                        }
//                        else{
//                            Toast.makeText(getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    }catch(Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener(){
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            requestQueue.add(stringRequest);
//
//



    }

    private void LoadMainData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        user_name.setText(sharedPreferences.getString("name","0"));
        user_email.setText(sharedPreferences.getString("email","0"));
        Picasso.get().load(Stables.baseUrl+ sharedPreferences.getString("profile_pic","0")).into(user_profile_pic);
    }

    private void LogOut() {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(getActivity(), LoginOrSignUp.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==RESULT_CODE_REQUEST && data!=null)
        {
            imageuri=data.getData();
            isImageAdded=true;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),imageuri);
                user_profile_pic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
