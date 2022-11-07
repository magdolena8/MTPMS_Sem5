package com.begdev.lab_5;

import android.Manifest;
import android.app.Activity;
import android.app.Person;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.MediaCodec;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import android.app.Fragment;
import android.widget.SearchView;

import androidx.core.app.ActivityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ListFragment extends Fragment {
    RecyclerView recyclerView;
    EventAdapter.OnEventClickListener listener;
    interface OnFragmentSendDataListener {
        void onSendData(String data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.list);
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        setHasOptionsMenu(true);
        listener = new EventAdapter.OnEventClickListener() {
            @Override
            public void onEventClick(int id) {
                Fragment fr = new EventFragment();
                Bundle args = new Bundle();
                args.putInt("ID", id);
//                args.putSerializable("Event", event);
                fr.setArguments(args);
                Log.d("List", "list item pressed");
                if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.details_fragment_view, fr).commit();
                else
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.container_main, fr).addToBackStack(null).commit();
            }
        };

        EventsDBHelper helper = new EventsDBHelper(getActivity());
        EventAdapter eventAdapter = new EventAdapter(getActivity().getApplicationContext(),helper.getAllEvents() , listener);
        recyclerView.setAdapter(eventAdapter);
        return view;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
    @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
//        getActivity().getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.search_mbtn);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        ArrayList<Event> searchResults = new ArrayList<>();
        getActivity().getMenuInflater().inflate(R.menu.main, menu);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Pattern pattern =  Pattern.compile(s);
                for(Event event: Event.eventsList){
                    if(pattern.matcher(event.title).find()){
                        searchResults.add(event);
                    }
                }
                EventAdapter eventAdapter = new EventAdapter(getActivity().getApplicationContext(), null, listener);
                recyclerView.setAdapter(eventAdapter);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, menuInflater);
    }
}
