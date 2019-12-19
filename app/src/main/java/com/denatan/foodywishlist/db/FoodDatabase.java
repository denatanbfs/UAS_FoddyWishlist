package com.denatan.foodywishlist.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.denatan.foodywishlist.dao.Access;
import com.denatan.foodywishlist.models.Food;

@Database(entities = {Food.class}, version = 1, exportSchema = false)
public abstract class FoodDatabase extends RoomDatabase {
    public abstract Access access();
}