package com.a123.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a123.MyAppointmentActivity;
import com.a123.R;
import com.a123.model.Agent;
import com.a123.model.AppointmentData;
import com.a123.model.Appoitment;
import com.a123.model.Client;
import com.a123.model.Property;
import com.a123.model.UserAppointmentData;
import com.a123.model.UserAppoitment;
import com.a123.model.UserProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DJ-PC on 7/6/2017.
 */

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.DataHolder> {

    private List<UserAppointmentData.Info> listData;

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


    public AppointmentAdapter(List<UserAppointmentData.Info> listData, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listData = listData;
        this.context = c;
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.user_appointment_item, parent, false);
        return new DataHolder(view);

    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        UserAppoitment item = listData.get(position).getAppoitment();
        Agent client_item = listData.get(position).getAgent();
        UserProperty property_item = listData.get(position).getProperty();

        holder.tv_property_type.setText("Property Type : "+property_item.getType_of_property());
        holder.tv_for.setText("For : "+property_item.getType_of_operation());
        holder.tv_time_date.setText(item.getAppointment_time());
        holder.tv_name.setText("Agent Name: "+client_item.getName());
        holder.tv_address.setText("Agent Mb No: "+client_item.getPhoneno());
        holder.tv_mb_no.setText("Mobile No: "+item.getPhone_no());
        holder.tv_description.setText(item.getAppointment_description());

    }

    @Override
    public int getItemCount() {
        return listData.size();


    }


    class DataHolder extends RecyclerView.ViewHolder {
        TextView tv_property_type, tv_for, tv_time_date, tv_name, tv_address, tv_mb_no, tv_description;
        TextView tv_btn_accept;


        public DataHolder(final View itemView) {
            super(itemView);


            tv_property_type = (TextView) itemView.findViewById(R.id.tv_property_type);
            tv_for = (TextView) itemView.findViewById(R.id.tv_for);
            tv_time_date = (TextView) itemView.findViewById(R.id.tv_time_date);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            tv_mb_no = (TextView) itemView.findViewById(R.id.tv_mb_no);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);

            tv_btn_accept = (TextView) itemView.findViewById(R.id.tv_btn_accept);

            tv_btn_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MyAppointmentActivity)context).knowMyPosition( getLayoutPosition());
                }
            });
        }


    }

    public void setListData(ArrayList<UserAppointmentData.Info> exerciseList) {
        this.listData.clear();
        this.listData.addAll(exerciseList);

    }
}
