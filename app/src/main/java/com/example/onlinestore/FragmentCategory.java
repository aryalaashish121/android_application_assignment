package com.example.onlinestore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCategory extends Fragment implements View.OnClickListener{

    LinearLayout layoutLaptop,layoutDesktop,layoutAccessories,layoutHardware,layoutSoftware;

    public FragmentCategory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        layoutLaptop = view.findViewById(R.id.linearLaptop);
        layoutDesktop = view.findViewById(R.id.linearDesktop);
        layoutHardware = view.findViewById(R.id.linearHardware);
        layoutSoftware = view.findViewById(R.id.linearSoftware);
        layoutAccessories = view.findViewById(R.id.linearAccessories);

        layoutLaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "This one is laptop", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),Product.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
//
//        if(v.getId()==R.id.linearLaptop){
//            Toast.makeText(getActivity(), "This one is laptop", Toast.LENGTH_LONG).show();
//        }
        if(v.getId()==R.id.linearDesktop){
            Toast.makeText(getActivity(), "Desktop", Toast.LENGTH_LONG).show();
        }
        if(v.getId()==R.id.linearHardware){
            Toast.makeText(getActivity(), "Hardware", Toast.LENGTH_LONG).show();
        }
        if(v.getId()==R.id.linearSoftware){
            Toast.makeText(getActivity(), "Software", Toast.LENGTH_LONG).show();
        }
        if(v.getId()==R.id.linearAccessories){
            Toast.makeText(getActivity(), "Accessories", Toast.LENGTH_LONG).show();
        }
    }
}
