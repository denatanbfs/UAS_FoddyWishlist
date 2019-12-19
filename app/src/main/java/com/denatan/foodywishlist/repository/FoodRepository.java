package com.denatan.foodywishlist.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.denatan.foodywishlist.db.FoodDatabase;
import com.denatan.foodywishlist.models.Food;

import java.util.List;

public class FoodRepository {
    public static final String DB_NAME = "db_food";

    private FoodDatabase foodDatabase;

    public FoodRepository(Context context) {
        foodDatabase = Room.databaseBuilder(context, FoodDatabase.class, DB_NAME).build();
    }

    public void insertFood(String nama, String lokasi, String notelp) {
        Food food = new Food();
        food.setNama(nama);
        food.setLokasi(lokasi);
        food.setNotelp(notelp);

        insertFood(food);
    }
    public void insertFood(final Food food) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                foodDatabase.access().insertFood(food);
                return null;
            }
        }.execute();
    }

    public void updateFood(final Food food) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                foodDatabase.access().updateFood(food);
                return null;
            }
        }.execute();
    }

    public void deleteFood(final int id) {
        final LiveData<Food> food = getFood(id);
        if (food != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    foodDatabase.access().deleteFood(food.getValue());
                    return null;
                }
            }.execute();
        }
    }

    public void deleteFood(final Food food) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                foodDatabase.access().deleteFood(food);
                return null;
            }
        }.execute();
    }

    public LiveData<Food> getFood(int id) {
        return foodDatabase.access().getFood(id);
    }

    public LiveData<List<Food>> getFood() {
        return foodDatabase.access().fetchAllFood();
    }
}