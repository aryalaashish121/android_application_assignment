package com.example.onlinestore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ItemDescription extends AppCompatActivity {

    TextView iPrice,iName,iDesc;
    ImageView iImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);

        iImage = findViewById(R.id.itemImage);
        iPrice = findViewById(R.id.price);
        iName = findViewById(R.id.itemName);
        iDesc = findViewById(R.id.itemDescription);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
           iName.setText(bundle.getString("itemName"));
            iPrice.setText("RS "+(bundle.getString("itemPrice")));
            iDesc.setText(bundle.getString("itemDescription"));

            String itemImages = bundle.getString("itemImageName");
            Picasso.with(this).load(itemImages).into(iImage);

        }
    }
}
