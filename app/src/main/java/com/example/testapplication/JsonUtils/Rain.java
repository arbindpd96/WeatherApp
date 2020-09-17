package com.example.testapplication.JsonUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rain {

    @JsonProperty("3h")
    private int h3;

    public int getH3() {
        return h3;
    }

    public void setH3(int h3) {
        this.h3 = h3;
    }

}
