package com.vikas.dev.searchplaces.model;

/**
 * Created by vikasmalhotra on 12/6/16.
 */

public class IPLocation {
    private String ip, country, name, abbr, area, lat, lng;

    public IPLocation() {
    }

    public IPLocation(String ip, String country, String name, String abbr, String area, String lat, String lng) {
        this.ip = ip;
        this.country = country;
        this.name = name;
        this.abbr = abbr;
        this.area = area;
        this.lat = lat;
        this.lng = lng;
    }

    public String getIp() {
        return ip;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public String getAbbr() {
        return abbr;
    }

    public String getArea() {
        return area;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }
}
