package com.project.controller;

import com.project.annotation.ResObjectAnno;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @program: extension
 * @description: 测试控制器
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-09-12 13:58
 **/

@RequestMapping("/api/v1")
@RestController
@ResObjectAnno  //自定义统一返回类注解
public class testController {

    @GetMapping("/test1")
    public HashMap<String,Object> test1() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","test");
        return map;
    }
}
