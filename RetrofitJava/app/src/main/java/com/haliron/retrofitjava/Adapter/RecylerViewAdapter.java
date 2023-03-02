package com.haliron.retrofitjava.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haliron.retrofitjava.Model.CryptoModel;
import com.haliron.retrofitjava.R;

import java.util.ArrayList;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.RowHolder>
{
    private ArrayList<CryptoModel> cryptoList;
    private String [] colors = {"#46FF33","#23F6A0","#23A9F6","#9323F6","#F623E0","#F62360","#F67623","#D3F623"};

    public RecylerViewAdapter(ArrayList<CryptoModel> crptoList)
    {
        this.cryptoList = crptoList;
    }

    public class RowHolder extends RecyclerView.ViewHolder
    {
        TextView textName;
        TextView textPrice;
        public RowHolder(@NonNull View itemView)
        {
            super(itemView);
        }

        public  void  bind(CryptoModel cryptoModel , String[] colors, Integer position)
        {
            itemView.setBackgroundColor(Color.parseColor(colors[position% 8]));

            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);

            textName.setText(cryptoModel.currency);
            textPrice.setText(cryptoModel.price);
        }
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout,parent,false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position)
    {
        holder.bind(cryptoList.get(position),colors,position);
    }

    @Override
    public int getItemCount()
    {
        return cryptoList.size();
    }


}
