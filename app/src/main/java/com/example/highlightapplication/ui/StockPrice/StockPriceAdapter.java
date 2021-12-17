package com.example.highlightapplication.ui.StockPrice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.highlightapplication.GlobalCity;
import com.example.highlightapplication.R;
import com.example.highlightapplication.ui.Weather.WeatherAdapter;

import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

public class StockPriceAdapter extends RecyclerView.Adapter<StockPriceAdapter.ViewHolder> implements Filterable {
    Context appContext;
    ArrayList<StockData> stockDataList;
    ArrayList<StockData> stockDataArrayList;

    public StockPriceAdapter(Context activity, ArrayList<StockData> stockDataArrayList) {
        appContext=activity;
        stockDataList=stockDataArrayList;
        this.stockDataArrayList=stockDataArrayList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(appContext).inflate(R.layout.stockprice_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StockData stockData1 = stockDataList.get(position);
        holder.txt_stockprice.setText(stockData1.getC() + " " +"USD" );
        holder.txt_stockname.setText(stockData1.getName() );
    }

    @Override
    public int getItemCount() {
        return stockDataList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    stockDataList = stockDataArrayList;
                } else {
                    ArrayList<StockData> filteredList = new ArrayList<>();
                    for (StockData row : stockDataArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    stockDataList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = stockDataList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                stockDataList = (ArrayList<StockData>) results.values;
                notifyDataSetChanged();
            }
        };

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_stockname, txt_stockprice;
        public ViewHolder(View itemView) {
            super(itemView);
            txt_stockname = itemView.findViewById(R.id.txt_stockname);
            txt_stockprice = itemView.findViewById(R.id.txt_stockprice);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            //listner.cityClicked(cityList.get(getAdapterPosition()));
        }
    }

}
