package com.denatan.foodywishlist.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.denatan.foodywishlist.models.Food;

import java.util.List;

@Dao
public interface Access {
    @Insert
    Long insertFood(Food food);

    @Query("SELECT * FROM Food ORDER BY nama asc")
    LiveData<List<Food>> fetchAllFood();

    @Query("SELECT * FROM Food WHERE id =:taskId")
    LiveData<Food> getFood(int taskId);

    @Update
    void updateFood(Food note);

    @Delete
    void deleteFood(Food note);
}