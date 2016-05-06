package com.shoppingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoppingapp.models.HandBag;

import java.util.ArrayList;

import activities.shoppingapp.com.shoppingapp.HomeActivity;
import activities.shoppingapp.com.shoppingapp.R;

/**
 * Created by prerana_katyarmal on 2/16/2016.
 */
public class ItemsRecyclerViewAdapter extends RecyclerView
        .Adapter<ItemsRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private final Context mContext;
    private ArrayList<HandBag> mDataset;
    private static MyClickListener
            myClickListener;


    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView txtLabel;
        ImageView imgItem;
        CheckBox checkBox;

        public DataObjectHolder(View itemView) {
            super(itemView);
            txtLabel = (TextView) itemView.findViewById(R.id.item_textview);
            imgItem = (ImageView) itemView.findViewById(R.id.item_imageview);
            checkBox= (CheckBox) itemView.findViewById(R.id.item_checkbox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.v("TAG",String.valueOf(isChecked)+checkBox.getTag());
                    if (isChecked) {
                        ((HomeActivity)mContext).getHashMapOfSelectedItems().put((int) checkBox.getTag()+1, mDataset.get((int) checkBox.getTag()));
                    } else {
                        ((HomeActivity)mContext).getHashMapOfSelectedItems().remove((int) checkBox.getTag()+1);
                    }
                    Log.v("TAG","hhashMap"+((HomeActivity)mContext).getHashMapOfSelectedItems().toString());
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

    public ItemsRecyclerViewAdapter(ArrayList<HandBag> myDataset,Context context) {
        mDataset = myDataset;
        mContext=context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);



        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.txtLabel.setText(mDataset.get(position).getBagName());
        holder.imgItem.setImageResource(mDataset.get(position).getDrawableImage());
        holder.checkBox.setTag((int)position);

    }

    public void addItem(HandBag dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}