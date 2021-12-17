package com.example.highlightapplication.ui.GoldPrice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.highlightapplication.GlobalCity;
import com.example.highlightapplication.R;
import com.example.highlightapplication.ui.Weather.WeatherAdapter;

import java.util.ArrayList;

public class GoldPriceAdapter extends RecyclerView.Adapter<GoldPriceAdapter.ViewHolder> {

    interface CityclickListner {
        public void cityClicked(GlobalCity selectedCity);
    }
    public Context mCtx;
    public ArrayList<GlobalCity> metalList;
    CityclickListner listner;

    public GoldPriceAdapter(Context context, ArrayList<GlobalCity> timezoneList, CityclickListner cityclickListner) {
        this.mCtx = context;
        this.metalList = timezoneList;
        listner = cityclickListner;
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.worldtime_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        GlobalCity t = metalList.get(position);
        holder.city_time.setText(t.getCityName() );
    }

    @Override
    public int getItemCount() {
        return metalList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView city_time, countryTextView;

        public ViewHolder( View itemView) {
            super(itemView);
            city_time = itemView.findViewById(R.id.city_time);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listner.cityClicked(metalList.get(getAdapterPosition()));
            Intent intent = new Intent(mCtx,CurrencyDetailFragment.class);
            intent.putExtra("data",metalList.get(getAdapterPosition()));
            mCtx.startActivity(intent);
        }
    }
}
