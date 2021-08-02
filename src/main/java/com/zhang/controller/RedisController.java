package com.zhang.controller;

import com.zhang.entity.Stu;
import com.zhang.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/redis")
public class RedisController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取对应键的值，redisTemplate操作
     * @param key 键
     * @return
     */
    @RequestMapping(value = "get")
    public Object getValue(String key){
        Object value = redisTemplate.opsForValue().get(key);
        return value;
    }

    /**
     * 添加键值
     * @param key 键
     * @param value 值
     * @return
     */
    @RequestMapping(value = "set")
    public boolean set(String key, String value){
        return redisUtil.set(key, value);
    }

    /**
     * 添加List对象数据到redis中
     * @return
     */
    @RequestMapping(value = "/setList")
    public boolean setList(){
        List<Object> stuList = new ArrayList<>();
        Stu stu = new Stu();
        stu.setId(1);
        stu.setAge(18);
        stu.setDept("软件部");
        stu.setName("张三");

        Stu stu1 = new Stu();
        stu1.setId(2);
        stu1.setAge(18);
        stu1.setDept("软件部");
        stu1.setName("王五");

        Stu stu2 = new Stu();
        stu2.setId(3);
        stu2.setAge(18);
        stu2.setDept("财务部");
        stu2.setName("李四");

        stuList.add(stu);
        stuList.add(stu1);
        stuList.add(stu2);
        return redisUtil.lSetList("stu", stuList);
    }

    /**
     * 获取全部数据
     * @return
     */
    @RequestMapping(value = "getList")
    public Object getList(){
        return redisUtil.lGet("stu", 0, -1);
    }

    /**
     * 根据key获取过期时间
     * @param key 键
     * @return
     */
    @GetMapping(value = "/getExpire")
    public long getExpire(String key){
        return redisUtil.getExpire(key);
    }

    /**
     * 根据key判断是否存在
     * @param key
     * @return
     */
    @GetMapping(value = "hasKey")
    public boolean hasKey(String key){
        return redisUtil.hasKey(key);
    }

    /**
     * 删除缓存
     * @param key
     */
    @GetMapping(value = "del")
    public void del(String ... key){
        redisUtil.del(key);
    }

    /**
     * 设置缓存和缓存时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    @GetMapping(value = "setTime")
    public boolean setTime(String key,String value,long time){
        return redisUtil.set(key,value,time);
    }

    /**
     * 递增
     * @param key
     * @param delta
     * @return
     */
    @GetMapping(value = "incr")
    public long incr(String key, long delta){
        return redisUtil.incr(key,delta);
    }

    /**
     * set值
     * @param key
     * @param item
     * @return
     */
    @GetMapping(value = "hmset")
    public Object hmset(String key){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name","zhangsan");
        map.put("age","12");
        return redisUtil.hmset(key, map);
    }

    /**
     * 获取hashKey对应的所有键值
     * @param key
     * @return
     */
    @GetMapping(value = "hmget")
    public Object hmget(String key){
        return redisUtil.hmget(key);
    }

    /**
     * 根据key和hashMap的key获取值
     * @param key
     * @param item
     * @return
     */
    @GetMapping(value = "hget")
    public Object hget(String key,String item){
        return redisUtil.hget(key,item);
    }
}
