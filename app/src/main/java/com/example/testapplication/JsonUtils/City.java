package com.example.testapplication.JsonUtils;

public class City {
        private int id;
        private String name;
        private Cord coord;
        private String country;
        private int population;
        private int timezone;
        private int sunrise;
        private int sunset;

        public int getId(){
            return id;
        }
        public void setId(int input){
            this.id = input;
        }
        public String getName(){
            return name;
        }
        public void setName(String input){
            this.name = input;
        }
        public Cord getCoord(){
            return coord;
        }
        public void setCoord(Cord input){
            this.coord = input;
        }
        public String getCountry(){
            return country;
        }
        public void setCountry(String input){
            this.country = input;
        }
        public int getPopulation(){
            return population;
        }
        public void setPopulation(int input){
            this.population = input;
        }
        public int getTimezone(){
            return timezone;
        }
        public void setTimezone(int input){
            this.timezone = input;
        }
        public int getSunrise(){
            return sunrise;
        }
        public void setSunrise(int input){
            this.sunrise = input;
        }
        public int getSunset(){
            return sunset;
        }
        public void setSunset(int input){
            this.sunset = input;
        }
    }

