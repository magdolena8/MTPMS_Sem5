package com.begdev.lab_5;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private static ArrayList<Event> events;
    public static int pickedID;
    private final OnEventClickListener onClickListener;

    private Context mContext;
    private Cursor mCursor;

    public EventAdapter(Context context, Cursor cursor, OnEventClickListener onClickListener) {
        mContext = context;
        mCursor = cursor;
        this.onClickListener = onClickListener;
        this.inflater = LayoutInflater.from(context);
    }

    interface OnEventClickListener {
        void onEventClick(int id);
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        int id = mCursor.getInt(mCursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_ID));
        String title = mCursor.getString(mCursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_TITLE));  //щас добавил если что...
        String description = mCursor.getString(mCursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DESCRIPTION));
        String imagePath = mCursor.getString(mCursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_IMAGE));

        holder.titleTW.setText(String.valueOf(title));
        holder.imageView.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(imagePath)));
        holder.descriptionTW.setText(String.valueOf(description));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onEventClick(id);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                pickedID = id;
//                holder.getPosition();//важно !!!
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
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
            MenuItem deleteItem = menu.add(0, v.getId(), 0, "Удалить");//groupId, itemId, order, title
            deleteItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    EventsDBHelper db = new EventsDBHelper(v.getContext());
                    db.deleteEvent(pickedID);
                    //TODO:RESET CURSOR
                    try {
                        Event.serializeEventsList(v.getContext());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            });
        }

    }


    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}