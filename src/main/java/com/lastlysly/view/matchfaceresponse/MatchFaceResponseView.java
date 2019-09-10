package com.lastlysly.view.matchfaceresponse;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-10 14:50
 **/
public class MatchFaceResponseView {
    private MatchFaceResultView result;

    private Long log_id;

    private String error_msg;

    private int cached;

    private int error_code;

    private Long timestamp;

    public MatchFaceResultView getResult() {
        return result;
    }

    public void setResult(MatchFaceResultView result) {
        this.result = result;
    }

    public Long getLog_id() {
        return log_id;
    }

    public void setLog_id(Long log_id) {
        this.log_id = log_id;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public int getCached() {
        return cached;
    }

    public void setCached(int cached) {
        this.cached = cached;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MatchFaceResponseView{" +
                "result=" + result +
                ", log_id=" + log_id +
                ", error_msg='" + error_msg + '\'' +
                ", cached=" + cached +
                ", error_code=" + error_code +
                ", timestamp=" + timestamp +
                '}';
    }
}
