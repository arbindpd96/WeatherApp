package com.example.testapplication.JsonUtils;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class WList {
    @JsonProperty("dt")
    private int dt;
    @JsonProperty("main")
    private Main main;
    @JsonProperty("weather")
    private List<Weather> weather;
    @JsonProperty("clouds")
    private Clouds clouds;
    @JsonProperty("wind")
    private Wind wind;
    @JsonProperty("visibility")
    private int visibility;
    @JsonProperty("pop")
    private int pop;
    @JsonProperty("rain")
    private Rain rain;
    @JsonProperty("sys")
    private Sys sys;
    @JsonProperty("dt_txt")
    private String dtTxt;
    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public int getDt(){
        return dt;
    }
    public void setDt(int input){
        this.dt = input;
    }
    public Main getMain(){
        return main;
    }
    public void setMain(Main input){
        this.main = input;
    }
    public List<Weather> getWeather(){
        return weather;
    }
    public void setWeather(List<Weather> input){
        this.weather = input;
    }
    public Clouds getClouds(){
        return clouds;
    }
    public void setClouds(Clouds input){
        this.clouds = input;
    }
    public Wind getWind(){
        return wind;
    }
    public void setWind(Wind input){
        this.wind = input;
    }
    public int getVisibility(){
        return visibility;
    }
    public void setVisibility(int input){
        this.visibility = input;
    }
    public int getPop(){
        return pop;
    }
    public void setPop(int input){
        this.pop = input;
    }
    public Sys getSys(){
        return sys;
    }
    public void setSys(Sys input){
        this.sys = input;
    }
    public String getDtTxt(){
        return dtTxt;
    }
    public void setDtTxt(String input){
        this.dtTxt = input;
    }

    @Override
    public String toString() {
        return "WList{" +
                "dt=" + dt +
                ", main=" + main +
                ", weather=" + weather +
                ", clouds=" + clouds +
                ", wind=" + wind +
                ", visibility=" + visibility +
                ", pop=" + pop +
                ", rain=" + rain +
                ", sys=" + sys +
                ", dtTxt='" + dtTxt + '\'' +
                '}';
    }
}
