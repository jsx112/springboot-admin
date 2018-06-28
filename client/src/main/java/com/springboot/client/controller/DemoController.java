package com.springboot.client.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2017/11/19.
 */
@RestController
@RequestMapping("demo")
public class DemoController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("add")
    @HystrixCommand(fallbackMethod="errorDefaultAdd")
    public Integer getServer(@RequestParam Integer a, @RequestParam Integer b){
        return restTemplate.getForObject("http://EUREKA-SERVER/demo/add?a="+a+"&b="+b,Integer.class);
    }

    private Integer errorDefaultAdd(Integer a,  Integer b){
        return 0;
    }
}
