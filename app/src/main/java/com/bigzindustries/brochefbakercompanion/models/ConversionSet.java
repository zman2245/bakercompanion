package com.bigzindustries.brochefbakercompanion.models;

import java.util.List;

public class ConversionSet {
    private String name;
    private int priority;
    private List<Conversion> conversions;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<Conversion> getConversions() {
        return conversions;
    }

    public void setConversions(List<Conversion> conversions) {
        this.conversions = conversions;
    }
}
