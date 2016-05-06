package activities.shoppingapp.com.shoppingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shoppingapp.adapters.BasketRecyclerViewAdapter;
import com.shoppingapp.adapters.ItemsRecyclerViewAdapter;
import com.shoppingapp.com.shoppingapp.modes.utils.HandbagUtil;
import com.shoppingapp.common.Constant;
import com.shoppingapp.models.HandBag;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingBasketActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private BasketRecyclerViewAdapter mAdapter;
    private ArrayList<HandBag> bagArrayList;
    private boolean isAutosyncOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_shopping_basket);
        inflateXml();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        MenuItem item = menu.findItem(R.id.syncSwitch);
        item.setActionView(R.layout.on_off_switch);
        return true;
    }

    private void inflateXml() {
        mRecyclerView = (RecyclerView) findViewById(R.id.items_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        getdataFromIntent();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);



    }

    private void getdataFromIntent() {
        isAutosyncOn = getSharedPreferences(Constant.TAG,MODE_PRIVATE).getBoolean(Constant.AUTO_SYNC_STATUS,false);
        if (isAutosyncOn) {
            new getHandBagsFromDb().execute();
        } else {
            bagArrayList = (ArrayList<HandBag>) getIntent().
                    getSerializableExtra(Constant.SELECTED_ITEMS);
if(bagArrayList!=null) {
    mAdapter = new BasketRecyclerViewAdapter(bagArrayList, ShoppingBasketActivity.this, isAutosyncOn);
    mRecyclerView.setAdapter(mAdapter);
}
        }
    }

    private class getHandBagsFromDb extends AsyncTask<Void, Void, ArrayList<HandBag>> {

        @Override
        protected ArrayList<HandBag> doInBackground(Void... params) {
            bagArrayList = HandbagUtil.getProducts(ShoppingBasketActivity.this);

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<HandBag> aVoid) {
            super.onPostExecute(aVoid);
            mAdapter = new BasketRecyclerViewAdapter(bagArrayList, ShoppingBasketActivity.this, isAutosyncOn);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
