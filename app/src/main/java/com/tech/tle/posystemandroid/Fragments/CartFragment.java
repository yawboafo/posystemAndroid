package com.tech.tle.posystemandroid.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tech.tle.posystemandroid.Adapters.ShoppingCartAdapter;
import com.tech.tle.posystemandroid.Adapters.StoreAdapter;
import com.tech.tle.posystemandroid.AppDatabase;
import com.tech.tle.posystemandroid.Application;
import com.tech.tle.posystemandroid.Helper.ClickListener;
import com.tech.tle.posystemandroid.Helper.GridSpacingItemDecoration;
import com.tech.tle.posystemandroid.Helper.RecyclerTouchListener;
import com.tech.tle.posystemandroid.Helper.Utility;
import com.tech.tle.posystemandroid.Models.MemoryData;
import com.tech.tle.posystemandroid.Models.Product;
import com.tech.tle.posystemandroid.Models.ShoppingCart;
import com.tech.tle.posystemandroid.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.tech.tle.posystemandroid.Helper.ViewUtils.dpToPx;


public class CartFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private List<ShoppingCart> itemsList;
    private ShoppingCartAdapter mAdapter;
    TextView textView ;
    TextView textcount ;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CartFragment() {

    }


    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

       // new getAllCartItemsAsync().execute();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        itemsList.addAll(MemoryData.getActiveShoppingCart());
        mAdapter.notifyDataSetChanged();

        Log.d("View status","View was created ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // return  inflater.inflate(R.layout.fragment_cart, container, false);


        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        itemsList = new ArrayList<>();

        if(MemoryData.activeShoppingCart == null)
            MemoryData.setActiveShoppingCart(Collections.<ShoppingCart>emptyList());
        mAdapter = new ShoppingCartAdapter(getActivity(), MemoryData.activeShoppingCart);
        textView = (TextView)view.findViewById(R.id.title);
        textcount = (TextView)view.findViewById(R.id.price);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(2), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(final View view, int position) {

               final TextView textView = (TextView)view.findViewById(R.id.quantity);
               final int quantity = Integer.parseInt(textView.getText().toString());

                Button incrementButton = (Button)view.findViewById(R.id.increment);
                incrementButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int newQuantity = quantity +1 ;
                        TextView textView = (TextView)view.findViewById(R.id.quantity);
                        textView.setText(""+newQuantity);


                        TextView hiddenID = (TextView)view.findViewById(R.id.productIDHiddenTextView);

                            ShoppingCart shoppingCartM = null;

                        for (ShoppingCart shoppingCart : MemoryData.getActiveShoppingCart()){


                            if(shoppingCart.getId() == Integer.parseInt(hiddenID.getText().toString())){

                                shoppingCartM = shoppingCart;
                                shoppingCartM.setQuantity(newQuantity);

                                return;

                            }

                        }


                        new InsertCartAsync(shoppingCartM).execute();

                    }
                });



                Button decrementButton = (Button)view.findViewById(R.id.decrement);
                decrementButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int newQuantity = quantity - 1 ;
                        TextView textView = (TextView)view.findViewById(R.id.quantity);
                        textView.setText(""+newQuantity);


                        TextView hiddenID = (TextView)view.findViewById(R.id.productIDHiddenTextView);

                        ShoppingCart shoppingCartM = null;

                        for (ShoppingCart shoppingCart : MemoryData.getActiveShoppingCart()){


                            if(shoppingCart.getId() == Integer.parseInt(hiddenID.getText().toString())){

                                shoppingCartM = shoppingCart;
                                shoppingCartM.setQuantity(newQuantity);

                                return;


                            }


                        }




                        new InsertCartAsync(shoppingCartM).execute();

                    }
                });

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        Log.d("View status","View was create ");
        new getAllCartItemsAsync().execute();


        //price
        Double total = 0.0;
        int qty = 0;

        for (ShoppingCart shoppingCart : MemoryData.getActiveShoppingCart()){

            System.out.println(shoppingCart.getName());

            total+=shoppingCart.getTotal();
            qty+=shoppingCart.getQuantity();
        }



        textcount.setText("" + qty + " Item ");

        textView.setText( Application.AppCurrency + " " + Utility.formatDecimal(total));

        return  view;

    }

    // TODO: Rename method, update argument and hook method into UI event



    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }


        Log.d("View status","View was attached ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class getAllCartItemsAsync extends AsyncTask<Void, Void, Void>
    {

        List<ShoppingCart> products;

        AppDatabase db = AppDatabase.getAppDatabase(getActivity());

        String TAG = getClass().getSimpleName();

        protected void onPreExecute (){
            super.onPreExecute();
           // MemoryData.activeShoppingCart = Collections.emptyList();

        }

        protected Void doInBackground(Void... unused) {

            products = db.getShoppingCartdDao().getAllShoppingCarts();
            MemoryData.setActiveShoppingCart(products);
            return null;
        }

        protected void onProgressUpdate(Integer...a){

        }

        protected void onPostExecute(Void result) {



                itemsList.clear();
                itemsList.addAll(products);
                mAdapter.notifyDataSetChanged();



            //price
            Double total = 0.0;
            int qty = 0;

            for (ShoppingCart shoppingCart : MemoryData.getActiveShoppingCart()){

                System.out.println(shoppingCart.getName());

                total+=shoppingCart.getTotal();
                qty+=shoppingCart.getQuantity();
            }



            textcount.setText("" + qty + " Item ");

            textView.setText( Application.AppCurrency + " " + Utility.formatDecimal(total));



        }
    }
    class InsertCartAsync extends AsyncTask<Void, Void, Void>
    {

        ShoppingCart ShoppingCart ;

        InsertCartAsync(ShoppingCart shoppingCart){

            this.ShoppingCart = shoppingCart;

        }

        String TAG = getClass().getSimpleName();

        protected void onPreExecute (){
            super.onPreExecute();

        }

        protected Void doInBackground(Void... unused) {




            AppDatabase db = AppDatabase.getAppDatabase(getActivity());
            ShoppingCart product1 = db.getShoppingCartdDao().findShoppingCartByName(ShoppingCart.getName());
            if (product1 == null) {
                Log.d("Found shopCART","FOUND Nothing ");

                db.getShoppingCartdDao().insert(ShoppingCart);
            } else {


                Log.d("Found shopCART","FOUND SOMETHING "+product1.getName() +" Quantity Previos : "+product1.getQuantity() + "Quantity new to add : "+ShoppingCart.getQuantity());

                int previousQty = ShoppingCart.getQuantity() + product1.getQuantity();

                product1.setQuantity( previousQty);
                db.getShoppingCartdDao().update(product1);
            }


            return null;
        }

        protected void onProgressUpdate(Integer...a){

        }

        protected void onPostExecute(Void result) {

           // new getAllCartItemsAsync().execute();

        }
    }
}
