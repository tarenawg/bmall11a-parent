package com.bw.bmall11a.logger.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bw.bmall11a.common.constant.BmallConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class LoggerController {
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    @PostMapping("/log")
    @ResponseBody
    public String log(@RequestParam("logString") String logString){
       JSONObject jsonObject =  JSON.parseObject(logString);
       jsonObject.put("ts",System.currentTimeMillis());
       if("startup".equals(jsonObject.get("type"))){
         kafkaTemplate.send(BmallConstant.KAFKA_STARTUP,jsonObject.toString());
       }else{
           kafkaTemplate.send(BmallConstant.KAFKA_EVENT,jsonObject.toString());
       }
        log.info(logString);
        return "success";
    }
}
