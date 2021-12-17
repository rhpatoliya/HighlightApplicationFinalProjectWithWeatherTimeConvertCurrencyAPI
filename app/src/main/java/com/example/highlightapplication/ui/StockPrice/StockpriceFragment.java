package com.example.highlightapplication.ui.StockPrice;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.highlightapplication.R;
import com.example.highlightapplication.ui.APIService.NetworkingService;
import com.example.highlightapplication.ui.HelperMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class StockpriceFragment extends Fragment implements SearchView.OnQueryTextListener, NetworkingService.NetworkingListener {
    RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    String TAG="StockpriceFragment";
    NetworkingService networkingService;
    StockPriceAdapter adapter;
    ProgressDialog progressDialog;
    RecyclerView recyclerview;
    SearchView search_view;
    ArrayList<StockData> stockDataArrayList=new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stockprice, container, false);
        search_view= view.findViewById(R.id.stockprice_searchview);
        recyclerview=view.findViewById(R.id.recyclerview);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching Stock data");
        mRequestQueue= Volley.newRequestQueue(getActivity());
        sendAndRequestResponse();


        search_view.setOnQueryTextListener(this);
        search_view.setQueryHint("Search City for Weather");
        setHasOptionsMenu(true);

        return view;
    }

    private void sendAndRequestResponse() {
        progressDialog.show();
        String url=NetworkingService.Stockurl+HelperMethod.getCurrentDate()+NetworkingService.Stockurl_2;
        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("results");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        StockData stockData=new StockData();
                        stockData.setName(jsonObject1.getString("T"));
                        stockData.setC(jsonObject1.getString("c"));
                        stockDataArrayList.add(stockData);
                    }
                    if(stockDataArrayList!=null && stockDataArrayList.size()>0){
                        adapter=new StockPriceAdapter(getActivity(),stockDataArrayList);
                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
                        recyclerview.setLayoutManager(linearLayoutManager);
                        recyclerview.setAdapter(adapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.getFilter().filter(query);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public void APINetworkListner(String jsonString) {
        Log.d(TAG,"Json string="+jsonString);
    }

    @Override
    public void WorlTimeNetworkListner(String jsonString, int position) {

    }
}