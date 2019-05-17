package com.example.hakim.e_comerceapp.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hakim.e_comerceapp.Interface.ItemClickListner;
import com.example.hakim.e_comerceapp.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
   public TextView textProductName,textProductDescription,textProductPrice;
   public ImageView imageView;
   public ItemClickListner listner;

    public ProductViewHolder(@NonNull View itemView)
    {
        super (itemView);
        imageView=(ImageView) itemView.findViewById (R.id.product_image);
        textProductName=(TextView) itemView.findViewById (R.id.product_name);
        textProductDescription=(TextView) itemView.findViewById (R.id.product_description);
        textProductPrice=(TextView) itemView.findViewById (R.id.product_price);
    }
    public  void setItemClickListner(ItemClickListner listner)
    {
      this.listner=listner;
    }

    @Override
    public void onClick(View v)
    {
        listner.onClick (v, getAdapterPosition (),false);
    }
}
