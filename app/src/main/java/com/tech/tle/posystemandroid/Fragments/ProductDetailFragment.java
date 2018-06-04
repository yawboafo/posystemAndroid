package com.tech.tle.posystemandroid.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.afollestad.materialdialogs.DialogAction;
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
import com.tech.tle.posystemandroid.Models.ShoppingCart;
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
   private static Product activeproduct;
    public ProductDetailFragment() {
        // Required empty public constructor
    }


    public static ProductDetailFragment newInstance(Product param1) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
         activeproduct = param1;
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
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                EditText valueNumber =  (EditText)dialog.getCustomView().findViewById(R.id.numbervalue);
                                if(!valueNumber.getText().toString().isEmpty() || Integer.valueOf(valueNumber.getText().toString()) >0){

                                    ShoppingCart shoppingCart = new ShoppingCart();
                                    shoppingCart.setProduct(activeproduct);
                                    shoppingCart.setProductID(activeproduct.getProductID()+"");
                                    shoppingCart.setName(activeproduct.getName());
                                    //EditText valueNumber =  (EditText)dialog.getCustomView().findViewById(R.id.numbervalue);
                                    quantityCount = Integer.parseInt(valueNumber.getText().toString());
                                    shoppingCart.setQuantity(quantityCount);
                                    shoppingCart.setTimeStamp(Utility.getCurrentTimeStamp());

                                    Double total = activeproduct.getUnitPrice() * Double.parseDouble(quantityCount+"");
                                    shoppingCart.setTotal(Double.parseDouble(total.toString()));
                                    shoppingCart.setUserid(1+"");

                                    new InsertCartAsync(shoppingCart).execute();


                                }
                            }
                        })
                        .show();


                final View materialView = dialog.getCustomView();


                TextView title_text = (TextView)materialView.findViewById(R.id.title);
                TextView price_text = (TextView)materialView.findViewById(R.id.price);
                TextView supporting_text = (TextView)materialView.findViewById(R.id.supporting_text);

                final EditText valueNumber = (EditText)materialView.findViewById(R.id.numbervalue);
                quantityCount=1;


                title_text.setText(activeproduct.getName());


                String price = Utility.formatDecimal(Double.valueOf(activeproduct.getUnitPrice()));
                price_text.setText(Application.AppCurrency+ " " +price);
              // supporting_text.setText("Total "+Application.AppCurrency+ " "+price + " x " + quantityCount + " = ");

                valueNumber.setText(String.valueOf(quantityCount));
                valueNumber.requestFocus();

            }
        });

        productTitle.setText(""+activeproduct.getName());

        String price = Utility.formatDecimal(Double.valueOf(activeproduct.getUnitPrice()));
        productPrice.setText(Application.AppCurrency+""+price);
        if(activeproduct.getDescription().isEmpty()){
            productSupportText.setText(getResources().getString(R.string.cehcek_out_instant_d));
        }else{
            productSupportText.setText(activeproduct.getDescription());
        }

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false); //new GridLayoutManager(getActivity(), 2);
        PDrecycleView.setLayoutManager(mLayoutManager);

        PDrecycleView.setAdapter(mAdapter);
        PDrecycleView.setNestedScrollingEnabled(false);



            Glide.with(getActivity())
                    .load(activeproduct.getImageUrl())
                    .into(coverImage);


            Log.d("ActiveProduct","activeP category ID " + activeproduct.getCategoryID());
            new getAllProductByCategoryAsync(activeproduct.getCategoryID()).execute();
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


        }
    }

}
