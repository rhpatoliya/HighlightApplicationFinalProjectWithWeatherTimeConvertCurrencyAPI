package com.example.highlightapplication.ui.WorldTime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.highlightapplication.GlobalCity;
import com.example.highlightapplication.R;
import com.example.highlightapplication.ui.APIService.JsonService;
import com.example.highlightapplication.ui.APIService.NetworkingService;
import com.example.highlightapplication.ui.Weather.WeatherAdapter;

import java.util.ArrayList;


public class WorldTimeFragment extends Fragment implements SearchView.OnQueryTextListener,
        NetworkingService.NetworkingListener , WorldTimeAdapter.CityclickListner {
    String TAG="WorldTimeFragment";

    Context appContext;
    WorldTimeAdapter adapter;
    ArrayList<GlobalCity> timezoneList = new ArrayList<>();
    NetworkingService networkingService;
    JsonService jsonService;
    RecyclerView recyclerView;
    SearchView search_view;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_worldtime, container, false);

        appContext=getActivity();
        networkingService = new NetworkingService(this);
        networkingService.fetchTimezoneData();
        jsonService=new JsonService();

        search_view = view.findViewById(R.id.worldtime_searchview);
        recyclerView = view.findViewById(R.id.recyclerview_time);

        search_view.setOnQueryTextListener(this);
        search_view.setQueryHint("Search timezone for World Time");

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG,"List size inside textsubmit="+timezoneList.size());
        adapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(TAG,"List size inside text change="+timezoneList.size());
        adapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public void APINetworkListner(String jsonString) {
        timezoneList =  jsonService.parseTimezoneAPIJson(jsonString);
        adapter = new WorldTimeAdapter(appContext,timezoneList,this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void WorlTimeNetworkListner(String jsonString, int position) {

    }


   @Override
    public void cityClicked(GlobalCity selectedCity) {
       Intent intent = new Intent(getActivity(),WorldTimeDetailsActivity.class);
       intent.putExtra("data",selectedCity);
       startActivity(intent);
    }
}