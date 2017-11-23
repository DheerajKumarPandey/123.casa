package com.a123.adapter;



import com.a123.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Abhishek on 29-03-2017.
 */

public class DummyHotelData {
    private static final String[] SrNo = {"12345 MXN", "12345 MXN" , "12345 MXN" , "12345 MXN" , "12345 MXN" };
    private static final Integer[] HotelImage = {R.drawable.hotel, R.drawable.hotel,R.drawable.hotel , R.drawable.hotel , R.drawable.hotel};
    private static final String[] Date = {"Posted On : 12 Oct 2017" , "Posted On : 12 Oct 2017", "Posted On : 12 Oct 2017", "Posted On : 12 Oct 2017" , "Posted On : 12 Oct 2017" };
    private static final String[] Description = {"2 BHK Flat 123 Towers", "2 BHK Flat 123 Towers" , "2 BHK Flat 123 Towers" , "2 BHK Flat 123 Towers" , "2 BHK Flat 123 Towers" };
    private static final String[] Address = {"12345 MXN", "12345 MXN" , "12345 MXN" , "12345 MXN" , "12345 MXN" };

    public static List<DummyHotelItem> getListData() {
        List<DummyHotelItem> data = new ArrayList<>();


            for (int x = 0; x < SrNo.length; x++) {
                DummyHotelItem item = new DummyHotelItem();
                item.setSrNo(SrNo[x]);
                item.setHotelImage(HotelImage[x]);
                item.setDate(Date[x]);
                item.setDescription(Description[x]);
                item.setAddress(Address[x]);

                data.add(item);
            }

        return (data);
    }

    public static DummyHotelItem getRandomListItem() {
        int rand = new Random().nextInt(5);

        DummyHotelItem item = new DummyHotelItem();
        item.setSrNo(SrNo[rand]);
        item.setHotelImage(HotelImage[rand]);
        item.setDate(Date[rand]);
        item.setDescription(Description[rand]);
        item.setAddress(Address[rand]);

        return item;
    }
}
