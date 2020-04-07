package com.example.yum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingleCategory extends Fragment {

    private EditText go_back;

    public SingleCategory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_single_category, container, false);

        //UI Declare
        go_back  =v.findViewById(R.id.single_category_tv_back);


        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dashboard dashboard = new Dashboard();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                fragmentTransaction.replace(R.id.container,dashboard).commit();
            }
        });




        // Inflate the layout for this fragment
        return v;
    }
}
