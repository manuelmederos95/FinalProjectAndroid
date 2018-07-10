package com.analytics.model;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;


public class StatsList {


    private HashMap<BigInteger, List<Stats>> stats;

    public StatsList(){
        stats = new HashMap<BigInteger,List<Stats>>();
    }

    public HashMap<BigInteger, List<Stats>> getStats() {
        return stats;
    }

    public void setStats(HashMap<BigInteger, List<Stats>> stats) {
        this.stats = stats;
    }









}
