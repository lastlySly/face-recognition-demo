package com.lastlysly.utils;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.FaceVerifyRequest;
import com.baidu.aip.face.MatchRequest;
import com.baidu.aip.util.Base64Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.lastlysly.view.UserInfo;
import com.lastlysly.view.facedetails.DetectFaceInfoView;
import com.lastlysly.view.matchfaceresponse.MatchFaceResponseView;
import com.lastlysly.view.searchfaceresponse.SearchFaceResponseView;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-08 08:21
 **/
public class MyFaceRecognitionUtils {

    //private static final BASE64Decoder decoder = new BASE64Decoder();
    //需要修改为自己的
    private static final String AppID = "******";
    private static final String APIKey = "*********";
    private static final String SecretKey = "********";

    static AipFace client = null;
    static {
        client = new AipFace(AppID, APIKey, SecretKey);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
    }

    /**
     * 人脸检测
     *
     * @return
     * @throws IOException
     */
    public static DetectFaceInfoView detectFace(File file, String max_face_num) throws IOException {
        return detectFace(FileToByte(file), max_face_num);
    }

    /**
     * 人脸检测
     *
     * @return
     * @throws IOException
     */
    public static DetectFaceInfoView detectFace(byte[] arg0, String maxFaceNum) {
        try {

            HashMap<String, String> options = new HashMap<String, String>();
            options.put("face_field",
                    "age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities,eye_status,quality");
            options.put("max_face_num", maxFaceNum);
//            options.put("face_type", "LIVE");

            // 图片数据
            String imgStr = Base64Util.encode(arg0);
            String imageType = "BASE64";
            JSONObject res = client.detect(imgStr, imageType, options);
            ObjectMapper objectMapper = new ObjectMapper();

            if (res.getInt("error_code") != 0){
                DetectFaceInfoView detectFaceInfoView = new DetectFaceInfoView();
                detectFaceInfoView.setError_msg(res.getString("error_msg"));
                detectFaceInfoView.setError_code(res.getInt("error_code"));
                return detectFaceInfoView;
            }

            DetectFaceInfoView detectFaceInfoView =
                    objectMapper.readValue(res.getJSONObject("result").toString(2),DetectFaceInfoView.class);
            detectFaceInfoView.setError_msg(res.getString("error_msg"));
            detectFaceInfoView.setError_code(res.getInt("error_code"));
            return detectFaceInfoView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 人脸比对
     *
     * @param file1
     * @param file2
     * @return
     */
    public static MatchFaceResponseView matchFace(File file1, File file2) throws IOException {
        return matchFace(FileToByte(file1), FileToByte(file2));
    }

    /**
     * 人脸比对
     *
     * @param arg0
     *            人脸1
     * @param arg1
     *            人脸2
     * @return
     */
    public static MatchFaceResponseView matchFace(byte[] arg0, byte[] arg1) throws IOException {
        String imgStr1 = Base64Util.encode(arg0);
        String imgStr2 = Base64Util.encode(arg1);
        MatchRequest req1 = new MatchRequest(imgStr1, "BASE64");
        MatchRequest req2 = new MatchRequest(imgStr2, "BASE64");
        ArrayList<MatchRequest> requests = new ArrayList<MatchRequest>();
        requests.add(req1);
        requests.add(req2);
        JSONObject res = client.match(requests);
        ObjectMapper objectMapper = new ObjectMapper();
        MatchFaceResponseView matchFaceResponseView = objectMapper
                .readValue(res.toString(2),MatchFaceResponseView.class);
        return matchFaceResponseView;
    }

    /**
     * 人脸搜索(userId 传null 为 1：N人脸搜索)
     *      1：N人脸搜索：也称为1：N识别，在指定人脸集合中，找到最相似的人脸；
     *      1：N人脸认证：基于uid维度的1：N识别，由于uid已经锁定固定数量的人脸，所以检索范围更聚焦；
     * @param file
     * @param groupIdList
     * @param userId
     * @return
     */
    public static SearchFaceResponseView searchFace(File file, String groupIdList,
                                                    String userId,String max_user_num) throws IOException {

        return searchFace(FileToByte(file), groupIdList, userId,max_user_num);
    }

    /**
     * 人脸搜索(userId 传null 为 1：N人脸搜索)
     *      1：N人脸搜索：也称为1：N识别，在指定人脸集合中，找到最相似的人脸；
     *      1：N人脸认证：基于uid维度的1：N识别，由于uid已经锁定固定数量的人脸，所以检索范围更聚焦；
     * @param arg0
     * @param groupIdList
     * @return
     */
    public static SearchFaceResponseView searchFace(byte[] arg0, String groupIdList,
                                                    String userId,String max_user_num) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String imgStr = Base64Util.encode(arg0);
        String imageType = "BASE64";
        HashMap<String, String> options = new HashMap<String, String>();
//        options.put("quality_control", "NORMAL");
//        options.put("liveness_control", "LOW");
        if (userId != null) {
            options.put("user_id", userId);
        }
        //查找后返回的用户数量。返回相似度最高的几个用户，默认为1，最多返回50个。
        options.put("max_user_num", max_user_num);
        JSONObject res = client.search(imgStr, imageType, groupIdList, options);
        SearchFaceResponseView searchFaceResponseView = objectMapper
                .readValue(res.toString(2),SearchFaceResponseView.class);
        if (searchFaceResponseView.getError_code() == 0){
            //由于 user_info在返回值中为字符串，无法序列化，故单独提取，序列化后存入userInfo
            for (int i = 0; i<searchFaceResponseView.getResult().getUser_list().size(); i++){
                UserInfo userInfo = objectMapper.readValue(searchFaceResponseView.getResult()
                        .getUser_list().get(i).getUser_info(), UserInfo.class);
                searchFaceResponseView.getResult()
                        .getUser_list().get(i).setUserInfo(userInfo);
            }
        }


        return searchFaceResponseView;
    }

    /**
     * 增加用户
     *
     * @param file
     * @param userInfo
     * @param userId
     * @param groupId
     * @return
     */
    public static String addUser(File file, String userInfo, String userId,
                                 String groupId) throws IOException {

        return addUser(FileToByte(file), userInfo, userId, groupId);
    }

    /**
     * 增加用户
     *
     * @param arg0
     * @param userInfo
     * @param userId
     * @param groupId
     * @return
     */
    public static String addUser(byte[] arg0, String userInfo, String userId,String groupId) {
        String imgStr = Base64Util.encode(arg0);
        String imageType = "BASE64";
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("user_info", userInfo);
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");

        JSONObject res = client.addUser(imgStr, imageType, groupId, userId,options);
        return res.toString(2);
    }

    public static String updateUser(File file, String userInfo, String userId,
                                    String groupId) throws IOException {

        return updateUser(FileToByte(file), userInfo, userId, groupId);

    }

    /**
     * 更新用户
     *
     * @param arg0
     * @param userInfo
     * @param userId
     * @param groupId
     * @return
     */
    public static String updateUser(byte[] arg0, String userInfo,
                                    String userId, String groupId) {
        String imgStr = Base64Util.encode(arg0);
        String imageType = "BASE64";
        HashMap<String, String> options = new HashMap<String, String>();
        if (userInfo != null) {
            options.put("user_info", userInfo);
        }
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");

        JSONObject res = client.updateUser(imgStr, imageType, groupId, userId,
                options);
        return res.toString(2);
    }

    /**
     * 删除用户人脸信息
     *
     * @param userId
     * @param groupId
     * @param faceToken
     * @return
     */
    public static String deleteUserFace(String userId, String groupId,
                                        String faceToken) {
        HashMap<String, String> options = new HashMap<String, String>();
        // 人脸删除
        JSONObject res = client.faceDelete(userId, groupId, faceToken, options);
        return res.toString();
    }

    /**
     * 查询用户信息
     *
     * @param userId
     * @param groupId
     * @return
     */
    public static String searchUserInfo(String userId, String groupId) {
        HashMap<String, String> options = Maps.newHashMap();
        // 用户信息查询
        JSONObject res = client.getUser(userId, groupId, options);
        return res.toString(2);
    }

    /**
     * 获取用户人脸列表
     *
     * @param userId
     * @param groupId
     * @return
     */
    public static String getUserFaceList(String userId, String groupId) {
        HashMap<String, String> options = Maps.newHashMap();
        // 获取用户人脸列表
        JSONObject res = client.faceGetlist(userId, groupId, options);
        return res.toString(2);
    }

    /**
     * 获取一组用户
     *
     * @param groupId
     * @param returnNum
     * @return
     */
    public static List<String> getGroupUsers(String groupId, String returnNum) {
        HashMap<String, String> options = Maps.newHashMap();
        options.put("start", "0");
        if (returnNum != null) {
            options.put("length", returnNum);
        }
        // 获取用户列表
        JSONObject res = client.getGroupUsers(groupId, options);
        JSONObject userIdListJb = res.getJSONObject("result");
        Map<String, Object> map = userIdListJb.toMap();

        List<String> userIdList = (List<String>) map.get("user_id_list");

        return userIdList;
    }

    /**
     * 组用户复制
     *
     * @param userId
     * @param srcGroupId
     * @param dstGroupId
     * @return
     */
    public static String userCopy(String userId, String srcGroupId,
                                  String dstGroupId) {
        HashMap<String, String> options = Maps.newHashMap();
        options.put("src_group_id", srcGroupId);
        options.put("dst_group_id", dstGroupId);
        // 复制用户
        JSONObject res = client.userCopy(userId, options);
        return res.toString(2);
    }

    /**
     * 删除用户
     *
     * @param userId
     * @param groupId
     * @return
     */
    public static String deleteUser(String userId, String groupId) {
        HashMap<String, String> options = Maps.newHashMap();
        // 人脸删除
        JSONObject res = client.deleteUser(groupId, userId, options);
        return res.toString();
    }

    /**
     * 增加组信息
     *
     * @param groupId
     * @return
     */
    public static String addGroup(String groupId) {
        HashMap<String, String> options = Maps.newHashMap();
        // 创建用户组
        JSONObject res = client.groupAdd(groupId, options);
        return res.toString();
    }

    /**
     * 删除组
     *
     * @param groupId
     * @return
     */
    public static String deleteGroup(String groupId) {
        HashMap<String, String> options = Maps.newHashMap();
        // 创建用户组
        JSONObject res = client.groupDelete(groupId, options);
        return res.toString();
    }

    /**
     * 获取组列表
     *
     * @param length
     * @return
     */
    public static String getGroupList(String length) {
        HashMap<String, String> options = Maps.newHashMap();
        options.put("start", "0");
        options.put("length", length);
        // 组列表查询
        JSONObject res = client.getGroupList(options);
        return res.toString();
    }

    /**
     * 活体检测
     *
     * @param arg0
     * @return
     */
    public static String faceverify(byte[] arg0) {
        String imgStr = Base64Util.encode(arg0);
        String imageType = "BASE64";
        FaceVerifyRequest req = new FaceVerifyRequest(imgStr, imageType);
        ArrayList<FaceVerifyRequest> list = new ArrayList<FaceVerifyRequest>();
        list.add(req);
        JSONObject res = client.faceverify(list);
        return res.toString();
    }

    /**
     * 文件转字节
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] FileToByte(File file) throws IOException {
        // 将数据转为流
        @SuppressWarnings("resource")
        InputStream content = new FileInputStream(file);
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = content.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        // 获得二进制数组
        return swapStream.toByteArray();
    }

    /**
     * 删除某组下所有用户
     * （获取哦用户列表，最多1000个，该接口待修改）
     * @param groupId
     * @param delSign
     * @return
     */
    public static boolean delAllUsersByGroupId(String groupId,String delSign) throws InterruptedException {
        List<String> userIdList = getGroupUsers(groupId,delSign);
//        System.out.println(userIdList);

        for (int i = 0;i < userIdList.size(); i ++){
            System.out.println("开始删除："+ userIdList.get(i));
            String str = deleteUser(userIdList.get(i),groupId);
//            QPS 超限。当前为2。
            Thread.sleep(500);
            System.out.println("删除完成："+ userIdList.get(i));
        }
        return true;
    }

    public static void main(String[] args){
        try {
            String res = searchUserInfo("2d0f50601ac644b382233cfdf8e549ac","test");
            System.out.println(res);
//            delAllUsersByGroupId("test","1000");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
