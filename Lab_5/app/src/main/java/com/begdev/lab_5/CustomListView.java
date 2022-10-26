package com.begdev.lab_5;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomListView extends ArrayAdapter<Event>{


    public CustomListView(@NonNull Context context, int resource, @NonNull List<Event> objects) {
        super(context, resource, objects);
    }
}
