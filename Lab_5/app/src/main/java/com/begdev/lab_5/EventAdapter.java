package com.begdev.lab_5;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private static ArrayList<Event> events;
    public static int pickedID;
    public static Context context;
    private final OnEventClickListener onClickListener;

    EventAdapter(Context context, ArrayList<Event> events, OnEventClickListener onClickListener) {
        this.events = events == null ?
                new ArrayList<Event>() :
                events;
        this.context = context;
        this.onClickListener = onClickListener;
        this.inflater = LayoutInflater.from(context);
    }

    interface OnEventClickListener {
        void onEventClick(Event event, int position);
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
        Event event = Event.eventsList.get(position);
        holder.imageView.setImageBitmap(BitmapFactory.decodeFile(event.image));
        holder.titleTW.setText(event.title);
        holder.descriptionTW.setText(event.description);
//        setImageBitmap(BitmapFactory.decodeFile(film.image));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onEventClick(event, position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                pickedID = holder.getPosition();


                holder.getPosition();//важно !!!
                Log.d("LOOOOng", "LOOOONG");
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return Event.eventsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        final ImageView imageView;
        final TextView titleTW, descriptionTW;

        ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.item_image);
            titleTW = view.findViewById(R.id.item_title);
            descriptionTW = view.findViewById(R.id.item_description);
            view.setOnCreateContextMenuListener(this);
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

//            menu.setHeaderTitle("Select The Action");
            MenuItem deleteItem =  menu.add(0, v.getId(), 0, "Удалить");//groupId, itemId, order, title
            deleteItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    Log.d("Menu", "DELETEEE");
//                    Log.d("Menu", pickedID);
//                    int i = holder.getPosition();
                    Event.eventsList.remove(pickedID);
//                    FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
//                    FragmentTransaction tr =  manager.beginTransaction();
//                    tr.replace(R.id.container_main, new ListFragment());
//                    tr.commit();
                    try {
                        Event.serializeEventsList(v.getContext());
                    }catch (IOException e){e.printStackTrace();}
                    return true;
                }
            });
        }


    }
}