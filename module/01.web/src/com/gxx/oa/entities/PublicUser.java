package com.gxx.oa.entities;

/**
 * 公众账号实体
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-4 19:01
 */
public class PublicUser {
    int id;
    String name;
    String shortName;
    String englishName;
    String headPhoto;
    String url;

    /**
     * 查询时候用(没有程序创建部分，所有没有新增用的构造函数)
     * @param id
     * @param name
     * @param shortName
     * @param englishName
     * @param headPhoto
     * @param url
     */
    public PublicUser(int id, String name, String shortName, String englishName, String headPhoto, String url) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.englishName = englishName;
        this.headPhoto = headPhoto;
        this.url = url;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
