package com.haliron.retrofitjava.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.haliron.retrofitjava.Adapter.RecylerViewAdapter;
import com.haliron.retrofitjava.Model.CryptoModel;
import com.haliron.retrofitjava.R;
import com.haliron.retrofitjava.Service.CryptoAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{
    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL = "https://raw.githubusercontent.com/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecylerViewAdapter recylerViewAdapter;

    CompositeDisposable compositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        loadData();
    }

    private void loadData()
    {
       final CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);

       compositeDisposable = new CompositeDisposable();
       compositeDisposable.add(cryptoAPI.getData()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(this :: handleResponse));
       /*
        Call<List<CryptoModel>> call = cryptoAPI.getData();
         call.enqueue(new Callback<List<CryptoModel>>()
         {
             @Override
             public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response)
             {
                if (response.isSuccessful())
                {
                    List<CryptoModel> responseList = response.body();
                    cryptoModels = new ArrayList<>(responseList);

                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recylerViewAdapter = new RecylerViewAdapter(cryptoModels);
                    recyclerView.setAdapter(recylerViewAdapter);
                }
             }

             @Override
             public void onFailure(Call<List<CryptoModel>> call, Throwable t)
             {
                t.printStackTrace();
             }
         });
     */
    }

    private void handleResponse(List<CryptoModel> cryptoModelList)
    {
        cryptoModels = new ArrayList<>(cryptoModelList);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recylerViewAdapter = new RecylerViewAdapter(cryptoModels);
        recyclerView.setAdapter(recylerViewAdapter);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        compositeDisposable.clear();
    }
}