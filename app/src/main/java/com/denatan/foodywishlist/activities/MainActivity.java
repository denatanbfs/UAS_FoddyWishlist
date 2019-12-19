package com.denatan.foodywishlist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.denatan.foodywishlist.R;
import com.denatan.foodywishlist.fragments.FoodListFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleFoodList(View view) {
        Intent intent =  new Intent(this, FoodListActivity.class);
        startActivity(intent);
    }
}
