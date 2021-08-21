package com.example.water;

public class Order {
    private String orderid;
    private String numofcans;
    private String amount;
    private String name;
    private String phone;
    private String date;
    private String address;
    private String landmark;

    public Order() {
    }

    public Order(String orderid, String numofcans, String amount, String name, String phone, String date, String address, String landmark) {
        this.orderid = orderid;
        this.numofcans = numofcans;
        this.amount = amount;
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.address = address;
        this.landmark = landmark;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getNumofcans() {
        return numofcans;
    }

    public void setNumofcans(String numofcans) {
        this.numofcans = numofcans;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    //    private String userid;
//    private String username;
//    private String useremail;
//    private String usermobileno;
//
//    public Order() {
//
//    }
//
//    public Order(String userid, String username, String useremail, String usermobileno)
//    {
//        this.userid = userid;
//        this.username = username;
//        this.useremail = useremail;
//        this.usermobileno = usermobileno;
//    }
//    public String getUserid() {
//        return userid;
//    }
//    public void setUserid(String userid) {
//        this.userid = userid;
//    }
//    public String getUsername() {
//        return username;
//    }
//    public void setUsername(String username) {
//        this.username = username;
//    }
//    public String getUseremail() {
//        return useremail;
//    }
//    public void setUseremail(String useremail) {
//        this.useremail = useremail;
//    }
//
//    public String getUsermobileno() {
//        return usermobileno;
//    }
//    public void setUsermobileno(String usermobileno) {
//        this.usermobileno = usermobileno;
//    }
}