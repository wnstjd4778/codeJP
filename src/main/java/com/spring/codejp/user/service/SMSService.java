package com.spring.codejp.user.service;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import netscape.javascript.JSObject;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;

@Service
@Slf4j
public class SMSService {

    // 인증번호 전송
    @Transactional
    public void sendMessage(String tel, String message) {
        String API_KEY = "NCSU7VI0RR3W4LFK";
        String API_SECRET = "FIIRKKFNZZOQ09RJKZN3A8W8IYKZRZ6K";
        Message msg = new Message(API_KEY, API_SECRET);

        HashMap<String, String> params = new HashMap<>();
        params.put("to", tel); // 누구에게 보낼건지
        params.put("from", "01065614778"); // 누구 번호에서 보낼건지
        params.put("type", "SMS"); // 타입 정하기
        params.put("text", message);
        params.put("app_version", "test app 1.2"); // 이름과 서버

        try {
            log.info("message: " + message);
            JSONObject object = (JSONObject) msg.send(params);
            log.info(object.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
