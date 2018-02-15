package com.a123.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a123.MyAppointmentActivity;
import com.a123.MyAppointmentUserActivity;
import com.a123.R;
import com.a123.model.AppointmentData;
import com.a123.model.Appoitment;
import com.a123.model.Client;
import com.a123.model.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DJ-PC on 7/6/2017.
 */

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.DataHolder> {

    private List<AppointmentData.Info> listData;

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


    public AppointmentListAdapter(List<AppointmentData.Info> listData, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listData = listData;
        this.context = c;
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.appointment_item, parent, false);
        return new DataHolder(view);

    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        Appoitment item = listData.get(position).getAppoitment();
        Client client_item = listData.get(position).getClient();
        Property property_item = listData.get(position).getProperty();

        holder.tv_property_type.setText("Property Type : "+property_item.getType_of_property());
        holder.tv_for.setText("For : "+property_item.getType_of_operation());
        holder.tv_time_date.setText(item.getAppointment_time());
        holder.tv_name.setText(client_item.getName());
        holder.tv_address.setText(client_item.getAddress());
        holder.tv_mb_no.setText(item.getPhone_no());
        holder.tv_description.setText(item.getAppointment_description());

    }

    @Override
    public int getItemCount() {
        return listData.size();
        //yaha pe kiska size return karenge

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
                    ((MyAppointmentUserActivity)context).appointmentAccept( getLayoutPosition());
                }
            });


        }


    }

    public void setListData(ArrayList<AppointmentData.Info> exerciseList) {
        this.listData.clear();
        this.listData.addAll(exerciseList);

    }
}
