package com.lastlysly.view.searchfaceresponse;

import java.util.List;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-10 09:39
 **/
public class SearchFaceResultView {
    private String face_token;

    private List<SearchFaceUserInfo> user_list;

    public String getFace_token() {
        return face_token;
    }

    public void setFace_token(String face_token) {
        this.face_token = face_token;
    }

    public List<SearchFaceUserInfo> getUser_list() {
        return user_list;
    }

    public void setUser_list(List<SearchFaceUserInfo> user_list) {
        this.user_list = user_list;
    }

    @Override
    public String toString() {
        return "SearchFaceResultView{" +
                "face_token='" + face_token + '\'' +
                ", user_list=" + user_list +
                '}';
    }
}
