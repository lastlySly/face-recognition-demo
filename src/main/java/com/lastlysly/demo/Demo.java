package com.lastlysly.demo;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.util.Base64Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastlysly.utils.MyFaceRecognitionUtils;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-06 18:23
 *
 **/
public class Demo {
    //设置APPID/AK/SK
    private static final String APP_ID = "17194690";
    private static final String API_KEY = "bdKlBpTopnCNkgCYesdStz8u";
    private static final String SECRET_KEY = "sbb3wdb2U7S8BgAllQiIyntfRQMzS2Ge";

//    https://console.bce.baidu.com/ai/s/facelib/face?appId=1209144&groupId=test&uid=3&faceId=c468cb3685f389ea43a36e8bc27d19dc

//
//    public static void main(String[] args) {
//        // 初始化一个AipFace
//        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
//
//        // 可选：设置网络连接参数
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);
//
//        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
//
//        // 调用接口
//        String image = "取决于image_type参数，传入BASE64字符串或URL字符串或FACE_TOKEN字符串";
//        String imageType = "BASE64";
//
//        // 人脸检测
//        JSONObject res = client.detect(image, imageType, options);
//        System.out.println(res.toString(2));
//    }


    public static void main(String[] args) {
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

        ObjectMapper objectMapper = new ObjectMapper();


        HashMap<String,String> userInfo = new HashMap<>(16);
        userInfo.put("age","20");
        userInfo.put("id","file23333");
        userInfo.put("user_info","某某");

        HashMap<String, String> options = new HashMap<String, String>();

        options.put("name","某某2");
        String imageType = "BASE64";
        String groupId = "test";
        String userId = "1";
        File imgFile = new File("F:\\MyAllWorkProject\\xmgps\\司机标准照片\\新建文件夹 (2)\\闽DTL755 2.jpg");
        try {
            byte[] img = MyFaceRecognitionUtils.FileToByte(imgFile);
            String imgStr = Base64Util.encode(img);
//            userInfo.put("fileBase64",imgStr);

            String user_info = objectMapper.writeValueAsString(userInfo);
            System.out.println(user_info);
            options.put("user_info",user_info);
            JSONObject res = client.addUser(imgStr,imageType,groupId,userId,options);
//            System.out.println(res.toString(2));
//            System.out.println("===================");
//            JSONObject res = client.getUser(userId, groupId, options);

//            String sout = res.getJSONObject("result").getJSONArray("user_list").getJSONObject(0).get("user_info").toString();
//            Map mapres = objectMapper.readValue(sout, Map.class);
////            String sout = res.getJSONObject("result").get("user_info").toString();
//            System.out.println("11111111111111");
//            System.out.println(mapres.get("age"));



//            JSONObject res = client.search(imgStr, imageType, groupId, options);

            System.out.println(res.toString(2));


//            https://console.bce.baidu.com/ai/s/facelib/face?appId=1209144&groupId=test&uid=445&faceId=df4beeeb23b863045c5e14be09551ca1


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
