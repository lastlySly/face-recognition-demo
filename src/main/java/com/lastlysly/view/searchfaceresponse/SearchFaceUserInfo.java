package com.lastlysly.view.searchfaceresponse;

import com.lastlysly.view.UserInfo;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-10 09:45
 **/
public class SearchFaceUserInfo {
    /**
     * 用户的匹配得分
     */
    private Double score;

    private String group_id;

    private String user_id;

    private UserInfo userInfo;

    private String user_info;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_info() {
        return user_info;
    }

    public void setUser_info(String user_info) {
        this.user_info = user_info;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "SearchFaceUserInfo{" +
                "score=" + score +
                ", group_id='" + group_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_info='" + user_info + '\'' +
                '}';
    }
}
