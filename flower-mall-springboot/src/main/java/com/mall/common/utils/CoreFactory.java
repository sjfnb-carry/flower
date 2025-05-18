package com.mall.common.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/*短信工厂*/
@Component
@Data
public class CoreFactory {

    @Value("${aliyun.sys.core.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.sys.core.accessSecret}")
    private String accessSecret;

    @Value("${aliyun.sys.core.signName}")
    private String signName;

    @Value("${aliyun.sys.core.templateCode}")
    private String templateCode;

    public String sendCore(String phone) throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);

        Long aLong = SnowflakeUtil.randomLong();
        String s = randomCode();
        CacheTemplateUtil.setVerificationCode(String.valueOf(aLong),s);
        request.putQueryParameter("TemplateParam", "{\"code\":\"" +s+ "\"}");


        CommonResponse response = client.getCommonResponse(request);
        System.out.println(response.getData());


        return String.valueOf(aLong);
    }


    public String randomCode() {

        String sixChar = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        Date date = new Date();
        String time = sdf.format(date);
        for (int i = 0; i < time.length() / 2; i++) {
            String singleChar;
            String x = time.substring(i * 2, (i + 1) * 2);
            int b = Integer.parseInt(x);
            if (b < 10) {
                singleChar = Integer.toHexString(Integer.parseInt(x));
            } else if (b >= 10 && b < 36) {
                singleChar = String.valueOf((char) (Integer.parseInt(x) + 55));
            } else {
                singleChar = String.valueOf((char) (Integer.parseInt(x) + 61));
            }
            sixChar = sixChar + singleChar;

        }
        return sixChar;

    }
}
