package com.example.pharm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private List<Drug> listData;

    public ShopAdapter(List<Drug> listData) {
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Drug myListData = listData.get(position);
        holder.textView.setText(myListData.getName());
        Picasso.get().load(myListData.getImageUrl()).into(holder.imageView);
        holder.q.setText(myListData.getQuantity());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Clicked item: " + myListData.getName(), Toast.LENGTH_LONG).show();
            }
        });
        holder.addbag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = myListData.getName();
                Toast.makeText(v.getContext(), "Added to cart: " + itemName, Toast.LENGTH_SHORT).show();
                Context context = v.getContext();
                Intent intent = new Intent(context, cart.class);
                intent.putExtra("itemName", itemName);
                context.startActivity(intent);
                String editnum = myListData.getQuantity().trim();
                int eq = Integer.parseInt(editnum);
                eq=eq-1;
                holder.q.setText(""+eq);



            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView q;
        Button addbag;
        public ConstraintLayout constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            q = itemView.findViewById(R.id.qnum);
            addbag = itemView.findViewById(R.id.addtobag);
            constraintLayout = itemView.findViewById(R.id.constraint);
        }
    }
}
