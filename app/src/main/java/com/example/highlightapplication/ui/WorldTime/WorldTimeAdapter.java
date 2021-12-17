package com.example.highlightapplication.ui.WorldTime;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.highlightapplication.GlobalCity;
import com.example.highlightapplication.R;

import java.util.ArrayList;

public class WorldTimeAdapter extends RecyclerView.Adapter<WorldTimeAdapter.ViewHolder> implements Filterable {

    String TAG="WorldTimeAdapter";
    interface CityclickListner {
        public void cityClicked(GlobalCity selectedCity);
    }
    public Context mCtx;
    public ArrayList<GlobalCity> timezoneList;
    public ArrayList<GlobalCity> stockDataArrayList;
    CityclickListner listner;

    public WorldTimeAdapter(Context context, ArrayList<GlobalCity> timezoneList, CityclickListner cityclickListner) {
        this.mCtx = context;
        this.timezoneList = timezoneList;
        this.stockDataArrayList = timezoneList;
        listner = cityclickListner;
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.worldtime_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        GlobalCity t = timezoneList.get(position);
        holder.city_time.setText(t.getCityName() );
    }

    @Override
    public int getItemCount() {
        return timezoneList.size();
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
            listner.cityClicked(timezoneList.get(getAdapterPosition()));
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    timezoneList = stockDataArrayList;
                    Log.d(TAG,"stockDataArrayList="+stockDataArrayList.size());
                } else {
                    ArrayList<GlobalCity> filteredList = new ArrayList<>();
                    for (GlobalCity row : stockDataArrayList) {

                        if (row.getCityName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    timezoneList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = timezoneList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                timezoneList = (ArrayList<GlobalCity>) results.values;
                notifyDataSetChanged();
            }
        };

    }
}
