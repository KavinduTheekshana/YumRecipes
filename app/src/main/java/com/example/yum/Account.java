package com.example.yum;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.yum.Common.Stables;
import com.example.yum.adapters.MyCookBookAdapter;
import com.example.yum.models.Menus;
import com.example.yum.models.MyCookBook;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;

import static com.example.yum.R.id.bottom_navigation_view;
import static com.example.yum.R.id.gone;


/**
 * A simple {@link Fragment} subclass.
 */
public class Account extends Fragment{

    private ImageView profile_edit,user_profile_pic;
    private TextView user_name,user_email,user_recipes_count;

    RecyclerView recyclerViewProduct;
    public ArrayList<Menus> menusList;
    MyCookBookAdapter myCookBookAdapter;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;


    public Account() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_account, container, false);

        //progress Dialog
        progressDialog=new Stables().showLoading(getContext());

        //UI Declare
        profile_edit = v.findViewById(R.id.btn_go_profilr_rdite);
        user_profile_pic = v.findViewById(R.id.account_user_profile_pic);

        user_name = v.findViewById(R.id.account_tv_user_name);
        user_email = v.findViewById(R.id.account_tv_user_email);
        user_recipes_count = v.findViewById(R.id.account_tv_recipes_count);

        menusList = new ArrayList<>();
        recyclerViewProduct=v.findViewById(R.id.account_my_cook_book);

        loadCookBook();

        LoadMainData();






        profile_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDetails profileDetails = new ProfileDetails();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                fragmentTransaction.replace(R.id.container,profileDetails).commit();
            }
        });




        // Inflate the layout for this fragment
        return v;
    }

    private void LoadMainData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        user_name.setText(sharedPreferences.getString("name","0"));
        user_email.setText(sharedPreferences.getString("email","0"));
        Picasso.get().load(Stables.baseUrl+ sharedPreferences.getString("profile_pic","0")).into(user_profile_pic);
    }

    private void loadCookBook() {
        myCookBookAdapter = new MyCookBookAdapter(this,menusList,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewProduct.setLayoutManager(mLayoutManager);
        recyclerViewProduct.setItemAnimator(new DefaultItemAnimator());
        recyclerViewProduct.setAdapter(myCookBookAdapter);
        loadCookBooksToList();
    }

    private void loadCookBooksToList() {

        progressDialog.show();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(new Stables().getCookBooksToList(sharedPreferences.getString("user_id","0")), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Menus menus = new Menus();
                        menus.setId(jsonObject.getString("id"));
                        menus.setRecipename(jsonObject.getString("recipename"));
                        menus.setCategoryname(jsonObject.getString("categoryname"));
                        menus.setRecipeingredients(jsonObject.getString("recipeingredients"));
                        menus.setRecipedescription(Jsoup.parse(jsonObject.getString("recipedescription")).text());
                        menus.setRecipeimage(jsonObject.getString("recipeimage"));
                        menus.setUserimage(jsonObject.getString("userimage"));
                        menus.setRecipeslikes(jsonObject.getString("recipeslikes"));
                        menus.setRecipestatus(jsonObject.getString("recipestatus"));
                        menusList.add(menus);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                myCookBookAdapter.notifyDataSetChanged();
                RecipeCount();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
        progressDialog.dismiss();

    }


    public void RecipeCount(){
        int count=0;
        for(Menus menus:menusList){
            count++;
        }
        user_recipes_count.setText(String.valueOf(count));
    }


}
