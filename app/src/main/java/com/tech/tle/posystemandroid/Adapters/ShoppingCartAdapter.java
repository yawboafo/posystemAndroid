package com.tech.tle.posystemandroid.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tech.tle.posystemandroid.Application;
import com.tech.tle.posystemandroid.Helper.Utility;
import com.tech.tle.posystemandroid.Models.Product;
import com.tech.tle.posystemandroid.Models.ShoppingCart;
import com.tech.tle.posystemandroid.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder> {
    private Context context;
    private List<ShoppingCart> productList;
    private int mini;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price,quantity,id;
        public ImageView thumbnail;
        public Button increment,decrement;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.title);
            price = view.findViewById(R.id.price);
            thumbnail = view.findViewById(R.id.thumbnail);
            quantity = view.findViewById(R.id.quantity);
            increment = view.findViewById(R.id.increment);
            decrement = view.findViewById(R.id.decrement);
            id = view.findViewById(R.id.productIDHiddenTextView);
        }
    }


    public ShoppingCartAdapter(Context context, List<ShoppingCart> productList) {
        this.context = context;
        this.productList = productList;
    }
    public ShoppingCartAdapter(Context context, List<ShoppingCart> productList,int mini) {
        this.context = context;
        this.productList = productList;
        this.mini = mini;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;

            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_cart_item_layout, parent, false);


        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

        final ShoppingCart product = productList.get(position);
        holder.name.setText(product.getProduct().getName());
        holder.price.setText(Application.AppCurrency+" "+Utility.formatDecimal(product.getTotal()));
        holder.id.setText(""+product.getId());
        holder.quantity.setText(""+product.getQuantity());




        String dateStr = product.getTimeStamp();
        Date date = null;
        try {
            date = inputFormat.parse(dateStr);
            String niceDateStr = DateUtils.getRelativeTimeSpanString(date.getTime() , Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS).toString();
         //   holder.time.setText(niceDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(context)
                .load(product.getProduct().getImageUrl())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
