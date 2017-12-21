package com.a123.adapter;



import com.a123.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DummySliderData {
    private static final Integer[] PropertyImage = {R.drawable.hotel,R.drawable.hotel,R.drawable.hotel,R.drawable.hotel,R.drawable.hotel};
    private static final String[] PropertyInfo = {"Property, in the abstract, is what belongs to or with something, whether as an attribute or as a component of said thing.",
            "Property, in the abstract, is what belongs to or with something, whether as an attribute or as a component of said thing.",
            "Property, in the abstract, is what belongs to or with something, whether as an attribute or as a component of said thing.",
            "Property, in the abstract, is what belongs to or with something, whether as an attribute or as a component of said thing.",
            "Property, in the abstract, is what belongs to or with something, whether as an attribute or as a component of said thing."};








    public static List<DummySliderItem> getListData() {
        List<DummySliderItem> data = new ArrayList<>();


            for (int x = 0; x < PropertyImage.length; x++) {
                DummySliderItem item = new DummySliderItem();
                item.setPropertyImage(PropertyImage[x]);
                item.setPropertyInfo(PropertyInfo[x]);



                data.add(item);
            }

        return (data);
    }

    public static DummySliderItem getRandomListItem() {
        int rand = new Random().nextInt(5);

        DummySliderItem item = new DummySliderItem();

        item.setPropertyImage(PropertyImage[rand]);
        item.setPropertyInfo(PropertyInfo[rand]);



        return item;
    }
}
