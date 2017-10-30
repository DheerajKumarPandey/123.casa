package com.a123.application;


import com.google.android.gms.location.places.Place;

/**
 * Created by Aquad on 21-07-2017.
 */

public class SingleInstance {
    private static final SingleInstance ourInstance = new SingleInstance();
    private String servicesId = "";

    public static SingleInstance getInstance() {
        return ourInstance;
    }

    private SingleInstance() {
    }

    private Place selectedPlace;
    private int domesticCount = 0;
    private int myCareCount = 0;
    private int eventsCount = 0;

    public int getDomesticCount() {
        return domesticCount;
    }

    public void setDomesticCount(int domesticCount) {
        this.domesticCount = domesticCount;
    }

    public int getMyCareCount() {
        return myCareCount;
    }

    public void setMyCareCount(int myCareCount) {
        this.myCareCount = myCareCount;
    }

    public int getEventsCount() {
        return eventsCount;
    }

    public void setEventsCount(int eventsCount) {
        this.eventsCount = eventsCount;
    }

    public Place getSelectedPlace() {
        return selectedPlace;
    }

    public void setSelectedPlace(Place selectedPlace) {
        this.selectedPlace = selectedPlace;
    }

    //private Services selectedServiceCategory;

   /* public Services getSelectedServiceCategory() {
        return selectedServiceCategory;
    }

    public void setSelectedServiceCategory(Services selectedServiceCategory) {
        this.selectedServiceCategory = selectedServiceCategory;
    }*/

    public String getServicesId() {
        return servicesId;
    }

    public void setServicesId(String servicesId) {
        this.servicesId = servicesId;
    }
}
