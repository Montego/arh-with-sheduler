package com.arhivator.arhwithsheduler.dtos;

public class SeparatedName {
    private String firstPart;
    private String secondPart;
    private String thirdPart;

    public SeparatedName() {
    }

    public SeparatedName(String firstPart, String secondPart, String thirdPart) {
        this.firstPart = firstPart;
        this.secondPart = secondPart;
        this.thirdPart = thirdPart;
    }

    public String getFirstPart() {
        return firstPart;
    }

    public void setFirstPart(String firstPart) {
        this.firstPart = firstPart;
    }

    public String getSecondPart() {
        return secondPart;
    }

    public void setSecondPart(String secondPart) {
        this.secondPart = secondPart;
    }

    public String getThirdPart() {
        return thirdPart;
    }

    public void setThirdPart(String thirdPart) {
        this.thirdPart = thirdPart;
    }

    @Override
    public String toString() {
        return "SeparatedName{" +
                "firstPart='" + firstPart + '\'' +
                ", secondPart='" + secondPart + '\'' +
                ", thirdPart='" + thirdPart + '\'' +
                '}';
    }
}
