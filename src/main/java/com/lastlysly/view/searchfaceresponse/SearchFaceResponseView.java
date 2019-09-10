package com.lastlysly.view.searchfaceresponse;


import java.io.File;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-10 09:41
 **/
public class SearchFaceResponseView {
    private SearchFaceResultView result;

    private Long log_id;

    private String error_msg;

    private int cached;

    private int error_code;

    private Long timestamp;

    public SearchFaceResultView getResult() {
        return result;
    }

    public void setResult(SearchFaceResultView result) {
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
        return "SearchFaceResponseView{" +
                "result=" + result +
                ", log_id=" + log_id +
                ", error_msg='" + error_msg + '\'' +
                ", cached=" + cached +
                ", error_code=" + error_code +
                ", timestamp=" + timestamp +
                '}';
    }


    /*==============非API返回值===============*/

    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
