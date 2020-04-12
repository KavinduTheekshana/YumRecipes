package com.example.yum;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.yum.Common.Stables;
import com.example.yum.adapters.CategoriesAdapter;
import com.example.yum.adapters.MyCookBookAdapter;
import com.example.yum.models.Categories;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard extends Fragment {
    private EditText search_box;
    RecyclerView recyclerViewProduct;
    public ArrayList<Categories> categories;
    CategoriesAdapter categoriesAdapter;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;

    public Dashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //UI Declaration
        search_box = v.findViewById(R.id.dashboard_et_search);

        //progress
        progressDialog=new Stables().showLoading(getActivity());

        //recycle view
        categories=new ArrayList<>();
        recyclerViewProduct=v.findViewById(R.id.category_item_recycle);

        loadCategoryItems();



        // Inflate the layout for this fragment
        return v;
    }

    private void loadCategoryItems() {
        categoriesAdapter = new CategoriesAdapter(getContext(),categories,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewProduct.setLayoutManager(mLayoutManager);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerViewProduct.setItemAnimator(new DefaultItemAnimator());
        recyclerViewProduct.setAdapter(categoriesAdapter);


//        categoriesAdapter = new CategoriesAdapter(getContext(),categories,this);
//        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerViewProduct.setLayoutManager(new GridLayoutManager(getContext(),3));
//        recyclerViewProduct.setItemAnimator(new DefaultItemAnimator());
//        recyclerViewProduct.setAdapter(categoriesAdapter);
        loadItemsToList();
    }

    private void loadItemsToList() {
        progressDialog.show();

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(new Stables().getCategoriesToList(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {



                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Categories ca = new Categories();
                        ca.setName(jsonObject.getString("name"));
                        ca.setImage(jsonObject.getString("image"));


                        categories.add(ca);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }

                categoriesAdapter.notifyDataSetChanged();

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
