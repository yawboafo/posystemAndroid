package com.tech.tle.posystemandroid.Activities;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.SubtitleCollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tech.tle.posystemandroid.Adapters.StoreAdapter;
import com.tech.tle.posystemandroid.AppDatabase;
import com.tech.tle.posystemandroid.Fragments.ProductDetailFragment;
import com.tech.tle.posystemandroid.Helper.ViewUtils;
import com.tech.tle.posystemandroid.Models.MemoryData;
import com.tech.tle.posystemandroid.Models.Product;
import com.tech.tle.posystemandroid.R;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailFragment.OnFragmentInteractionListener {
    private SubtitleCollapsingToolbarLayout subtitleCollapsingToolbarLayout;
    private ImageView coverImage;
    private RecyclerView PDrecycleView;
    private StoreAdapter mAdapter;
    private List<Product> itemsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        ProductDetailFragment productDetailFragment = new ProductDetailFragment().newInstance(MemoryData.getActiveProduct());
        ViewUtils.loadFragment(ProductDetailActivity.this,productDetailFragment);



        //intiView();
    }




  /**  void intiView(){

        // subtitleCollapsingToolbarLayout = view.findViewById(R.id.toolbarLayout);
        coverImage = findViewById(R.id.coverImage);
        PDrecycleView = findViewById(R.id.PDrecycleView);


        itemsList = new ArrayList<>();
        mAdapter = new StoreAdapter(ProductDetailActivity.this, itemsList,1);

        //GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), ROWSCOUNT, GridLayoutManager.HORIZONTAL, false);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ProductDetailActivity.this, 1, GridLayoutManager.HORIZONTAL, false); //new GridLayoutManager(getActivity(), 2);
        PDrecycleView.setLayoutManager(mLayoutManager);
        //PDrecycleView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(2), true));
        //PDrecycleView.setItemAnimator(new DefaultItemAnimator());
        PDrecycleView.setAdapter(mAdapter);
        PDrecycleView.setNestedScrollingEnabled(false);

        //  if(MemoryData.activeProduct!=null) {
        // subtitleCollapsingToolbarLayout.setTitle(MemoryData.activeProduct.getName());
        // subtitleCollapsingToolbarLayout.setSubtitle("GHS " + MemoryData.activeProduct.getUnitPrice());
        // subtitleCollapsingToolbarLayout.setTitleEnabled(false);
        // subtitleCollapsingToolbarLayout.setc
        // subtitleCollapsingToolbarLayout.setcollapseTitle

        Glide.with(ProductDetailActivity.this)
                .load(MemoryData.activeProduct.getImageUrl())
                .into(coverImage);


        Log.d("ActiveProduct","activeP category ID " + MemoryData.activeProduct.getCategoryID());
        new getAllProductByCategoryAsync(MemoryData.activeProduct.getCategoryID()).execute();
    }
**/
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStackImmediate();
        else
            this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();
                //Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


   /** @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.product_detail_menu, menu);

        return true;

    }**/
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class getAllProductByCategoryAsync extends AsyncTask<Void, Void, Void>
    {

        private int categoryID;
        getAllProductByCategoryAsync(int categoryID){

            Log.d(getClass().getSimpleName(),"Constructor was called ");
            this.categoryID = categoryID;
        }

        List<Product> products;

        AppDatabase db = AppDatabase.getAppDatabase(ProductDetailActivity.this);

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
