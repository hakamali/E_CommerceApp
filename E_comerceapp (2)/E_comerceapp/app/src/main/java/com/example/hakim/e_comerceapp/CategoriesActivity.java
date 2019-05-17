package com.example.hakim.e_comerceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
    }

    public void CategorySportList(View view) {
        Intent intent = new Intent(CategoriesActivity.this,CategorySportActivity.class);
        startActivity(intent);
    }

    public void CategoryTechnologyList(View view) {
        Intent intent = new Intent(CategoriesActivity.this,CategoryTechnologyActivity.class);
        startActivity(intent);
    }

    public void CategoryWearsList(View view) {
        Intent intent = new Intent(CategoriesActivity.this,CategoryWearsActivity.class);
        startActivity(intent);
    }

    public void CategoryCrockeryList(View view) {
        Intent intent = new Intent(CategoriesActivity.this,CategoryCrockeryActivity.class);
        startActivity(intent);
    }

    public void CategoryHouseholdList(View view) {
        Intent intent = new Intent(CategoriesActivity.this,CategoryHouseholdActivity.class);
        startActivity(intent);
    }

    public void CategoryAccessoriesList(View view) {
        Intent intent = new Intent(CategoriesActivity.this,CategoryAccessoriesActivity.class);
        startActivity(intent);
    }
}
