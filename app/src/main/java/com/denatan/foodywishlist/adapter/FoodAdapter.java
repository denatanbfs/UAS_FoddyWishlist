package com.denatan.foodywishlist.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denatan.foodywishlist.R;
import com.denatan.foodywishlist.models.Food;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {
    List<Food> foodList;
    private Context context;

    public FoodAdapter(List<Food> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View foodView;
        foodView = layoutInflater.inflate(R.layout.food_item, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(foodView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.MyViewHolder holder, final int position) {
        Food food = foodList.get(position);

        holder.foodName.setText(food.getNama());
        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getItem(position).getNotelp()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (foodList != null) ? foodList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView foodName;
        ImageView phone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.txtNama);
            phone = itemView.findViewById(R.id.btnPhone);
        }
    }

    public Food getItem(int position) {
        return foodList.get(position);
    }
}