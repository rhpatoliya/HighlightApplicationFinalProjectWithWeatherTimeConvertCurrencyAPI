package com.example.highlightapplication.ui.GoldPrice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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


public class GoldpriceFragment extends Fragment implements SearchView.OnQueryTextListener,SearchView.OnCloseListener,
        NetworkingService.NetworkingListener , GoldPriceAdapter.CityclickListner {
    String TAG="WorldTimeFragment";

    Context appContext;
    GoldPriceAdapter adapter;
    ArrayList<GlobalCity> timezoneList = new ArrayList<>();
    NetworkingService networkingService;
    JsonService jsonService;
    RecyclerView recyclerView;
    SearchView search_view;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_worldtime, container, false);

        appContext=getActivity();
        networkingService = new NetworkingService(this);
        jsonService=new JsonService();

        search_view = view.findViewById(R.id.worldtime_searchview);
        recyclerView = view.findViewById(R.id.recyclerview_time);

        search_view.setOnQueryTextListener(this);
        search_view.setQueryHint("Search Gold Price");

        adapter = new GoldPriceAdapter(appContext,timezoneList,this);
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
            networkingService.fetchgold(newText);
        }
        else {
            timezoneList = new ArrayList<>(0);
            adapter.metalList=timezoneList;
            adapter.notifyDataSetChanged();
        }
        return false;
    }



    @Override
    public void APINetworkListner(String jsonString) {
        timezoneList =  jsonService.parseGoldJasonAPI(jsonString);
        adapter.metalList = timezoneList;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void WorlTimeNetworkListner(String jsonString, int position) {

    }


    @Override
    public void cityClicked(GlobalCity selectedCity) {
        Intent intent = new Intent(getActivity(),CurrencyDetailFragment.class);
        intent.putExtra("data",selectedCity);
        startActivity(intent);

        /*WorldDetalsTimeFragment fragment2 = new WorldDetalsTimeFragment(selectedCity);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment2);
        fragmentTransaction.commit();
*/

    }

    @Override
    public boolean onClose() {
        timezoneList = new ArrayList<>(0);
        adapter.metalList=timezoneList;
        adapter.notifyDataSetChanged();
        return true;
    }

}