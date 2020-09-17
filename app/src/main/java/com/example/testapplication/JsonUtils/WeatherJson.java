package com.example.testapplication.JsonUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class WeatherJson {
    private String cod;
    private int message;
    private int cnt;
    @JsonProperty("list")
    private List<WList> List = new ArrayList<>();
    private City city;

    public String getCod(){
        return cod;
    }
    public void setCod(String input){
        this.cod = input;
    }
    public int getMessage(){
        return message;
    }
    public void setMessage(int input){
        this.message = input;
    }
    public int getCnt(){
        return cnt;
    }
    public void setCnt(int input){
        this.cnt = input;
    }
    public List<WList> getWList(){
        return List;
    }
    public void setWList(List<WList> input){
        this.List = input;
    }
    public City getCity(){
        return city;
    }
    public void setCity(City input){
        this.city = input;
    }
}
