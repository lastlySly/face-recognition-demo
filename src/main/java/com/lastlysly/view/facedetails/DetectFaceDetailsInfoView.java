package com.lastlysly.view.facedetails;

import java.util.List;
import java.util.Map;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-09 11:31
 **/
public class DetectFaceDetailsInfoView {

    private ProbabilityAndType expression;
    private ProbabilityAndType face_shape;
    private Double beauty;
    private ProbabilityAndType gender;
    private ProbabilityAndType race;
    private Double face_probability;
    private ProbabilityAndType glasses;
    private String face_token;
    private FaceLocationView location;
    private Integer age;
    private EyeStatusView eye_status;

    private List<Map<String,Double>> landmark72;
    private List<Map<String,Double>> landmark;

    private AngleView angle;

    private FaceQualityView quality;

    public EyeStatusView getEye_status() {
        return eye_status;
    }

    public void setEye_status(EyeStatusView eye_status) {
        this.eye_status = eye_status;
    }

    public ProbabilityAndType getExpression() {
        return expression;
    }

    public void setExpression(ProbabilityAndType expression) {
        this.expression = expression;
    }

    public ProbabilityAndType getFace_shape() {
        return face_shape;
    }

    public void setFace_shape(ProbabilityAndType face_shape) {
        this.face_shape = face_shape;
    }

    public Double getBeauty() {
        return beauty;
    }

    public void setBeauty(Double beauty) {
        this.beauty = beauty;
    }

    public ProbabilityAndType getGender() {
        return gender;
    }

    public void setGender(ProbabilityAndType gender) {
        this.gender = gender;
    }

    public ProbabilityAndType getRace() {
        return race;
    }

    public void setRace(ProbabilityAndType race) {
        this.race = race;
    }

    public Double getFace_probability() {
        return face_probability;
    }

    public void setFace_probability(Double face_probability) {
        this.face_probability = face_probability;
    }

    public ProbabilityAndType getGlasses() {
        return glasses;
    }

    public void setGlasses(ProbabilityAndType glasses) {
        this.glasses = glasses;
    }

    public String getFace_token() {
        return face_token;
    }

    public void setFace_token(String face_token) {
        this.face_token = face_token;
    }

    public FaceLocationView getLocation() {
        return location;
    }

    public void setLocation(FaceLocationView location) {
        this.location = location;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Map<String, Double>> getLandmark72() {
        return landmark72;
    }

    public void setLandmark72(List<Map<String, Double>> landmark72) {
        this.landmark72 = landmark72;
    }

    public List<Map<String, Double>> getLandmark() {
        return landmark;
    }

    public void setLandmark(List<Map<String, Double>> landmark) {
        this.landmark = landmark;
    }

    public AngleView getAngle() {
        return angle;
    }

    public void setAngle(AngleView angle) {
        this.angle = angle;
    }

    public FaceQualityView getQuality() {
        return quality;
    }

    public void setQuality(FaceQualityView quality) {
        this.quality = quality;
    }

    @Override
    public String toString() {
        return "DetectFaceDetailsInfoView{" +
                "expression=" + expression +
                ", face_shape=" + face_shape +
                ", beauty=" + beauty +
                ", gender=" + gender +
                ", race=" + race +
                ", face_probability=" + face_probability +
                ", glasses=" + glasses +
                ", face_token='" + face_token + '\'' +
                ", location=" + location +
                ", age=" + age +
                ", eye_status=" + eye_status +
                ", landmark72=" + landmark72 +
                ", landmark=" + landmark +
                ", angle=" + angle +
                ", quality=" + quality +
                '}';
    }
}
