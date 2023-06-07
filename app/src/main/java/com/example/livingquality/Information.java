package com.example.livingquality;

public class Information {
    private int humidity;
    private int light;
    private int air;
    private int sound;

    public Information(int humidity, int light, int air, int sound){
        this.humidity = humidity;
        this.light = light;
        this.air = air;
        this.sound = sound;
    }

    public Information(){}

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getAir() {
        return air;
    }

    public void setAir(int air) {
        this.air = air;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }
}
