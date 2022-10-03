package com.example.lab_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class ActivitySecond extends AppCompatActivity {
    Film film;
    EditText countryEdit, taglineEdit, descriptionEdit;
    CheckBox localizationCheck;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        film = (Film) getIntent().getSerializableExtra("film");
        countryEdit = findViewById(R.id.countryEdit);
        taglineEdit = findViewById(R.id.taglineEdit);
        descriptionEdit = findViewById(R.id.descriptionEdit);
        localizationCheck = findViewById(R.id.localizationCheck);
        ratingBar = findViewById(R.id.ratingBar);




    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        EditText countryEdit = findViewById(R.id.countryEdit);
        outState.putString("country",countryEdit.getText().toString());
        super.onSaveInstanceState(outState);
        Log.d("SaveInsatnceState","сохранили state");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("RestoreInstance", "восстановили state");
        EditText countryEdit = findViewById(R.id.countryEdit);
        countryEdit.setText(savedInstanceState.getString("country"));
        System.out.println(savedInstanceState.getString("country"));
    }
    public void backBtnClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void nextBtnClick(View view){
        film.setCountry(countryEdit.getText().toString());
        film.setTagline(taglineEdit.getText().toString());
        film.setDescription(descriptionEdit.getText().toString());
        film.setIsLocalized(localizationCheck.isChecked());
        film.setRating(ratingBar.getRating());

        Intent intent = new Intent(this, ActivityThird.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("film", film);
        startActivity(intent);
    }

}
