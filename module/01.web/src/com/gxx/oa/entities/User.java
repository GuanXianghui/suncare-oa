package com.gxx.oa.entities;

/**
 * 用户实体
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-29 18:21
 */
public class User {
    int id;
    String name;
    String password;
    String letter;
    int state;
    String company;
    String dept;
    String position;
    String desk;
    int sex;
    String birthday;
    String officeTel;
    String mobileTel;
    String email;
    String qq;
    String msn;
    String address;
    String headPhoto;
    String website;
    String registerDate;
    String registerTime;
    String registerIp;
    String visitDate;
    String visitTime;
    String visitIp;

    /**
     * 新建时用构造函数
     *
     * @param name
     * @param password
     * @param letter
     * @param state
     * @param company
     * @param dept
     * @param position
     * @param desk
     * @param sex
     * @param birthday
     * @param officeTel
     * @param mobileTel
     * @param email
     * @param qq
     * @param msn
     * @param address
     * @param headPhoto
     * @param website
     * @param registerDate
     * @param registerTime
     * @param registerIp
     * @param visitDate
     * @param visitTime
     * @param visitIp
     */
    public User(String name, String password, String letter, int state, String company, String dept,
                String position, String desk, int sex, String birthday, String officeTel, String mobileTel,
                String email, String qq, String msn, String address, String headPhoto, String website,
                String registerDate, String registerTime, String registerIp, String visitDate, String visitTime,
                String visitIp) {
        this.name = name;
        this.password = password;
        this.letter = letter;
        this.state = state;
        this.company = company;
        this.dept = dept;
        this.position = position;
        this.desk = desk;
        this.sex = sex;
        this.birthday = birthday;
        this.officeTel = officeTel;
        this.mobileTel = mobileTel;
        this.email = email;
        this.qq = qq;
        this.msn = msn;
        this.address = address;
        this.headPhoto = headPhoto;
        this.website = website;
        this.registerDate = registerDate;
        this.registerTime = registerTime;
        this.registerIp = registerIp;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.visitIp = visitIp;
    }

    /**
     * 查询时用构造函数
     *
     * @param id
     * @param name
     * @param password
     * @param letter
     * @param state
     * @param company
     * @param dept
     * @param position
     * @param desk
     * @param sex
     * @param birthday
     * @param officeTel
     * @param mobileTel
     * @param email
     * @param qq
     * @param msn
     * @param address
     * @param headPhoto
     * @param website
     * @param registerDate
     * @param registerTime
     * @param registerIp
     * @param visitDate
     * @param visitTime
     * @param visitIp
     */
    public User(int id, String name, String password, String letter, int state, String company, String dept,
                String position, String desk, int sex, String birthday, String officeTel, String mobileTel,
                String email, String qq, String msn, String address, String headPhoto, String website,
                String registerDate, String registerTime, String registerIp, String visitDate, String visitTime,
                String visitIp) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.letter = letter;
        this.state = state;
        this.company = company;
        this.dept = dept;
        this.position = position;
        this.desk = desk;
        this.sex = sex;
        this.birthday = birthday;
        this.officeTel = officeTel;
        this.mobileTel = mobileTel;
        this.email = email;
        this.qq = qq;
        this.msn = msn;
        this.address = address;
        this.headPhoto = headPhoto;
        this.website = website;
        this.registerDate = registerDate;
        this.registerTime = registerTime;
        this.registerIp = registerIp;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.visitIp = visitIp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getOfficeTel() {
        return officeTel;
    }

    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }

    public String getMobileTel() {
        return mobileTel;
    }

    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public String getVisitIp() {
        return visitIp;
    }

    public void setVisitIp(String visitIp) {
        this.visitIp = visitIp;
    }
}
