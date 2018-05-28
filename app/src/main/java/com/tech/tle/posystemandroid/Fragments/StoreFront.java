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
import com.tech.tle.posystemandroid.Models.Product;
import com.tech.tle.posystemandroid.ModelsDAO.ProductDao;
import com.tech.tle.posystemandroid.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.tech.tle.posystemandroid.Helper.ViewUtils.dpToPx;


public class StoreFront extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private APIRequest apiRequest;



    private static final String TAG = StoreFront.class.getSimpleName();



    private RecyclerView recyclerView;
    private List<Product> itemsList;
    private StoreAdapter mAdapter;



    private OnFragmentInteractionListener mListener;

    public StoreFront() {
        // Required empty public constructor
    }


    public static StoreFront newInstance(String param1, String param2) {
        StoreFront fragment = new StoreFront();
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

             startActivity(new Intent(getActivity(), ProductDetailActivity.class));

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        fetchStoreItems();

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
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }





    private void fetchStoreItems() {



        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, APIRequest.getProductURL, null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        if (response == null) {
                            Toast.makeText(getActivity(), "Couldn't fetch the store items! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Log.e(TAG, "response " + response.toString());

                        // List<Product> items = new Gson().fromJson(response.toString(), new TypeToken<List<Product>>() {
                        //}.getType());


                        ProductResponse productResponse = new Gson().fromJson(response.toString(), new TypeToken<ProductResponse>() {
                        }.getType());

                        itemsList.clear();
                        itemsList.addAll(productResponse.getData());

                    //    insertProduct(productResponse.getData());

                             new InsertProductAsync(productResponse.getData()).execute();
                        Log.e(TAG, "itemsList.count: " + itemsList.size()  +" "+ productResponse.getData().get(0).getName());
                        // refreshing recycler view
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

}
