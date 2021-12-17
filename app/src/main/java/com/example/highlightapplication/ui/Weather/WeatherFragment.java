package com.example.highlightapplication.ui.Weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.highlightapplication.GlobalCity;
import com.example.highlightapplication.R;
import com.example.highlightapplication.ui.APIService.JsonService;
import com.example.highlightapplication.ui.APIService.NetworkingService;

import java.util.ArrayList;


public class WeatherFragment extends Fragment implements SearchView.OnQueryTextListener,SearchView.OnCloseListener,
        NetworkingService.NetworkingListener , WeatherAdapter.CityclickListner {
    String TAG="WeatherFragment";

    Context appContext;
    WeatherAdapter adapter;
    ArrayList<GlobalCity> cities = new ArrayList<>();
    NetworkingService networkingService;
    JsonService jsonService;
    RecyclerView recyclerView;
    SearchView search_view;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        appContext=getActivity();
        networkingService = new NetworkingService(this);
        jsonService=new JsonService();

        search_view = view.findViewById(R.id.weather_searchview);
        recyclerView = view.findViewById(R.id.recyclerview);

        search_view.setOnQueryTextListener(this);
        search_view.setQueryHint("Search City for Weather");

        adapter = new WeatherAdapter(appContext,cities,this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() >= 3) {
           networkingService.fetchCitiesData(newText);
        }
        else {
            cities = new ArrayList<>(0);
            adapter.cityList=cities;
            adapter.notifyDataSetChanged();
        }
        return false;
    }



    @Override
    public void APINetworkListner(String jsonString) {
        cities =  jsonService.parseCitiesAPIJson(jsonString);
        adapter.cityList = cities;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void WorlTimeNetworkListner(String jsonString, int position) {

    }


    @Override
    public void cityClicked(GlobalCity selectedCity) {
        Intent intent=new Intent(getActivity(),WeatherDetailsActivity.class);
        intent.putExtra("data", (Parcelable) selectedCity);
        startActivity(intent);
    }

    @Override
    public boolean onClose() {
        cities = new ArrayList<>(0);
        adapter.cityList=cities;
        adapter.notifyDataSetChanged();
        return true;
    }
}