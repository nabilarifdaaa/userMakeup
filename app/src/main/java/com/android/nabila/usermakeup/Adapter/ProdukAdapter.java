package com.android.nabila.usermakeup.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.nabila.usermakeup.Model.Produk;
import com.android.nabila.usermakeup.R;
import com.android.nabila.usermakeup.Rest.ApiClient;
import com.bumptech.glide.Glide;

import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ProdukViewHolder> {
    List<Produk> listProduk;
    public ProdukAdapter(List<Produk> listProduk) {
        this.listProduk = listProduk;
    }

    @Override
    public ProdukAdapter.ProdukViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_skincare, parent, false);
        ProdukAdapter.ProdukViewHolder mHolder = new ProdukAdapter.ProdukViewHolder(view);
        return mHolder;

    }

    @Override
    public void onBindViewHolder(ProdukAdapter.ProdukViewHolder holder, final int position) {
        holder.tvIdProduk.setText(listProduk.get(position).getIdProduk());
        holder.tvNama.setText(listProduk.get(position).getNamaProduk());
        holder.tvJenis.setText(listProduk.get(position).getJenisProduk());
        holder.tvHarga.setText(listProduk.get(position).getHargaProduk());
        if (listProduk.get(position).getPhotoUrl() != null ){
            Glide.with(holder.itemView.getContext()).load(ApiClient.BASE_URL + listProduk.get
                    (position).getPhotoUrl())
                    .into(holder.mPhotoURL);
        } else {
            Glide.with(holder.itemView.getContext()).load(R.drawable.default_user).into(holder
                    .mPhotoURL);
        }

    }

    @Override
    public int getItemCount() {
        return listProduk.size();
    }

    public class ProdukViewHolder extends RecyclerView.ViewHolder {
        ImageView mPhotoURL;
        TextView tvIdProduk, tvNama,tvJenis,tvHarga;

        public ProdukViewHolder(View itemView) {
            super(itemView);
            mPhotoURL =  itemView.findViewById(R.id.imgPembeli);
            tvIdProduk= itemView.findViewById(R.id.tvIdProduk);
            tvJenis= itemView.findViewById(R.id.tvJenis);
            tvNama =  itemView.findViewById(R.id.tvNamaProduk);
            tvHarga= itemView.findViewById(R.id.tvHarga);

        }
    }
}
