package model;

public class Ambassador {

    private String name;
    private Integer numOfWs;
    private String address;
    private String mail;
    private String phone;
    private Boolean trained;


    public Ambassador(String name, String address, String mail, String phone, Boolean trained) {
        this.name = name;
        this.address = address;
        this.mail = mail;
        this.phone = phone;
        this.trained = trained;
    }

    public Ambassador(String name, Integer numOfWs) {
        this.name = name;
        this.numOfWs = numOfWs;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumOfWs() {
        return numOfWs;
    }

    public void setNumOfWs(Integer numOfWs) {
        this.numOfWs = numOfWs;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getTrained() {
        return trained;
    }

    public void setTrained(Boolean trained) {
        this.trained = trained;
    }
}
