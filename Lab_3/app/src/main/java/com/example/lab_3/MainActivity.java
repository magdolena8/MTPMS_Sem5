package com.example.lab_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Film film = new Film();
    static final String filmKey = "savedFilm";

    EditText titleEdit, directorEdit;
    DatePicker datePicker;
    Spinner genreSpiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleEdit = findViewById(R.id.mailEdit);
        directorEdit = findViewById(R.id.phoneEdit);
        datePicker = findViewById(R.id.calendar);

        genreSpiner = findViewById(R.id.genreSpinner);
        ArrayAdapter<?> genreAdapter = ArrayAdapter.createFromResource(this, R.array.genres, android.R.layout.simple_spinner_item);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpiner.setAdapter(genreAdapter);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putSerializable(filmKey, film);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
//        film = (Film)savedInstanceState.getSerializable(filmKey);
    }
    public void nextBtnClick(View view){
        film.setTitle(titleEdit.getText().toString());
        film.setDirector(directorEdit.getText().toString());
        film.setGenre(genreSpiner.getSelectedItem().toString());
        film.setDate(new Date(datePicker.getYear()-1900,datePicker.getMonth(),datePicker.getDayOfMonth()));
        Intent intent = new Intent(this, ActivitySecond.class);
        intent.putExtra("film", film);
        startActivity(intent);
    }
    public void listBtnClick(View view){
        Intent listActivityIntent = new Intent(this, ListActivity.class);
        startActivity(listActivityIntent);
    }
}