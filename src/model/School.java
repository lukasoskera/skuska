package model;

public class School {

    private Integer id;
    private String name;
    private String region;
    private String city;
    private Integer studentsCount;
    private Boolean wsActive;

    public School(Integer id, String name, String region, String city, Integer studentsCount, Boolean wsActive) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.city = city;
        this.studentsCount = studentsCount;
        this.wsActive = wsActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(Integer studentsCount) {
        this.studentsCount = studentsCount;
    }

    public boolean isWsActive() {
        return wsActive;
    }

    public void setWsActive(Boolean wsActive) {
        this.wsActive = wsActive;
    }
}
