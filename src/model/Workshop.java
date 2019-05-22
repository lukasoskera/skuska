package model;

import uicontrollers.WorkshopCreateController;

public class Workshop {

    private String name;
    private Integer ID;
    private String ambass;
    private String school;
    private Integer studentCount;
    private Integer duration;
    private Integer rating;
    private String report;
    private String date;


    public Workshop(Integer id,String name, String ambass, String school, Integer studentCount,
                    Integer duration, Integer rating, String report) {
        this.ID = id;
        this.name = name;
        this.ambass = ambass;
        this.school = school;
        this.studentCount = studentCount;
        this.duration = duration;
        this.rating = rating;
        this.report = report;
    }

    public Workshop(String name, String ambass, String date) {
        this.name = name;
        this.ambass = ambass;
        this.date = date;
    }

    public Workshop(String name, Integer studentCount, Integer duration, Integer rating/*, String description*/){
        this.name = name;
        this.studentCount = studentCount;
        this.duration = duration;
        this.rating = rating;
        //this.description = description;
    }

    public Workshop(Integer ID){
        this.ID = ID;
    }

    public Workshop(String name){
        this.name = name;
    }

    public Workshop(){
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(Integer ID)
    {
        this.ID = ID;
    }

    public Integer getID(){
        return ID;
    }

    public String getAmbass() {
        return ambass;
    }

    public void setAmbass(String ambass) {
        this.ambass = ambass;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
