package com.lastlysly.view.facedetails;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-09 11:36
 **/
public class ProbabilityAndType {
    private Object probability;
    private String type;

    public Object getProbability() {
        return probability;
    }

    public void setProbability(Object probability) {
        this.probability = probability;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ProbabilityAndType{" +
                "probability=" + probability +
                ", type='" + type + '\'' +
                '}';
    }
}
