package com.tech.tle.posystemandroid.Fragments;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tech.tle.posystemandroid.Activities.ProductDetailActivity;
import com.tech.tle.posystemandroid.Adapters.StoreAdapter;
import com.tech.tle.posystemandroid.AppDatabase;
import com.tech.tle.posystemandroid.Helper.ClickListener;
import com.tech.tle.posystemandroid.Helper.GridSpacingItemDecoration;


import com.tech.tle.posystemandroid.Helper.RecyclerTouchListener;
import com.tech.tle.posystemandroid.Http.APIRequest;
import com.tech.tle.posystemandroid.HttpModels.ProductResponse;
import com.tech.tle.posystemandroid.Models.MemoryData;
import com.tech.tle.posystemandroid.Models.Product;
import com.tech.tle.posystemandroid.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tech.tle.posystemandroid.Helper.ViewUtils.dpToPx;


public class StoreFrontFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private APIRequest apiRequest;



    private static final String TAG = StoreFrontFragment.class.getSimpleName();



    private RecyclerView recyclerView;
    private List<Product> itemsList;
    private StoreAdapter mAdapter;



    private OnFragmentInteractionListener mListener;

    public StoreFrontFragment() {
        // Required empty public constructor
    }


    public static StoreFrontFragment newInstance(String param1, String param2) {
        StoreFrontFragment fragment = new StoreFrontFragment();
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
        apiRequest = new APIRequest();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //fetchStoreItems();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //return inflater.inflate(R.layout.fragment_store_front, container, false);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_front, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        itemsList = new ArrayList<>();
        mAdapter = new StoreAdapter(getActivity(), itemsList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(2), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {


                String value,transType;
                TextView id=(TextView)view.findViewById(R.id.productIDHiddenTextView);


                if (!id.getText().toString().isEmpty())
             new getProductAsync(id.getText().toString()).execute();
             //startActivity(new Intent(getActivity(), ProductDetailActivity.class));

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        new getAllProductAsync().execute();

        return view;
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        AppDatabase.destroyInstance();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }





    private void fetchStoreItems() {

        Map<String, Integer> params = new HashMap();
        params.put("organization_id", 82);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, APIRequest.getProductURL, new JSONObject(params),
                new Response.Listener<JSONObject>()
                {





                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        if (response == null) {
                            Toast.makeText(getActivity(), "Couldn't fetch the store items! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Log.d(TAG, "response " + response.toString());




                        ProductResponse productResponse = new Gson().fromJson(response.toString(), new TypeToken<ProductResponse>() {
                        }.getType());

                        itemsList.clear();
                        itemsList.addAll(productResponse.getData());
                        new InsertProductAsync(productResponse.getData()).execute();
                        Log.d(TAG, "itemsList.count: " + itemsList.size()  +" "+ productResponse.getData().get(0).getName());

                        mAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error in getting json
                        Log.e(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );








        apiRequest.addToRequestQueue(request);
    }


    private void insertProduct(List<Product> products){

        AppDatabase db = AppDatabase.getAppDatabase(getActivity());

        for (Product product : products){




            Product product1 = db.getProductDao().findProductByIDNow(product.getProductID());

            if (product1 == null)
                db.getProductDao().insert(product);
             else
                db.getProductDao().update(product);
        }

      /**  new AsyncTask<Void, Void, Void>() {
            protected void onPreExecute() {
                // Pre Code
            }
            protected Void doInBackground(Void... unused) {




                return null;
            }
            protected void onPostExecute(Void unused) {
                // Post Code
            }
        }.execute();
***/

    }

    class InsertProductAsync extends AsyncTask<Void, Void, Void>
    {

        List<Product> products;

        InsertProductAsync(List<Product> products){

            this.products = products;

        }

        String TAG = getClass().getSimpleName();

        protected void onPreExecute (){
            super.onPreExecute();

        }

        protected Void doInBackground(Void... unused) {

            insertProduct(products);
            return null;
        }

        protected void onProgressUpdate(Integer...a){

        }

        protected void onPostExecute(Void result) {


        }
    }


    class getAllProductAsync extends AsyncTask<Void, Void, Void>
    {

        List<Product> products;

        AppDatabase db = AppDatabase.getAppDatabase(getActivity());

        String TAG = getClass().getSimpleName();

        protected void onPreExecute (){
            super.onPreExecute();

        }

        protected Void doInBackground(Void... unused) {

            products = db.getProductDao().getAllProducts();
            return null;
        }

        protected void onProgressUpdate(Integer...a){

        }

        protected void onPostExecute(Void result) {

            if (products.size() <= 0){

                fetchStoreItems();
            }else{

                itemsList.clear();
                itemsList.addAll(products);
                mAdapter.notifyDataSetChanged();
            }




        }
    }

    class getProductAsync extends AsyncTask<Void, Void, Void>
    {




        Product product;

        AppDatabase db = AppDatabase.getAppDatabase(getActivity());

        String TAG = getClass().getSimpleName();

        String productID ;

        getProductAsync(String productID){

            this.productID = productID;

        }

        protected void onPreExecute (){
            super.onPreExecute();

        }

        protected Void doInBackground(Void... unused) {
            product = db.getProductDao().findProductByIDNow(Integer.valueOf(productID));
            MemoryData.setActiveProduct(product);
            return null;
        }

        protected void onProgressUpdate(Integer...a){

        }

        protected void onPostExecute(Void result) {

           // MemoryData.activeProduct = product;
            if(MemoryData.getActiveProduct() != null)
                startActivity(new Intent(getActivity(), ProductDetailActivity.class));
            else
           Toast.makeText(getContext(),"Product is Empty",Toast.LENGTH_LONG).show();


        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppDatabase.destroyInstance();
    }
}
