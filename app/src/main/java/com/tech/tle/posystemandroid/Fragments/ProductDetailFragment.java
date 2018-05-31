package com.tech.tle.posystemandroid.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.SubtitleCollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.tech.tle.posystemandroid.Adapters.StoreAdapter;
import com.tech.tle.posystemandroid.AppDatabase;
import com.tech.tle.posystemandroid.Application;
import com.tech.tle.posystemandroid.Helper.ClickListener;
import com.tech.tle.posystemandroid.Helper.GridSpacingItemDecoration;
import com.tech.tle.posystemandroid.Helper.RecyclerTouchListener;
import com.tech.tle.posystemandroid.Helper.Utility;
import com.tech.tle.posystemandroid.Models.MemoryData;
import com.tech.tle.posystemandroid.Models.Product;
import com.tech.tle.posystemandroid.R;

import java.util.ArrayList;
import java.util.List;

import static com.tech.tle.posystemandroid.Helper.ViewUtils.dpToPx;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
   private int quantityCount;
    private OnFragmentInteractionListener mListener;
    private SubtitleCollapsingToolbarLayout subtitleCollapsingToolbarLayout;
    private ImageView coverImage;
    private RecyclerView PDrecycleView;
    private StoreAdapter mAdapter;
    private List<Product> itemsList;
    public ProductDetailFragment() {
        // Required empty public constructor
    }


    public static ProductDetailFragment newInstance(String param1, String param2) {
        ProductDetailFragment fragment = new ProductDetailFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

       // subtitleCollapsingToolbarLayout = view.findViewById(R.id.toolbarLayout);
        coverImage = view.findViewById(R.id.coverImage);
        PDrecycleView = view.findViewById(R.id.PDrecycleView);


        itemsList = new ArrayList<>();
        mAdapter = new StoreAdapter(getActivity(), itemsList,1);

        TextView productTitle = (TextView)view.findViewById(R.id.title);
        TextView productPrice = (TextView)view.findViewById(R.id.price);
        TextView productSupportText = (TextView)view.findViewById(R.id.supporting_text);
        Button addCartutton = (Button)view.findViewById(R.id.action_button_1);


        addCartutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                MaterialDialog dialog =   new MaterialDialog.Builder(getActivity())
                        .customView(R.layout.add_cart_layout, false)

                        .positiveText("Add")
                        .negativeText("Cancel")
                        .show();

                final View materialView = dialog.getCustomView();


                TextView title_text = (TextView)materialView.findViewById(R.id.title);
                TextView price_text = (TextView)materialView.findViewById(R.id.price);
                TextView supporting_text = (TextView)materialView.findViewById(R.id.supporting_text);

                final EditText valueNumber = (EditText)materialView.findViewById(R.id.numbervalue);
                quantityCount=1;


                title_text.setText(MemoryData.activeProduct.getName());


                String price = Utility.formatDecimal(Double.valueOf(MemoryData.activeProduct.getUnitPrice()));
                price_text.setText(Application.AppCurrency+ " " +price);
              // supporting_text.setText("Total "+Application.AppCurrency+ " "+price + " x " + quantityCount + " = ");

                valueNumber.setText(String.valueOf(quantityCount));


            }
        });

        productTitle.setText(""+MemoryData.activeProduct.getName());

        String price = Utility.formatDecimal(Double.valueOf(MemoryData.activeProduct.getUnitPrice()));
        productPrice.setText(Application.AppCurrency+""+price);
        if(MemoryData.activeProduct.getDescription().isEmpty()){
            productSupportText.setText(getResources().getString(R.string.cehcek_out_instant_d));
        }else{
            productSupportText.setText(MemoryData.activeProduct.getDescription());
        }

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false); //new GridLayoutManager(getActivity(), 2);
        PDrecycleView.setLayoutManager(mLayoutManager);

        PDrecycleView.setAdapter(mAdapter);
        PDrecycleView.setNestedScrollingEnabled(false);



            Glide.with(getActivity())
                    .load(MemoryData.activeProduct.getImageUrl())
                    .into(coverImage);


            Log.d("ActiveProduct","activeP category ID " + MemoryData.activeProduct.getCategoryID());
            new getAllProductByCategoryAsync(MemoryData.activeProduct.getCategoryID()).execute();
       // }
        return  view;
       // return inflater.inflate(R.layout.fragment_product_detail, container, false);
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


    class getAllProductByCategoryAsync extends AsyncTask<Void, Void, Void>
    {

        private int categoryID;
        getAllProductByCategoryAsync(int categoryID){

            Log.d(getClass().getSimpleName(),"Constructor was called ");
            this.categoryID = categoryID;
        }

        List<Product> products;

        AppDatabase db = AppDatabase.getAppDatabase(getActivity());

        String TAG = getClass().getSimpleName();

        protected void onPreExecute (){
            super.onPreExecute();

        }

        protected Void doInBackground(Void... unused) {



            products = db.getProductDao().getAllProductByCategory(categoryID);
            Log.d("counterProduct","Product Counter "+  products.size());



            return null;
        }

        protected void onProgressUpdate(Integer...a){

        }

        protected void onPostExecute(Void result) {



                itemsList.clear();
                itemsList.addAll(products);
                mAdapter.notifyDataSetChanged();



                Log.d("ActiveProd","itemL count " + itemsList.size());




        }
    }
}
