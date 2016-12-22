import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import http.CommonOperate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Spark;

import java.io.File;
import java.io.IOException;

/**
 * Created by Leo on 2016/12/21.
 */
public class Main {
    static String api_key="pPcCQavGfltqRq6m8vIFgALMpmaS3BhI";
    static String api_secret="dbwoZSx8BUYRIwQudeSxZiab7WnQEhST";
    static CommonOperate commonOperate=new CommonOperate(api_key,api_secret);

    static String personName;
    static String sex;
    static String nation;
    static String birthday;
    static String address;
    static String personId;
    static String department;
    static String startDate;
    static String endDate;
    static String imgPath;
    static String videoPath;
    static String picPath;
    private static boolean isMacOpen=false;

    static Dispatch disp;
    static ActiveXComponent com;
    public static void main(String[] args) throws InterruptedException {
            getMsg();
        //compareReturn();
        compare();
    }

    private static void compare() {
        Spark.post("/api/compare",(request, response) -> {
            responseJson rj=new responseJson();
            picPath=request.queryParams("picPath");

            if (picPath==""){
                rj.setDetail("path not found");
                rj.setStatus("fail");
            }else {
                int confidence=CompareFace(picPath,imgPath);
                if (confidence==1){
                    rj.setDetail("token create fail");
                    rj.setStatus("fail");
                }else if (confidence==0){
                    rj.setDetail("confindence too low");
                    rj.setStatus("fail");
                }else {
                    rj.setDetail(String.valueOf(confidence));
                    rj.setStatus("success");
                }
            }
            return rj;
        });
    }
    public static int CompareFace(String personPicPath, String picPath) throws Exception {
        File file1=new File(personPicPath);
        isFileExist(file1);
        File fileCompare=new File(picPath);
        isFileExist(fileCompare);
        byte[] result1= commonOperate.detectFile(file1);
        String token1= resultGetFaceToken(result1);
        if (token1==null){
            return 1;
        }
        byte[] result2= commonOperate.detectFile(fileCompare);
        String token2= resultGetFaceToken(result2);
        if (token1==null){
            return 1;
        }
        byte[] compareResult=commonOperate.compare(token1,token2);
        String s=new String(compareResult);
        JSONObject jsonObject=new JSONObject(s);
        JSONObject levelObject=jsonObject.getJSONObject("thresholds");
        int le_3=levelObject.getInt("1e-3");
        int le_4=levelObject.getInt("1e-3");
        int le_5=levelObject.getInt("1e-3");
        int confidence= (int) jsonObject.getDouble("confidence");
        if (confidence<le_3){
            return 0;
        }else {
            return confidence;
        }
    }
    private static String resultGetFaceToken(byte[] result) throws JSONException {
        String s=new String(result);
        JSONObject jsonObject = new JSONObject(s);
        JSONArray jsonArray = (JSONArray) jsonObject.get("faces");
        JSONObject object = jsonArray.optJSONObject(0);
        if (object==null) {
            return null;
        }else {
            String str = object.getString("face_token");
            return str;
        }
    }

    //    private static void compareReturn() {
//        Spark.post("/api/compareReturn",(request, response) -> {
//            picPath=request.queryParams("picPath");
//            if (picPath==""){
//                File file=new File("E:\\demo\\picFile");
//                isFileExist(file);
//                picPath=file.getAbsolutePath();
//            }
//        });
//    }
    private static void isFileExist(File file) throws IOException {
        if (file.exists()){
            System.out.println("exist");
        }else {
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdir();
            }
            file.createNewFile();
        }
    }
    /**
 *Author:ace
 *@Date: 2016/12/22 9:19
 *Decpration: 获取身份证信息
 */
    private static void getMsg() {
        Spark.get("/api/getMsg",(request, response) -> {
            responseJson rj=new responseJson();
            responseModel model=new responseModel();
            response.header("Content-Type", "application/json");
        //    jacobInit();
            File file=new File("E:\\demo\\person.jpg");
            isFileExist(file);
            ActiveXComponent com = new ActiveXComponent("CLSID:0F55CC69-97EF-42A9-B63D-D1831CB2B3B9");
            Dispatch disp = com.getObject();
            int ret = Dispatch.call(disp, "getCardInfo", new Variant("E:\\demo\\person.jpg")).getInt();
            if (ret==0) {
                personName = Dispatch.call(disp, "Name").getString().trim();
                sex = Dispatch.call(disp, "Sex").getString().trim();
                nation = Dispatch.call(disp, "Nation").getString().trim();
                birthday = Dispatch.call(disp, "Birthday").getString().trim();
                address = Dispatch.call(disp, "Address").getString().trim();
                personId = Dispatch.call(disp, "ID").getString().trim();
                department = Dispatch.call(disp, "Department").getString().trim();
                startDate = Dispatch.call(disp, "StartDate").getString().trim();
                endDate = Dispatch.call(disp, "EndDate").getString().trim();
                String path="E:\\demo\\person.jpg";
                imgPath = "E://demo//person.jpg";
                inputMsg(model);
                rj.setDetail(model.toString());
                System.out.println(rj.getDetail());
                rj.setStatus("success");
                isMacOpen=true;
                return rj.toJsonString();
            }else {
                isMacOpen=false;
                rj.setDetail("mac not open");
                rj.setStatus("fail");
                System.out.println("设备未连接");
                return rj.toString();
            }


        });
    }

    private static void jacobInit() {
         com = new ActiveXComponent("CLSID:0F55CC69-97EF-42A9-B63D-D1831CB2B3B9");
        disp = (Dispatch) com.getObject();
    }

    /**
 *Author:ace
 *@Date: 2016/12/22 9:19
 *Decpration: 信息放入
 */
    private static void inputMsg(responseModel model) {
        model.setPersonName(personName);
        model.setSex(sex);
        model.setNation(nation);
        model.setAddress(address);
        model.setBirthday(birthday);
        model.setDepartment(department);
        model.setStartDate(startDate);
        model.setEndDate(endDate);
        model.setImgPath(imgPath);
        model.setPersonId(personId);
    }


}
