package com.example.highlightapplication.ui.Highlight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.highlightapplication.R;
import com.example.highlightapplication.ui.RoomDatabase.WeatherCity;
import com.example.highlightapplication.ui.RoomDatabase.WorldTimeZone;

import java.util.ArrayList;
import java.util.List;

public class HomeWorldTimeAdapter extends RecyclerView.Adapter<HomeWorldTimeAdapter.ViewHolder> {

    String TAG="HomeAdapter";
    List<WorldTimeZone> weatherCities;
    Context appContext;

    HomeWorldTimeInterface buttonListener;

    public interface HomeWorldTimeInterface {
        void onRecyclerViewClick(WorldTimeZone weatherCity);

        void onClickForDelete(WorldTimeZone weatherCity, int adapterPosition);
    }

    public HomeWorldTimeAdapter(FragmentActivity activity, ArrayList<WorldTimeZone> weatherCities, HomeWorldTimeInterface buttonListener) {
        appContext=activity;
        this.weatherCities=weatherCities;
        this.buttonListener=buttonListener;
    }

    @NonNull
    @Override
    public HomeWorldTimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(appContext).inflate(R.layout.home_worldtime_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeWorldTimeAdapter.ViewHolder holder, int position) {
        WorldTimeZone weatherCity=weatherCities.get(position);

        holder.txt_cityname.setText(weatherCity.city_name);
        holder.timeText.setTimeZone(weatherCity.city_name);
    }

    @Override
    public int getItemCount() {
        return weatherCities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_cityname;
        TextClock timeText;
        LinearLayout line1;
        ImageView iv_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_cityname=itemView.findViewById(R.id.txt_cityname);
            timeText=itemView.findViewById(R.id.timeText);
            line1=itemView.findViewById(R.id.line1);
            iv_delete=itemView.findViewById(R.id.iv_delete);
            txt_cityname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonListener.onRecyclerViewClick(weatherCities.get(getAdapterPosition()));
                }
            });
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   buttonListener.onClickForDelete(weatherCities.get(getAdapterPosition()),getAdapterPosition());
                }
            });
        }
    }
}
