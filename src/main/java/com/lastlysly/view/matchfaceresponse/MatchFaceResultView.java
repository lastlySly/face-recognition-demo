package com.lastlysly.view.matchfaceresponse;

import java.util.List;
import java.util.Map;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-10 14:54
 **/
public class MatchFaceResultView {
    private Double score;
    private List<Map> face_list;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<Map> getFace_list() {
        return face_list;
    }

    public void setFace_list(List<Map> face_list) {
        this.face_list = face_list;
    }

    @Override
    public String toString() {
        return "MatchFaceResultView{" +
                "score=" + score +
                ", face_list=" + face_list +
                '}';
    }
}
