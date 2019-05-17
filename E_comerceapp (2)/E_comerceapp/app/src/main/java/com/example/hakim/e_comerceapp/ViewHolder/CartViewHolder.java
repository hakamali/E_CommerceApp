package com.example.hakim.e_comerceapp.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.hakim.e_comerceapp.Interface.ItemClickListner;
import com.example.hakim.e_comerceapp.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView textProductName,textProductPrice,textProductQuantity;
    private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super (itemView);

        textProductName=itemView.findViewById (R.id.cart_product_name);
        textProductPrice=itemView.findViewById (R.id.cart_product_price);
        textProductQuantity=itemView.findViewById (R.id.cart_product_quantity);


    }

    @Override
    public void onClick(View v)
    {
        itemClickListner.onClick (v,getAdapterPosition (),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner)
    {
        this.itemClickListner = itemClickListner;
    }
}
