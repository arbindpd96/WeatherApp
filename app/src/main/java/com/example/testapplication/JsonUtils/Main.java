package com.example.testapplication.JsonUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Main {
    private int temp;
    @JsonProperty("feels_like")
    private int feelsLike;
    @JsonProperty("temp_min")
    private int tempMin;
    @JsonProperty("temp_max")
    private int tempMax;
    private int pressure;
    @JsonProperty("sea_level")
    private int seaLevel;
    @JsonProperty("grnd_level")
    private int grndLevel;
    private int humidity;
    @JsonProperty("temp_kf")
    private int tempKf;

    @Override
    public String toString() {
        return "Main{" +
                "temp=" + temp +
                ", feelsLike=" + feelsLike +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                ", pressure=" + pressure +
                ", seaLevel=" + seaLevel +
                ", grndLevel=" + grndLevel +
                ", humidity=" + humidity +
                ", tempKf=" + tempKf +
                '}';
    }

    public int getTemp(){
        return temp;
    }
    public void setTemp(int input){
        this.temp = input;
    }
    public int getFeelsLike(){
        return feelsLike;
    }
    public void setFeelsLike(int input){
        this.feelsLike = input;
    }
    public int getTempMin(){
        return tempMin;
    }
    public void setTempMin(int input){
        this.tempMin = input;
    }
    public int getTempMax(){
        return tempMax;
    }
    public void setTempMax(int input){
        this.tempMax = input;
    }
    public int getPressure(){
        return pressure;
    }
    public void setPressure(int input){
        this.pressure = input;
    }
    public int getSeaLevel(){
        return seaLevel;
    }
    public void setSeaLevel(int input){
        this.seaLevel = input;
    }
    public int getGrndLevel(){
        return grndLevel;
    }
    public void setGrndLevel(int input){
        this.grndLevel = input;
    }
    public int getHumidity(){
        return humidity;
    }
    public void setHumidity(int input){
        this.humidity = input;
    }
    public int getTempKf(){
        return tempKf;
    }
    public void setTempKf(int input){
        this.tempKf = input;
    }
}
