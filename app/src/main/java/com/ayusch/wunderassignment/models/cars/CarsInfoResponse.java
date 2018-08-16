package com.ayusch.wunderassignment.models.cars;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class CarsInfoResponse {

    @SerializedName("placemarks")
    private List<PlaceMarks> placemarks;

    public void setPlacemarks(List<PlaceMarks> placemarks) {
        this.placemarks = placemarks;
    }

    public List<PlaceMarks> getPlacemarks() {
        return placemarks;
    }

    @Override
    public String toString() {
        return
                "CarsInfoResponse{" +
                        "placemarks = '" + placemarks + '\'' +
                        "}";
    }

    public static class PlaceMarks {

        @SerializedName("address")
        private String address;

        @SerializedName("fuel")
        private int fuel;

        @SerializedName("exterior")
        private String exterior;

        @SerializedName("coordinates")
        private List<Double> coordinates;

        @SerializedName("name")
        private String name;

        @SerializedName("engineType")
        private String engineType;

        @SerializedName("vin")
        private String vin;

        @SerializedName("interior")
        private String interior;

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress() {
            return address;
        }

        public void setFuel(int fuel) {
            this.fuel = fuel;
        }

        public int getFuel() {
            return fuel;
        }

        public void setExterior(String exterior) {
            this.exterior = exterior;
        }

        public String getExterior() {
            return exterior;
        }

        public void setCoordinates(List<Double> coordinates) {
            this.coordinates = coordinates;
        }

        public List<Double> getCoordinates() {
            return coordinates;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setEngineType(String engineType) {
            this.engineType = engineType;
        }

        public String getEngineType() {
            return engineType;
        }

        public void setVin(String vin) {
            this.vin = vin;
        }

        public String getVin() {
            return vin;
        }

        public void setInterior(String interior) {
            this.interior = interior;
        }

        public String getInterior() {
            return interior;
        }

        @Override
        public String toString() {
            return
                    "PlaceMarks{" +
                            "address = '" + address + '\'' +
                            ",fuel = '" + fuel + '\'' +
                            ",exterior = '" + exterior + '\'' +
                            ",coordinates = '" + coordinates + '\'' +
                            ",name = '" + name + '\'' +
                            ",engineType = '" + engineType + '\'' +
                            ",vin = '" + vin + '\'' +
                            ",interior = '" + interior + '\'' +
                            "}";
        }
    }
}