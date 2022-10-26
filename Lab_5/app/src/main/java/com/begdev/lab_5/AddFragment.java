package com.begdev.lab_5;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;    //read about

import java.io.IOException;
import java.util.Date;


public class AddFragment extends Fragment {
    EditText titleTE, descriptionTE;
    DatePicker datePicker;
    Button addBtn;
    ImageView imageView;
    Event currentEvent = new Event();

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleTE = view.findViewById(R.id.titleTE);
        descriptionTE = view.findViewById(R.id.descriptionTE);
        datePicker = view.findViewById(R.id.datePicker);
        addBtn = view.findViewById(R.id.deleteBtn);
        imageView = view.findViewById(R.id.imageView);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentEvent.date = new Date((datePicker.getYear()-1900), datePicker.getMonth(), datePicker.getDayOfMonth());
                currentEvent.title = titleTE.getText().toString().matches("") ?
                        null:
                        titleTE.getText().toString();
                currentEvent.description = descriptionTE.getText().toString().matches("")?
                        null:
                        descriptionTE.getText().toString();

                try {
                    if(Event.addEvent(currentEvent, getActivity().getApplicationContext())){
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                "Сохранено", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                "Одибка ввода", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("OnAddBtnClick", "Add Button clicked");
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imgPickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imgPickIntent, 1);
                Log.d("AddImageBtn", "Pick image clicked");
            }
        });

        Log.d("AddFragment", "onViewCreated");
        return;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 1:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    currentEvent.image = cursor.getString(columnIndex);
                    imageView.setImageBitmap(BitmapFactory.decodeFile(cursor.getString(columnIndex)));
                    cursor.close();
                    break;
            }
        }
    }
}
