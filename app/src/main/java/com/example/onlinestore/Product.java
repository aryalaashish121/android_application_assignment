package com.example.onlinestore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.onlinestore.Controller.ItemAdapter;
import com.example.onlinestore.Interface.UsersApi;
import com.example.onlinestore.Model.ItemsDetail;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Product extends AppCompatActivity {
    RecyclerView recyclerProduct;
    UsersApi api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        recyclerProduct = findViewById(R.id.recycler_product);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerProduct.addItemDecoration(itemDecoration);
        recyclerProduct.setLayoutManager(new GridLayoutManager(this,2));
        loadProducts();
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

        Call<List<ItemsDetail>> listCall= api.getItemDetail();
        listCall.enqueue(new Callback<List<ItemsDetail>>() {
            @Override
            public void onResponse(Call<List<ItemsDetail>> call, Response<List<ItemsDetail>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(Product.this, "Error", Toast.LENGTH_LONG).show();
                }
                List<ItemsDetail> itemsDetails = response.body();
                Toast.makeText(Product.this, "Body "+response.body(), Toast.LENGTH_LONG).show();
                recyclerProduct.setAdapter(new ItemAdapter(itemsDetails,Product.this));
            }

            @Override
            public void onFailure(Call<List<ItemsDetail>> call, Throwable t) {
                Toast.makeText(Product.this, "Error "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
