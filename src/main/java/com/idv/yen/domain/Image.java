package com.idv.yen.domain;

public class Image {
    private Integer id;
    private Integer foreignId;
    private String imagePath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getForeignId() {
        return foreignId;
    }

    public void setForeignId(Integer foreignId) {
        this.foreignId = foreignId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", foreignId=" + foreignId +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
