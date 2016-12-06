package com.vikas.dev.searchplaces.model;

/**
 * Created by vikasmalhotra on 12/6/16.
 */

public class State {
    private String country, name, abbr, area, largest_city, capital;

    public State() {
    }

    public State(String country, String name, String abbr, String area, String largest_city, String capital) {
        this.country = country;
        this.name = name;
        this.abbr = abbr;
        this.area = area;
        this.largest_city = largest_city;
        this.capital = capital;
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

    public String getLargestCity() {
        return largest_city;
    }

    public String getCapital() {
        return capital;
    }
}
