package com.example.testapplication.JsonUtils;

public class Weather {
    private int id;
    private String main;
    private String description;
    private String icon;

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", main='" + main + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }

    public int getId(){
        return id;
    }
    public void setId(int input){
        this.id = input;
    }
    public String getMain(){
        return main;
    }
    public void setMain(String input){
        this.main = input;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String input){
        this.description = input;
    }
    public String getIcon(){
        return icon;
    }
    public void setIcon(String input){
        this.icon = input;
    }
}
