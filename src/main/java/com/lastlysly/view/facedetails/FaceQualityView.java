package com.lastlysly.view.facedetails;

import java.util.Map;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-09 16:29
 **/
public class FaceQualityView {
    private Double illumination;
    private Map occlusion;
    private Double blur;
    private Double completeness;

    public Double getIllumination() {
        return illumination;
    }

    public void setIllumination(Double illumination) {
        this.illumination = illumination;
    }

    public Map getOcclusion() {
        return occlusion;
    }

    public void setOcclusion(Map occlusion) {
        this.occlusion = occlusion;
    }

    public Double getBlur() {
        return blur;
    }

    public void setBlur(Double blur) {
        this.blur = blur;
    }

    public Double getCompleteness() {
        return completeness;
    }

    public void setCompleteness(Double completeness) {
        this.completeness = completeness;
    }
}
