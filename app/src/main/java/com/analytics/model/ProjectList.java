package com.analytics.model;

import java.util.List;

public class ProjectList {

    public List<Project> getProjectOwn() {
        return projectOwn;
    }

    public void setProjectOwn(List<Project> projectOwn) {
        this.projectOwn = projectOwn;
    }

    public List<Project> getCollaborations() {
        return collaborations;
    }

    public void setCollaborations(List<Project> collaborations) {
        this.collaborations = collaborations;
    }

    private List<Project> projectOwn;
    private List<Project> collaborations;

}
