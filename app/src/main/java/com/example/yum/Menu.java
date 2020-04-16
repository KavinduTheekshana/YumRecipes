package com.example.yum;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.yum.Common.Stables;
import com.example.yum.adapters.MenuAdapter;
import com.example.yum.adapters.MenuCategoryAdapter;
import com.example.yum.adapters.MyCookBookAdapter;
import com.example.yum.models.Categories;
import com.example.yum.models.Menus;
import com.example.yum.models.MyCookBook;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Menu extends Fragment {

    private EditText search_box;

    RecyclerView recyclerViewMenu,recyclerViewCategory;
    public ArrayList<Menus> menuslist;
    public ArrayList<Categories> categorieslist;
    MenuAdapter menuAdapter;
    MenuCategoryAdapter menuCategoryAdapter;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;

    public String filter;
    String searchPrefix;

    public Menu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_menu, container, false);

        //UI Declare
        search_box = v.findViewById(R.id.menu_search_et_box);

        //progress Dialog
        progressDialog=new Stables().showLoading(getContext());

        //MenuItem Recycle View
        menuslist = new ArrayList<>();
        categorieslist = new ArrayList<>();
        
        recyclerViewMenu=v.findViewById(R.id.menu_recycle);
        recyclerViewCategory=v.findViewById(R.id.menu_category_recycle);

        filter="0";
        searchPrefix="";

        LoadMenuItems();
        LoadCategoryItems();

        addSearchListener();



        return v;
    }

    private void addSearchListener() {
        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchPrefix=search_box.getText().toString();
                loadmenuItemsToList(filter);

//                if(product_for_all_users_search.getText().toString().length()>2){
//                    searchPrefix=product_for_all_users_search.getText().toString();
//                    loadItemsToList(filter);
//                }else{
//                    if(product_for_all_users_search.getText().toString().length()==0){
//                        searchPrefix="";
//                        loadItemsToList(filter);
//                    }
//                }


            }
        });
    }




    private void LoadCategoryItems() {
        menuCategoryAdapter = new MenuCategoryAdapter(getContext(), categorieslist,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategory.setLayoutManager(mLayoutManager);
        recyclerViewCategory.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCategory.setAdapter(menuCategoryAdapter);
        loadCategoryToList();
    }

    private void loadCategoryToList() {
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(new Stables().getCategoriesToList(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Categories all = new Categories("0","","All");
                categorieslist.add(all);

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Categories categories = new Categories();
                        categories.setId(jsonObject.getString("id"));
                        System.out.println(jsonObject.getString("id"));
                        categories.setName(jsonObject.getString("name"));

                        categorieslist.add(categories);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                menuCategoryAdapter.notifyDataSetChanged();
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

    public void LoadMenuItems() {
        menuAdapter = new MenuAdapter(this,menuslist,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewMenu.setLayoutManager(mLayoutManager);
        recyclerViewMenu.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMenu.setAdapter(menuAdapter);
        loadmenuItemsToList(filter);
    }

    private void loadmenuItemsToList(final String filterPrefix) {

        if(searchPrefix.trim().equals("")){
            progressDialog.show();
        }

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(new Stables().MenuItems(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                menuslist.clear();


                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Menus menu = new Menus();
                        if(jsonObject.getString("recipename").trim().contains(searchPrefix.trim())) {
                            if (filterPrefix == "0") {
                                menu.setId(jsonObject.getString("id"));
                                menu.setRecipename(jsonObject.getString("recipename"));
                                menu.setRecipeingredients(jsonObject.getString("recipeingredients"));
                                menu.setRecipedescription(Jsoup.parse(jsonObject.getString("recipedescription")).text());
                                menu.setRecipeimage(jsonObject.getString("recipeimage"));
                                menu.setRecipeslikes(jsonObject.getString("recipeslikes"));

                                menuslist.add(menu);

                            }else if(jsonObject.getString("categoryid").equals(filterPrefix)){

                                menu.setId(jsonObject.getString("id"));
                                menu.setRecipename(jsonObject.getString("recipename"));
                                menu.setRecipeingredients(jsonObject.getString("recipeingredients"));
                                menu.setRecipedescription(Jsoup.parse(jsonObject.getString("recipedescription")).text());
                                menu.setRecipeimage(jsonObject.getString("recipeimage"));
                                menu.setRecipeslikes(jsonObject.getString("recipeslikes"));

                                menuslist.add(menu);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                menuAdapter.notifyDataSetChanged();
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
}
