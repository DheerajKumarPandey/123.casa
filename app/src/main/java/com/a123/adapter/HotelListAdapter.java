package com.a123.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.a123.ImmediateAppointmentActivity;
import com.a123.MainActivity;
import com.a123.PropertyDetailActivity;
import com.a123.R;
import com.a123.model.UserList;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DJ-PC on 7/6/2017.
 */

public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.DataHolder> {

    private List<UserList.Info> listdata;
    private LayoutInflater inflater;
    private ItemClickCallback itemclickcallback;
    private int count = 0;
    private Integer Position;
    private boolean fav=false;
    private Context context;
    public interface ItemClickCallback {
        void onItemClick(int p);

        void onSecondaryIconClick(int p);

    }







    public void SetItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemclickcallback = itemClickCallback;
    }


    public HotelListAdapter(List<UserList.Info> listdata, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listdata = listdata;
        this.context = c;
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_view_item, parent, false);
        return new DataHolder(view);

    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        UserList.Info item = listdata.get(position);
        holder.tv_sr_no.setText(item.getMax_price());
        Glide.with(context)
                .load(item.getThumbnailImage())
                .into(holder.img_hotel);
        holder.tv_date.setText(item.getCreated_at());
      //  holder.tv_description.setText(item.getDescription());
        holder.tv_address.setText("Location :"+item.getAddress());
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class DataHolder extends RecyclerView.ViewHolder {
        TextView tv_sr_no, tv_rent,tv_date, tv_description, tv_address,tv_appointment ;
        ImageButton img_btn_fav;
        ImageView img_hotel;
        RelativeLayout rel_list_item;
        
        public DataHolder(final View itemView) {
            super(itemView);
            img_hotel=(ImageView)itemView.findViewById(R.id.img_hotel);
            img_btn_fav=(ImageButton)itemView.findViewById(R.id.img_btn_fav);
            rel_list_item=(RelativeLayout)itemView.findViewById(R.id.rel_list_item);
            tv_sr_no=(TextView)itemView.findViewById(R.id.tv_sr_no);
            tv_rent=(TextView)itemView.findViewById(R.id.tv_rent);
            tv_date=(TextView)itemView.findViewById(R.id.tv_date);
            tv_description=(TextView)itemView.findViewById(R.id.tv_description);
            tv_address=(TextView)itemView.findViewById(R.id.tv_address);
            tv_appointment=(TextView)itemView.findViewById(R.id.tv_appointment);

            rel_list_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(context,PropertyDetailActivity.class);
                    intent.putExtra("position",String.valueOf(getLayoutPosition()));
                    // String position=pagetLayoutPosition();
                    context.startActivity(intent);

                   // context.startActivity(new Intent(context, PropertyDetailActivity.class));
                }
            });
            img_btn_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(fav==true){
                        img_btn_fav.setImageResource(R.drawable.ic_favorite_border);
                        fav= false;
                    }
                    else {
                        img_btn_fav.setImageResource(R.drawable.ic_favorite);
                        fav=true;
                    }

                }
            });
            tv_appointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //((MainActivity)context).immediateAppointment( getLayoutPosition());
                    Position = getLayoutPosition();
                    Intent intent= new Intent(context, ImmediateAppointmentActivity.class);
                    intent.putExtra("position", Position);
                   //intent.putExtra("category","Current Supply Bills");
                    context.startActivity(intent);


                    //context.startActivity(new Intent(context, ImmediateAppointmentActivity.class));
                }
            });

        }


    }

 /*   public void setListData(ArrayList<DummyHotelItem> exerciseList) {
        this.listdata.clear();
        this.listdata.addAll(exerciseList);

    }*/



}
