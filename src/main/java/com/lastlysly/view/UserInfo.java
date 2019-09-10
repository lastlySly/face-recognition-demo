package com.lastlysly.view;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-10 09:34
 **/
public class UserInfo {
    private String id;
    private String carPlateNumber;
    private String photoImgPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    public String getPhotoImgPath() {
        return photoImgPath;
    }

    public void setPhotoImgPath(String photoImgPath) {
        this.photoImgPath = photoImgPath;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", carPlateNumber='" + carPlateNumber + '\'' +
                ", photoImgPath='" + photoImgPath + '\'' +
                '}';
    }
}
