package com.tech.tle.posystemandroid.Adapters;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tech.tle.posystemandroid.Application;
import com.tech.tle.posystemandroid.Models.Product;
import com.tech.tle.posystemandroid.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by nykbMac on 27/05/2018.
 */



   public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {
        private Context context;
        private List<Product> productList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView name, price,time;
            public ImageView thumbnail;

            public MyViewHolder(View view) {
                super(view);
                name = view.findViewById(R.id.title);
                price = view.findViewById(R.id.price);
                thumbnail = view.findViewById(R.id.thumbnail);
                time = view.findViewById(R.id.time);
            }
        }


        public StoreAdapter(Context context, List<Product> productList) {
            this.context = context;
            this.productList = productList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.store_item_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {


            final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

            final Product product = productList.get(position);
            holder.name.setText(product.getName());
            holder.price.setText(Application.AppCurrency+" "+product.getUnitPrice());

            String dateStr = product.getDatecreated();
            Date date = null;
            try {
                date = inputFormat.parse(dateStr);
                String niceDateStr = DateUtils.getRelativeTimeSpanString(date.getTime() , Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS).toString();
                holder.time.setText(niceDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Glide.with(context)
                    .load(product.getImageUrl())
                    .into(holder.thumbnail);
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }
    }

