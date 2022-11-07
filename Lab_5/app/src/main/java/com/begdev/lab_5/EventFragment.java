package com.begdev.lab_5;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;      //если что-то не работает
import android.app.Fragment;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EventFragment extends Fragment {
    int ID;
    public String _path;
    public Event event;
    EditText titleTE, descriptionTE;
    TextView dateTW;
    View rootView;
    ImageView imageView;
    Button saveBtn, editBtn, deleteBtn;
    DatePicker datePicker;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ID = getArguments().getInt("ID");
//        event = (Event) getArguments().getSerializable("Event");
        EventsDBHelper db = new EventsDBHelper(getActivity());
        try {
            event = db.getEventByID(ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        event = Event.eventsList.get(ID);
        rootView = inflater.inflate(R.layout.fragment_event, container, false);

        saveBtn = rootView.findViewById(R.id.saveBtn);
        deleteBtn = rootView.findViewById(R.id.deleteBtn);
        editBtn = rootView.findViewById(R.id.editBtn);

        titleTE = rootView.findViewById(R.id.titleTE);
        titleTE.setText(event.title);
        descriptionTE = rootView.findViewById(R.id.descriptionTE);
        descriptionTE.setText(event.description);
        dateTW = rootView.findViewById(R.id.dateTW);
        String pattern = "dd MMMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        dateTW.setText(simpleDateFormat.format(event.date));
        imageView = rootView.findViewById(R.id.imageView);
        imageView.setImageBitmap(BitmapFactory.decodeFile(event.image));
        imageView.setOnClickListener(null);
        datePicker = rootView.findViewById(R.id.datePicker);
        datePicker.init(event.date.getYear(), event.date.getMonth(), event.date.getDay(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                return;
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBtn.setVisibility(View.VISIBLE);
                titleTE.setEnabled(true);
                descriptionTE.setEnabled(true);
                imageView.setClickable(true);
                datePicker.setVisibility(View.VISIBLE);
                dateTW.setVisibility(View.GONE);
                editBtn.setVisibility(View.GONE);
                imageView.setClickable(true);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent imgPickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(imgPickIntent, 1);
                        Log.d("AddImageBtn", "Pick image clicked");
                    }
                });
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBtn.setVisibility(View.GONE);
                editBtn.setVisibility(View.VISIBLE);
                event.title = titleTE.getText().toString();
                event.description = descriptionTE.getText().toString();
                event.date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
                event.image = _path;
                datePicker.setVisibility(View.GONE);
                dateTW.setVisibility(View.VISIBLE);
                titleTE.setEnabled(false);
                descriptionTE.setEnabled(false);
                String pattern = "dd MMMM yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                dateTW.setText(simpleDateFormat.format(event.date));
//TODO: диалог с подтверждением
                try {
                    EventsDBHelper db = new EventsDBHelper(getContext());
                    db.editEvent(ID, event);
//                    Event.serializeEventsList(rootView.getContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        return rootView;
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
                    _path = cursor.getString(columnIndex);
                    event.image = cursor.getString(columnIndex);
                    imageView.setImageBitmap(BitmapFactory.decodeFile(cursor.getString(columnIndex)));
                    cursor.close();
                    break;
            }
        }
    }
}
