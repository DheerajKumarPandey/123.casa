package com.a123.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.a123.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DJ-PC on 7/6/2017.
 */

public class PropertyListAdapter extends RecyclerView.Adapter<PropertyListAdapter.DataHolder> {

    private List<DummyPropertyItem> listdata;
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


    public PropertyListAdapter(List<DummyPropertyItem> listdata, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listdata = listdata;
        this.context = c;
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.property_list_item, parent, false);
        return new DataHolder(view);

    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        DummyPropertyItem item = listdata.get(position);



        holder.tv_views.setText(item.getTotalViews());
        holder.tv_requested_info.setText(item.getTotalRequest());
        holder.tv_req_appointment.setText(item.getTotalAppointments());
        holder.tv_visited.setText(item.getTotalVisitors());
        holder.tv_offers.setText(item.getTotalOffers());
        holder.tv_address_detail.setText(item.getDetailAddsress());
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class DataHolder extends RecyclerView.ViewHolder {
        TextView tv_views, tv_certified, tv_requested_info, tv_badge;
        ImageView img_certified, img_badge;
        TextView tv_req_appointment, tv_visited, tv_offers, tv_address_detail;

        public DataHolder(final View itemView) {
            super(itemView);

            img_certified=(ImageView)itemView.findViewById(R.id.img_certified);
            img_badge=(ImageView)itemView.findViewById(R.id.img_badge);

            tv_views=(TextView)itemView.findViewById(R.id.tv_views);
            tv_certified=(TextView)itemView.findViewById(R.id.tv_certified);
            tv_requested_info=(TextView)itemView.findViewById(R.id.tv_requested_info);
            tv_badge=(TextView)itemView.findViewById(R.id.tv_badge);


            tv_req_appointment=(TextView)itemView.findViewById(R.id.tv_req_appointment);
            tv_visited=(TextView)itemView.findViewById(R.id.tv_visited);
            tv_offers=(TextView)itemView.findViewById(R.id.tv_offers);
            tv_address_detail=(TextView)itemView.findViewById(R.id.tv_address_detail);



        }


    }

    public void setListData(ArrayList<DummyPropertyItem> exerciseList) {
        this.listdata.clear();
        this.listdata.addAll(exerciseList);

    }
}
