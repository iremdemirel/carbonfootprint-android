package com.example.bil496.model;

public class CarJourney {
    private Route route;
    //private Car car;
    private Double totalEmission;
    private String journeyDate;
    private String transMode;
    private long journeyID;
    private int route_save;//0 means unsaved 1 means saved route


    public CarJourney(String mode,/* Car car, Route route,*/ String date) {
        this.route = route;
        //this.car = car;
        transMode = mode;
        if (mode.equals("car")){
            //Double totalFuelUsage = route.getCityDistance()/car.getCityKmPerGallon()+route.getHighwayDistance()/car.getHighwayKmPerGallon();
            totalEmission = 5.0;// totalFuelUsage*car.getKgPerGallon();   //emission for car
        }
        else if (mode.equals("bus")){
            totalEmission = 6.0;//route.getTotalDistance()*0.086;  //emission for bus
        }
        else if (mode.equals("walk/bike")){
            totalEmission = 0.0;      //emission for walk/bike
        }
        else if (mode.equals("skytrain")){
            totalEmission = 7.0;//route.getTotalDistance()*0.003; //emission for skytrain
        }
        journeyDate = date;
    }

    //journey
    public CarJourney(long journeyId, String mode/*, Car car, Route route*/, String date, int route_save) {
        this.route = route;
        //this.car = car;
        transMode = mode;
        if (mode.equals("car")){
            //Double totalFuelUsage = route.getCityDistance()/car.getCityKmPerGallon()+route.getHighwayDistance()/car.getHighwayKmPerGallon();
            totalEmission = 8.0;//totalFuelUsage*car.getKgPerGallon();
        }
        else if (mode.equals("bus")){
            totalEmission = 9.0;//route.getTotalDistance()*0.086;
        }
        else if (mode.equals("walk/bike")){
            totalEmission = 0.0;
        }
        else if (mode.equals("skytrain")){
            totalEmission = 10.0;//route.getTotalDistance()*0.003;
        }
        journeyDate = date;
        journeyID = journeyId;
        this.route_save = route_save;
    }


    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }


    public Double getTotalEmission(String unit) {
        if (unit.equals("Kg")) {
            return totalEmission;
        }
        else if (unit.equals("Tree day")) {//returns carbon emission in tree hours
            return totalEmission/0.05965;//a tree absorbs as much as 48 pounds/21.7724 kg of CO2 a year, 21.7724/365
        }
        return totalEmission;
    }


    public String getJourneyDate() {
        return journeyDate;
    }

    public String getTransMode() { return transMode; }

    public void setTransMode(String mode) {this.transMode = transMode; }

    public long getJourneyID() {
        return journeyID;
    }

    public int getRoute_save() {
        return route_save;
    }

    public void setRoute_save(int route_save) {
        this.route_save = route_save;
    }

}
