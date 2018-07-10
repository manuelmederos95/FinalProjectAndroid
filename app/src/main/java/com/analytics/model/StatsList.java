package com.analytics.model;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class StatsList {
    private Project project;
    private Map<BigInteger,Stats> stats;


    public Map<BigInteger, Stats> getStats() {
        return stats;
    }

    public void setStats(Map<BigInteger, Stats> stats) {
        this.stats = stats;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }




}
