package com.example.highlightapplication.ui.Highlight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.highlightapplication.R;
import com.example.highlightapplication.ui.HelperMethod;
import com.example.highlightapplication.ui.RoomDatabase.WeatherCity;

import java.util.List;

public class HomeWeatherAdapter extends RecyclerView.Adapter<HomeWeatherAdapter.ViewHolder> {

    String TAG="HomeAdapter";
    List<WeatherCity> weatherCities;
    Context appContext;

    HomeWeatherInterface buttonListener;

    public interface HomeWeatherInterface {
        void onRecyclerViewClick(WeatherCity weatherCity);

        void onClickForDelete(WeatherCity weatherCity, int adapterPosition);
    }

    public HomeWeatherAdapter(FragmentActivity activity, List<WeatherCity> weatherCities, HomeWeatherInterface buttonListener) {
        appContext=activity;
        this.weatherCities=weatherCities;
        this.buttonListener=buttonListener;
    }

    @NonNull
    @Override
    public HomeWeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(appContext).inflate(R.layout.home_weather_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeWeatherAdapter.ViewHolder holder, int position) {
        WeatherCity weatherCity=weatherCities.get(position);

        holder.txt_cityname.setText(weatherCity.cityName);
        holder.txt_temp.setText(HelperMethod.getTemptoC(weatherCity.temp)+" ");
//        holder.txt_cityname.setText(HelperMethod.ConvertTemp(weatherCity.temp)+" ");
    }

    @Override
    public int getItemCount() {
        return weatherCities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_cityname,txt_temp;
        LinearLayout line1;
        ImageView iv_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_cityname=itemView.findViewById(R.id.txt_cityname);
            txt_temp=itemView.findViewById(R.id.txt_temp);
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
