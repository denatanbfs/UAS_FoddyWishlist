package com.denatan.foodywishlist.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denatan.foodywishlist.R;
import com.denatan.foodywishlist.activities.TambahFoodActivity;
import com.denatan.foodywishlist.adapter.FoodAdapter;
import com.denatan.foodywishlist.models.Food;
import com.denatan.foodywishlist.repository.FoodRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.denatan.foodywishlist.util.AppConstants.ACTIVITY_REQUEST_CODE;
import static com.denatan.foodywishlist.util.AppConstants.INTENT_DELETE;
import static com.denatan.foodywishlist.util.AppConstants.INTENT_FOOD;
import static com.denatan.foodywishlist.util.AppConstants.INTENT_LOKASI;
import static com.denatan.foodywishlist.util.AppConstants.INTENT_NAMA;
import static com.denatan.foodywishlist.util.AppConstants.INTENT_NO_TELP;

public class FoodListFragment extends Fragment implements View.OnClickListener {

    TextView emptyView, txtFoodList;
    FoodAdapter foodAdapter;
    FloatingActionButton floatingActionButton;
    RecyclerView rvFood;

    public static final String SWITCH_KEY = "switch";

    public FoodListFragment() {
        // Required empty public constructor
    }

    private SharedPreferences preferences;

    private FoodRepository foodRepository;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        boolean switchValue = preferences.getBoolean(SWITCH_KEY, false);
        if (switchValue) {
            txtFoodList.setVisibility(View.GONE);
        } else {
            txtFoodList.setVisibility(View.VISIBLE);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food_list, container, false);

        foodRepository = new FoodRepository(getActivity().getApplicationContext());
        rvFood = view.findViewById(R.id.rvFood);
        txtFoodList = view.findViewById(R.id.txtFoodList);

        floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);

        emptyView = view.findViewById(R.id.empty_view);



        rvFood.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        rvFood.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rvFood, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                Food food= foodAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), TambahFoodActivity.class);
                intent.putExtra(INTENT_FOOD, food);
                startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
            }
        }));

        updateFoodList();
        return view;
    }

    private void updateFoodList() {
        foodRepository.getFood().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                if (foods.size() > 0) {
                    emptyView.setVisibility(View.GONE);
                    rvFood.setVisibility(View.VISIBLE);
                    foodAdapter = new FoodAdapter(foods, getActivity());
                    rvFood.setAdapter(foodAdapter);
                } else updateEmptyView();
            }
        });
    }

    private void updateEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        rvFood.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), TambahFoodActivity.class);
        startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            if(data.hasExtra(INTENT_FOOD)) {
                if(data.hasExtra(INTENT_DELETE)) {
                    foodRepository.deleteFood((Food) data.getSerializableExtra(INTENT_FOOD));

                } else {
                    foodRepository.updateFood((Food) data.getSerializableExtra(INTENT_FOOD));
                }
            } else {
                String nama = data.getStringExtra(INTENT_NAMA);
                String lokasi = data.getStringExtra(INTENT_LOKASI);
                String noTelp = data.getStringExtra(INTENT_NO_TELP);
                foodRepository.insertFood(nama, lokasi, noTelp);
            }
            updateFoodList();
        }
    }

    public static interface ClickListener {
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}