package model;

public class SchoolDetail {

    private Integer id;
    private String name;
    private String city;
    private String region;
    private Integer studentsCount;
    private String language;
    private String headmasterName;
    private String headmasterPhone;
    private String headmasterMail;
    private String secretaryPhone;
    private String secretaryMail;
    private String teacherName;
    private String teacherMail;
    private Integer wsCount;
    private Integer wsDone;
    private Integer wsPlanned;
    private Integer wsStudAverage;
    private Integer wsDurAverage;
    private Double wsAmbRatAverage;
    private Double schRatAverage;
    private Double schRatEmpAverage;
    private Double schRatAmbAverage;


    public SchoolDetail() {
    }

    public SchoolDetail(String name, String city, String region, Integer studentsCount,
                        String language, String headmasterName, String headmasterPhone,
                        String headmasterMail, String secretaryPhone, String secretaryMail,
                        String teacherName, String teacherMail) {
        this.name = name;
        this.city = city;
        this.region = region;
        this.studentsCount = studentsCount;
        this.language = language;
        this.headmasterName = headmasterName;
        this.headmasterPhone = headmasterPhone;
        this.headmasterMail = headmasterMail;
        this.secretaryPhone = secretaryPhone;
        this.secretaryMail = secretaryMail;
        this.teacherName = teacherName;
        this.teacherMail = teacherMail;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(Integer studentsCount) {
        this.studentsCount = studentsCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadmasterName() {
        return headmasterName;
    }

    public void setHeadmasterName(String headmasterName) {
        this.headmasterName = headmasterName;
    }

    public String getHeadmasterPhone() {
        return headmasterPhone;
    }

    public void setHeadmasterPhone(String headmasterPhone) {
        this.headmasterPhone = headmasterPhone;
    }

    public String getHeadmasterMail() {
        return headmasterMail;
    }

    public void setHeadmasterMail(String headmasterMail) {
        this.headmasterMail = headmasterMail;
    }

    public String getSecretaryPhone() {
        return secretaryPhone;
    }

    public void setSecretaryPhone(String secretaryPhone) {
        this.secretaryPhone = secretaryPhone;
    }

    public String getSecretaryMail() {
        return secretaryMail;
    }

    public void setSecretaryMail(String secretaryMail) {
        this.secretaryMail = secretaryMail;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherMail() {
        return teacherMail;
    }

    public void setTeacherMail(String teacherMail) {
        this.teacherMail = teacherMail;
    }

    public Integer getWsCount() {
        return wsCount;
    }

    public void setWsCount(Integer wsCount) {
        this.wsCount = wsCount;
    }

    public Integer getWsDone() {
        return wsDone;
    }

    public void setWsDone(Integer wsDone) {
        this.wsDone = wsDone;
    }

    public Integer getWsPlanned() {
        return wsPlanned;
    }

    public void setWsPlanned(Integer wsPlanned) {
        this.wsPlanned = wsPlanned;
    }

    public Integer getWsStudAverage() {
        return wsStudAverage;
    }

    public void setWsStudAverage(Integer wsStudAverage) {
        this.wsStudAverage = wsStudAverage;
    }

    public Integer getWsDurAverage() {
        return wsDurAverage;
    }

    public void setWsDurAverage(Integer wsDurAverage) {
        this.wsDurAverage = wsDurAverage;
    }

    public Double getWsAmbRatAverage() {
        return wsAmbRatAverage;
    }

    public void setWsAmbRatAverage(Double wsAmbRatAverage) {
        this.wsAmbRatAverage = wsAmbRatAverage;
    }

    public Double getSchRatAverage() {
        return schRatAverage;
    }

    public void setSchRatAverage(Double schRatAverage) {
        this.schRatAverage = schRatAverage;
    }

    public Double getSchRatEmpAverage() {
        return schRatEmpAverage;
    }

    public void setSchRatEmpAverage(Double schRatEmpAverage) {
        this.schRatEmpAverage = schRatEmpAverage;
    }

    public Double getSchRatAmbAverage() {
        return schRatAmbAverage;
    }

    public void setSchRatAmbAverage(Double schRatAmbAverage) {
        this.schRatAmbAverage = schRatAmbAverage;
    }
}

