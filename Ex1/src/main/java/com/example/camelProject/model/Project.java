package com.example.camelProject.model;


public class Project {

    private int projectid;

    private String projectname;

    private String projectcode;

    public Project(int projectid, String projectname, String projectcode) {
        this.projectid = projectid;
        this.projectname = projectname;
        this.projectcode = projectcode;
    }

    public Project() {
    }

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getProjectcode() {
        return projectcode;
    }

    public void setProjectcode(String projectcode) {
        this.projectcode = projectcode;
    }
}
