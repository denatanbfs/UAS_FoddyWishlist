package com.denatan.foodywishlist.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denatan.foodywishlist.R;
import com.denatan.foodywishlist.models.Food;

import static com.denatan.foodywishlist.util.AppConstants.INTENT_DELETE;
import static com.denatan.foodywishlist.util.AppConstants.INTENT_FOOD;
import static com.denatan.foodywishlist.util.AppConstants.INTENT_LOKASI;
import static com.denatan.foodywishlist.util.AppConstants.INTENT_NAMA;
import static com.denatan.foodywishlist.util.AppConstants.INTENT_NO_TELP;

public class TambahFoodActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private EditText editNama, editLokasi, editNotelp;
    private TextView btnDone, toolbarTitle;
    private ImageView btnDelete, btnPhone;
    private ConstraintLayout contraintLayout;

    private Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_food);

        contraintLayout = findViewById(R.id.constraint_layout);

        toolbarTitle = findViewById(R.id.title);
        editNama = findViewById(R.id.editNama);
        editNotelp = findViewById(R.id.editNotelp);
        editLokasi = findViewById(R.id.editLokasi);

        btnDelete = findViewById(R.id.btn_close);
        btnDelete.setOnClickListener(this);

        btnDone = findViewById(R.id.btn_done);
        btnDone.setOnClickListener(this);

        btnPhone = findViewById(R.id.button_phone);

        food = (Food) getIntent().getSerializableExtra(INTENT_FOOD);
        if (food == null) {
            contraintLayout.setFocusableInTouchMode(false);
            btnPhone.setVisibility(View.GONE);
            toolbarTitle.setText("Tambah Food");
            btnDelete.setImageResource(R.drawable.button_close);
            btnDelete.setTag(R.drawable.button_close);
        } else {
            contraintLayout.setFocusableInTouchMode(true);
            toolbarTitle.setText("Food Detail");

            btnPhone.setVisibility(View.VISIBLE);
            btnPhone.setOnClickListener(this);

            btnDelete.setImageResource(R.drawable.button_trash);
            btnDelete.setTag(R.drawable.button_trash);
            if (food.getNama() != null && !food.getNama().isEmpty()) {
                editNama.setText(food.getNama());
                editNama.setSelection(editNama.getText().length());
            }
            if (food.getNotelp() != null && !food.getNotelp().isEmpty()) {
                editNotelp.setText(food.getNotelp());
                editNotelp.setSelection(editNotelp.getText().length());
            }
            if (food.getLokasi() != null && !food.getLokasi().isEmpty()) {
                editLokasi.setText(food.getLokasi());
                editLokasi.setSelection(editLokasi.getText().length());
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnDelete) {
            if((Integer)btnDelete.getTag() == R.drawable.button_close) {
                setResult(Activity.RESULT_CANCELED);

            } else {
                Intent intent = getIntent();
                intent.putExtra(INTENT_DELETE, true);
                intent.putExtra(INTENT_FOOD, food);
                setResult(Activity.RESULT_OK, intent);
            }

            finish();
            overridePendingTransition(R.anim.stay, R.anim.slide_down);
        } else if (view == btnDone) {
            if (editNama.getText().toString().isEmpty() || editNotelp.getText().toString().isEmpty() || editLokasi.getText().toString().isEmpty()) {
                Toast.makeText(this, "Informasi kontak harus lengkap", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = getIntent();
                if(food != null) {
                    food.setNama(editNama.getText().toString());
                    food.setNotelp(editNotelp.getText().toString());
                    food.setLokasi(editLokasi.getText().toString());
                    intent.putExtra(INTENT_FOOD, food);
                } else {
                    intent.putExtra(INTENT_NAMA, editNama.getText().toString());
                    intent.putExtra(INTENT_NO_TELP, editNotelp.getText().toString());
                    intent.putExtra(INTENT_LOKASI, editLokasi.getText().toString());
                }
                setResult(Activity.RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.stay, R.anim.slide_down);
            }
        } else if (view == btnPhone) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + food.getNotelp()));
            startActivity(intent);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}