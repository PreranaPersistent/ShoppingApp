package com.shoppingapp.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoppingapp.com.shoppingapp.modes.utils.HandbagUtil;
import com.shoppingapp.models.HandBag;

import java.util.ArrayList;

import activities.shoppingapp.com.shoppingapp.HomeActivity;
import activities.shoppingapp.com.shoppingapp.R;

/**
 * Created by prerana_katyarmal on 2/16/2016.
 */
public class BasketRecyclerViewAdapter extends RecyclerView
        .Adapter<BasketRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private final Context mContext;
    private ArrayList<HandBag> mDataset;
    private static MyClickListener myClickListener;
    private boolean isAutosyncOn = false;


    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView txtLabel;
        ImageView imgItem;
        Button btnDeleteItem;

        public DataObjectHolder(View itemView) {
            super(itemView);
            txtLabel = (TextView) itemView.findViewById(R.id.item_textview);
            imgItem = (ImageView) itemView.findViewById(R.id.item_imageview);
            btnDeleteItem = (Button) itemView.findViewById(R.id.remove_item_button);
            btnDeleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem((int) btnDeleteItem.getTag());
                    if (isAutosyncOn) {
                        new deleteFromDb().execute((int) btnDeleteItem.getTag()+1);
                    }
                }
            });

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public BasketRecyclerViewAdapter(ArrayList<HandBag> myDataset, Context context, boolean isAutosyncOn) {
        mDataset = myDataset;
        mContext = context;
        this.isAutosyncOn = isAutosyncOn;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.basket_recyclerview_item, parent, false);


        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.txtLabel.setText(mDataset.get(position).getBagName());
        holder.imgItem.setImageResource(mDataset.get(position).getDrawableImage());
        holder.btnDeleteItem.setTag(position);

    }

    public void addItem(HandBag dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    private class deleteFromDb extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            HandbagUtil.deleteProductFromDatabase(params[0], mContext);
            return null;
        }
    }

}