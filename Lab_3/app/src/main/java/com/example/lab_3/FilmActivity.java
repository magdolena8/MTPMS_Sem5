package com.example.lab_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

public class FilmActivity extends Activity {
    private Film film;
    TextView titleTW, directorTW, raitingTW;

//    EditText mailEdit, phoneEdit, vkEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        film = (Film) getIntent().getSerializableExtra("film");

        titleTW = findViewById(R.id.titleTW);
        directorTW = findViewById(R.id.directorTW);
        raitingTW = findViewById(R.id.raitingTW);

        titleTW.setText(film.title);
        directorTW.setText(film.director);
        raitingTW.setText(Float.toString(film.rating));

    }
    public void onListBtnClick(View view){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
    public void onAddNewBtnCLick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
