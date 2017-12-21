package com.a123.adapter;



import com.a123.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;







public class DummyPropertyData {
    private static final String[] TotalViews = {"Views : 13","Views : 13","Views : 13","Views : 13","Views : 13"};
    private static final String[] TotalRequest = {"Requested Info : 4","Requested Info : 4","Requested Info : 4","Requested Info : 4","Requested Info : 4"};
    private static final String[] TotalAppointments = {"Requested Appointments : 4","Requested Appointments : 4","Requested Appointments : 4","Requested Appointments : 4","Requested Appointments : 4"};
    private static final String[] TotalVisitors = {"Visited : 4","Visited : 4","Visited : 4","Visited : 4","Visited : 4"};
    private static final String[] TotalOffers = {"Offers : 4","Offers : 4","Offers : 4","Offers : 4","Offers : 4"};
    private static final String[] DetailAddsress = {"Here Comes Location & address details","Here Comes Location & address details","Here Comes Location & address details",
            "Here Comes Location & address details","Here Comes Location & address details",};







    public static List<DummyPropertyItem> getListData() {
        List<DummyPropertyItem> data = new ArrayList<>();


            for (int x = 0; x < TotalViews.length; x++) {
                DummyPropertyItem item = new DummyPropertyItem();
                item.setTotalViews(TotalViews[x]);
                item.setTotalRequest(TotalRequest[x]);
                item.setTotalAppointments(TotalAppointments[x]);
                item.setTotalVisitors(TotalVisitors[x]);
                item.setTotalOffers(TotalOffers[x]);
                item.setDetailAddsress(DetailAddsress[x]);



                data.add(item);
            }

        return (data);
    }

    public static DummyPropertyItem getRandomListItem() {
        int rand = new Random().nextInt(5);

        DummyPropertyItem item = new DummyPropertyItem();

        item.setTotalViews(TotalViews[rand]);
        item.setTotalRequest(TotalRequest[rand]);
        item.setTotalAppointments(TotalAppointments[rand]);
        item.setTotalVisitors(TotalVisitors[rand]);
        item.setTotalOffers(TotalOffers[rand]);
        item.setDetailAddsress(DetailAddsress[rand]);


        return item;
    }
}
