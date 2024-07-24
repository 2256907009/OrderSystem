package com.example.spritgdemo1.controller;

//import com.example.spritgdemo1.service.LoginFailureService;

import com.example.spritgdemo1.service.LoginFailureService;
import com.example.spritgdemo1.utils.RsaOperate;
import com.example.spritgdemo1.utils.CookieUtil;
import com.example.spritgdemo1.utils.TokenGenerator;
import com.wlkjyy.Eloquent.DB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;


@RestController
public class LoginController {

    @Autowired
    DB JavawebDB;

    @Autowired
    private LoginFailureService loginFailureService;

    @PostMapping("/index")
    public Object login_v(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String captchaId = "2150d8177bd4a551df304168e39f263b";
        String captchaKey = "7699e1fa99dc880941eeb4623ac44932";
        String domain = "http://gcaptcha4.geetest.com";

        String lotNumber = request.getParameter("lot_number");
        String captchaOutput = request.getParameter("captcha_output");
        String passToken = request.getParameter("pass_token");
        String genTime = request.getParameter("gen_time");

        // 3.生成签名
        // 3.generate signature
        // 生成签名使用标准的hmac算法，使用用户当前完成验证的流水号lot_number作为原始消息message，使用客户验证私钥作为key
        // use standard hmac algorithms to generate signatures, and take the user's current verification serial number lot_number as the original message, and the client's verification private key as the key
        // 采用sha256散列算法将message和key进行单向散列生成最终的签名
        // use sha256 hash algorithm to hash message and key in one direction to generate the final signature
        String signToken = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, captchaKey).hmacHex(lotNumber);

        // 4.上传校验参数到极验二次验证接口, 校验用户验证状态
        // 4.upload verification parameters to the secondary verification interface of GeeTest to validate the user verification status
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("lot_number", lotNumber);
        queryParams.add("captcha_output", captchaOutput);
        queryParams.add("pass_token", passToken);
        queryParams.add("gen_time", genTime);
        queryParams.add("sign_token", signToken);
        // captcha_id 参数建议放在 url 后面, 方便请求异常时可以在日志中根据id快速定位到异常请求
        // geetest recommends to put captcha_id parameter after url, so that when a request exception occurs, it can be quickly located in the log according to the id
        String url = String.format(domain + "/validate" + "?captcha_id=%s", captchaId);
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        JSONObject jsonObject = new JSONObject();
        //注意处理接口异常情况，当请求极验二次验证接口异常时做出相应异常处理
        // pay attention to interface exceptions, and make corresponding exception handling when requesting GeeTest secondary verification interface exceptions or response status is not 200
        //保证不会因为接口请求超时或服务未响应而阻碍业务流程
        // website's business will not be interrupted due to interface request timeout or server not-responding
        try {
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(queryParams, headers);
            ResponseEntity<String> responses = client.exchange(url, method, requestEntity, String.class);
            String resBody = responses.getBody();
            jsonObject = new JSONObject(resBody);
            System.out.println(resBody);
        } catch (Exception e) {
            jsonObject.put("result", "success");
            jsonObject.put("reason", "request geetest api fail");

        }

        // 5.根据极验返回的用户验证状态, 网站主进行自己的业务逻辑
        // 5. taking the user authentication status returned from geetest into consideration, the website owner follows his own business logic
        JSONObject res = new JSONObject();

        if (!jsonObject.getString("result").equals("success")) {
            return new HashMap<String, Object>() {
                {
                    put("code", 404);
                    put("message", "验证失败，请刷新重试");
                }
            };
        }


        String password = request.getParameter("password");
        String first_user = request.getParameter("first_user");

        String pri = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALAL8go5DDS/dMKV" + "LL39pWs7hRo5LJA3iWB8jHlCgAAXd9qmuEkcNd0jHCFNnypUQyB81IfA1kTCKkWO" + "zhdcbGaB1EbblJDB0wviq7Jm5sRtD5L5lSQ1IqXWla5T0vN1pwWmVJguZ5IMtDYi" + "v8QmT1QcC9TmJt7/RKzJJZHXUqpZAgMBAAECgYBR0Rtp+N1dXmVrUVz753gLgBv5" + "9fxqAgWMnBXE/UzzvfRr3/PhtoSqA3k9WQ/R4ltYK+uyM8LhLcPrk9TgMooFzHRb" + "tKpYD7HsoHe2xs0W/hgljrABppBN+935XNS9wO6o0txKqbeaeCm+u0IruA5gQDCV" + "hAcr3SuPjjOE8hd4KQJBANyQKiJDZGnzrS3rTVDTamq8SUZLexgut4ZgEJNkgiXP" + "r5u06XpTp/Z9bTvRFV2EbGvvkHdtv3hNGDU/UwUeX6sCQQDMVM2fw/IuIiL+V88n" + "ORM2StoopGpFu//T8zTMxORHSAhZfLAdiqz7+MPZwzQriLAPC+WVzPoJuGtCu0Tx" + "d6oLAkBnncJtfkN6EuPUoqobwnZH1lSGFYeqgKfvFMo0tA8APGU/POpCcXKCtcHP" + "TBZBbKiSvGSd9ozwsycgPLN/wBkpAkBK5h8FnFbu0DaRkvBtST+gnCmB0lreOv6Y" + "28KT+OxQsLfWzZzlB0tHhbWM4kACYTVgHpVGpv9UhtRSfsDT3nBnAkBTF4A8k8wV" + "uFM3eckUUwCIq4ZIOviMfghvSgvFmXV2wQMVkQeCHvuGdyOZTY/sAJQHK8jsPx7K" + "MUE65sDeIRLy";

        try {
            String messageDe = RsaOperate.decrypt(password, pri);
            System.out.println("解密结果：" + messageDe);


            //须在数据库中查询是否有数据
            ArrayList<HashMap<String, Object>> data = JavawebDB.table("login").where("username", first_user).where("password", messageDe).get();
            System.out.println(data);

            //准确判断是否为空，
            if (data != null && !data.isEmpty()) {


                String token = TokenGenerator.generateRandomToken(16);
                String cookie = CookieUtil.setCookie(response, token, 60);


                //传回给前端数据
                return new HashMap<String, Object>() {
                    {
                        put("code", 200);
                        put("message", "success");
                        put("cookie", cookie);


                    }
                };
            } else {
                loginFailureService.recordLoginFailures(first_user);
                System.out.println("账号或密码错误，请重新输入！");
                int failuresCount = loginFailureService.getLoginFailures(first_user);
                System.out.println(failuresCount);

                return new HashMap<String, Object>() {
                    {
                        put("code", 404);
                        put("message", "error");
                        put("failuresCount", failuresCount);
                    }
                };

            }

        } catch (Throwable e) {

            return new HashMap<String, Object>() {
                {
                    put("code", -1);
                    put("message", "error");
                }
            };
        }


    }


}
