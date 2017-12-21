package com.a123.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a123.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DJ-PC on 7/6/2017.
 */

public class SliderListAdapter extends RecyclerView.Adapter<SliderListAdapter.DataHolder> {

    private List<DummySliderItem> listdata;
    private LayoutInflater inflater;
    private ItemClickCallback itemclickcallback;
    private int count = 0;
    private Context context;
    public interface ItemClickCallback {
        void onItemClick(int p);

        void onSecondaryIconClick(int p);

    }

    public void SetItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemclickcallback = itemClickCallback;
    }


    public SliderListAdapter(List<DummySliderItem> listdata, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listdata = listdata;
        this.context = c;
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.slider_item, parent, false);
        return new DataHolder(view);

    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        DummySliderItem item = listdata.get(position);



        holder.img_property_pic.setImageResource(item.getPropertyImage());
        holder.tv_property_info.setText(item.getPropertyInfo());

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class DataHolder extends RecyclerView.ViewHolder {
        TextView tv_property_info ;
        ImageView img_property_pic ;


        public DataHolder(final View itemView) {
            super(itemView);

            img_property_pic=(ImageView)itemView.findViewById(R.id.img_property_pic);


            tv_property_info=(TextView)itemView.findViewById(R.id.tv_property_info);




        }


    }

    public void setListData(ArrayList<DummySliderItem> exerciseList) {
        this.listdata.clear();
        this.listdata.addAll(exerciseList);

    }
}
