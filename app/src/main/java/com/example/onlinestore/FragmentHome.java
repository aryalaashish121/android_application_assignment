package com.example.onlinestore;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.onlinestore.Controller.ImageSlideAdapter;
import com.example.onlinestore.Controller.ItemAdapter;
import com.example.onlinestore.Interface.ItemsApi;
import com.example.onlinestore.Interface.UsersApi;
import com.example.onlinestore.Model.ItemsDetail;
import com.example.onlinestore.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {
        RecyclerView recyclerView;
        UsersApi api;
        ViewPager slider;
        ImageView userimage,logout,user_profile_name,user_profile_email;
    public FragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.slidingImage);
        ImageSlideAdapter adapterView = new ImageSlideAdapter(this.getActivity());
        mViewPager.setAdapter(adapterView);


        recyclerView = view.findViewById(R.id.flash_sale_Recycler_view);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SharedPreferences preferences=getActivity().getSharedPreferences("UserData",0);
        String token=preferences.getString("token",null);
//        Log.d("token",token
        loadProducts();

        return view;
    }
    public void loadProducts(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api = retrofit.create(UsersApi.class);

        final Call<List<ItemsDetail>> listCall= api.getItemDetail();
        listCall.enqueue(new Callback<List<ItemsDetail>>() {
            @Override
            public void onResponse(Call<List<ItemsDetail>> call, Response<List<ItemsDetail>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                }
                List<ItemsDetail> itemsDetails = response.body();
//                Toast.makeText(getActivity(), "Body "+itemsDetails, Toast.LENGTH_LONG).show();
                recyclerView.setAdapter(new ItemAdapter(itemsDetails,getContext()));
            }

            @Override
            public void onFailure(Call<List<ItemsDetail>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
