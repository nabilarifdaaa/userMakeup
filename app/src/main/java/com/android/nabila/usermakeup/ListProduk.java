package com.android.nabila.usermakeup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.nabila.usermakeup.Adapter.ProdukAdapter;
import com.android.nabila.usermakeup.Model.GetProduk;
import com.android.nabila.usermakeup.Model.Produk;
import com.android.nabila.usermakeup.Rest.ApiClient;
import com.android.nabila.usermakeup.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProduk extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String jenisProduk = "";

    ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_skincare);

        Intent mIntent = getIntent();
        jenisProduk = mIntent.getStringExtra("jenisProduk");

        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetProduk> mProdukByJenisCall = mApiInterface.getProdukByJenis(jenisProduk);
        mProdukByJenisCall.enqueue(new Callback<GetProduk>() {

            @Override
            public void onResponse(Call<GetProduk> call, Response<GetProduk> response) {
                Log.d("Get Produk", response.body().getStatus());
                List<Produk> listProduk= response.body().getResult();
                mAdapter = new ProdukAdapter(listProduk);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetProduk> call, Throwable t) {
                Log.d("Get Produk", t.getMessage());
            }
        });
    }
}
