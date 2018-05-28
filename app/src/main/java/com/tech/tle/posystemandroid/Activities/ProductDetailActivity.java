package com.tech.tle.posystemandroid.Activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.tech.tle.posystemandroid.Fragments.ProductDetailFragment;
import com.tech.tle.posystemandroid.Helper.ViewUtils;
import com.tech.tle.posystemandroid.R;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        ViewUtils.loadFragment(ProductDetailActivity.this,productDetailFragment);

    }


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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
