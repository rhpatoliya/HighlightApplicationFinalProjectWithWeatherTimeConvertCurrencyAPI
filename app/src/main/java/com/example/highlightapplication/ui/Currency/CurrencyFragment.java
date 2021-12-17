package com.example.highlightapplication.ui.Currency;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.highlightapplication.R;
import com.example.highlightapplication.ui.APIService.JsonService;
import com.example.highlightapplication.ui.APIService.NetworkingService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Currency;


public class CurrencyFragment extends Fragment implements NetworkingService.NetworkingListener{
    
    RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    String TAG="StockpriceFragment";
    ProgressDialog progressDialog;
    ArrayList<String> currency_list=new ArrayList<>();
    Spinner spinner_to,spinner_from;
    ArrayAdapter<String> adapterTo;
    ArrayAdapter<String> adapterFrom;
    String Currency_To,Currency_from;
    int to_Position=0,from_Position=0;
    Button btn_convert,btn_savedata;
    TextView txt_currency;


    NetworkingService networkingService;
    JsonService jsonService;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currency, container, false);

        networkingService = new NetworkingService(this);
        jsonService=new JsonService();

        spinner_to=view.findViewById(R.id.spinner_to);
        spinner_from=view.findViewById(R.id.spinner_from);
        btn_convert=view.findViewById(R.id.btn_convert);
        btn_savedata = view.findViewById(R.id.btn_savedata);
        txt_currency=view.findViewById(R.id.txt_currency);

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching Currency Price Data");
        mRequestQueue= Volley.newRequestQueue(getActivity());
        setHasOptionsMenu(true);
        loadCurencyFromAsset();
        setCurrencyinDropdown();

        btn_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkingService.fetchCurrencyConvert(currency_list.get(from_Position),currency_list.get(to_Position));
            }
        });


        btn_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return view;
    }


    private void setCurrencyinDropdown() {
        adapterTo=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,currency_list);
        spinner_to.setAdapter(adapterTo);

        adapterFrom=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,currency_list);
        spinner_from.setAdapter(adapterFrom);

        spinner_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,"");
                to_Position=position;
                Currency_To=currency_list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                from_Position=position;
                Currency_from=currency_list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void loadCurencyFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("currency_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally {
            if(json!=null && json.length()>0){
                JSONObject jsonObject= null;
                try {
                    jsonObject = new JSONObject(json);
                    JSONArray jsonArray=jsonObject.getJSONArray("Currency");
                    for(int i=0;i<jsonArray.length();i++){
                        currency_list.add(jsonArray.get(i).toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void APINetworkListner(String jsonString) {
        ArrayList<String> currencylist=jsonService.parseCurrencyJsonAPI(jsonString, Currency_To,Currency_from);

        txt_currency.setText("1 "+Currency_from+" = "+ currencylist.get(0)+" "+Currency_To);
    }

    @Override
    public void WorlTimeNetworkListner(String jsonString, int position) {

    }
}