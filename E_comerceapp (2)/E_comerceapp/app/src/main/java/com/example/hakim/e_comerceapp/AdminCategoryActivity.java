package com.example.hakim.e_comerceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {
    private ImageView tshirts,sportsTshirts,femaleDresses,sweathers;
    private ImageView glasses,hatsCaps,walletBagsPurses,shoes;
    private ImageView headPhonesHandFree,Laptops,watches,mobilePhones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_admin_category);
        tshirts=findViewById (R.id.t_shirts);
        sportsTshirts=findViewById (R.id.sports_t_shirts);
        femaleDresses=findViewById (R.id.female_dresses);
        sweathers=findViewById (R.id.sweathers);
        glasses=findViewById (R.id.glasses);
        hatsCaps=findViewById (R.id.hats_caps);
        walletBagsPurses=findViewById (R.id.purses_bags_wallets);
        shoes=findViewById (R.id.shoes);
        headPhonesHandFree=findViewById (R.id.haedphones_handfree);
        Laptops=findViewById (R.id.laptop_pc);
        watches=findViewById (R.id.watches);
        mobilePhones=findViewById (R.id.mobilephones);

        tshirts.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra ("category","tshirts");
                startActivity (intent);
                }
        });
        sportsTshirts.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra ("category","sportsTshirts");
                startActivity (intent);
            }
        });
        femaleDresses.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra ("category","Female Dresses");
                startActivity (intent);

            }
        });
        sweathers.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra ("category","Sweathers");
                startActivity (intent);

            }
        });
        glasses.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra ("category","Glasses");
                startActivity (intent);

            }
        });
        hatsCaps.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra ("category","Hats Caps");
                startActivity (intent);

            }
        });
        walletBagsPurses.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra ("category","Wallets Bags Purses");
                startActivity (intent);

            }
        });
        shoes.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra ("category","Shoes");
                startActivity (intent);

            }
        });
        headPhonesHandFree.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra ("category","HeadPhones HandFree");
                startActivity (intent);
            }
        });
        Laptops.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra ("category","Laptops");
                startActivity (intent);

            }
        });
        watches.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra ("category","Watches");
                startActivity (intent);

            }
        });
        mobilePhones.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra ("category","Mobile Phones");
                startActivity (intent);

            }
        });

    }
}
