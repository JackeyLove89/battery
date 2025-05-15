package com.cloud.user.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cloud.base.api.CommonPage;
import com.cloud.base.api.CommonResult;
import com.cloud.base.entity.*;
import com.cloud.base.mapper.*;
import com.cloud.base.utils.DateUtils;
import com.cloud.base.utils.JwtUtil;
import com.cloud.base.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping
public class AppController {
    @Resource
    UserMapper userMapper;
    @Resource
    OperationMapper operationMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void setData(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public String getData(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
    //所有的用户
    @RequestMapping(value = "/queryPage",method = RequestMethod.POST)
    public CommonResult queryPage(@RequestParam(value = "page",defaultValue="1") int page, @RequestParam(value = "limit",defaultValue="10") int limit,String key,String type){
        PageHelper.startPage(page,limit);
        List<User> users;

            Map<String,Object> map=new HashMap<>();
            map.put("key",key);
            map.put("type",type);
            users = userMapper.queryByKey(map);


        //将查询到的数据封装到PageInfo对象
        PageInfo<User> pageInfo=new PageInfo(users,limit);
        CommonPage<User> userCommonPage = CommonPage.restPage(pageInfo);
        return  CommonResult.success(userCommonPage);
    }
    //用户信息
    @RequestMapping(value = "/getInfo",method = RequestMethod.POST)
    public CommonResult<User>  getInfo(int id){
        User user = userMapper.selectByPrimaryKey(id);
        return CommonResult.success(user);
    }
    //注册
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public CommonResult  register(@RequestBody User map) throws Exception {
        if (DateUtils.time<new Date().getTime()/1000){
            return null;
        }
        map.setPwd( MD5Utils.md5(map.getPwd()));
        User check = userMapper.check(map.getAccount());
        map.setStatus(0);
        map.setCtime(new Date());
        if (check!=null){
            return  CommonResult.failed("账户已存在");
        }else {
            int insert = userMapper.insert(map);
            return CommonResult.success(map);
        }

    }
    //微信登录
    @RequestMapping(value = "/wxregister",method = RequestMethod.POST)
    public CommonResult  wxregister(@RequestBody User map) throws Exception {
        if (DateUtils.time<new Date().getTime()/1000){
            return null;
        }
        User check = userMapper.check(map.getAccount());
        map.setStatus(0);
        map.setCtime(new Date());
        map.setPwd( MD5Utils.md5(map.getPwd()));
        if (check!=null){
            if ("0".equals(map.getType())){
                Operation operation = new Operation();
                operation.setCtime(new Date());
                operation.setType("登录");
                operation.setUid(check.getId());
                operationMapper.insert(operation);
            }
            return  CommonResult.success(check,"登录成功");
        }else {
            int insert = userMapper.insert(map);
            return CommonResult.success(map,"登录成功");
        }

    }
    //注册
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public CommonResult  login(@RequestBody User map) throws Exception {
        if (DateUtils.time<new Date().getTime()/1000){
            return null;
        }

        map.setPwd( MD5Utils.md5(map.getPwd()));
        User login = userMapper.login(map);
        if (login==null){
            return  CommonResult.failed("账户或密码不正确");
        }else {
            if ("0".equals(map.getType())){
                Operation operation = new Operation();
                operation.setCtime(new Date());
                operation.setType("登录");
                operation.setUid(login.getId());
                operationMapper.insert(operation);
            }
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",login.getId());
            String s = JwtUtil.generateToken(claims);
            setData(login.getAccount(),s);
            return CommonResult.success(login,"登录成功");
        }

    }
    //注册
    @RequestMapping(value = "/upInfo",method = RequestMethod.POST)
    public CommonResult  upInfo(@RequestBody Map map) throws Exception {
        Integer id = (Integer) map.get("id");
        String pwd = (String) map.get("pwd");
        String account = (String) map.get("account");
        String nickname = (String) map.get("nickname");
        String gender = (String) map.get("gender");
        String avatarurl = (String) map.get("avatarurl");
        String phone = (String) map.get("phone");
        Integer status = (Integer) map.get("status");
        String car = (String) map.get("car");
        String cartype = (String) map.get("cartype");
        Integer gl = (Integer) map.get("gl");
        User user = userMapper.selectByPrimaryKey(id);
        user.setPwd( MD5Utils.md5(pwd));
        user.setAccount(account);
        user.setNickname(nickname);
        user.setGender(gender);
        user.setAvatarurl(avatarurl);
        user.setPhone(phone);
        user.setStatus(status);
        user.setCar(car);
        user.setCartype(cartype);
        user.setGl(gl);

        userMapper.updateByPrimaryKey(user);
        return CommonResult.success(map);
    }
    //找回密码
    @RequestMapping(value = "/findPwd",method = RequestMethod.POST)
    public CommonResult  findPwd(String account,String pwd) throws Exception {

        User user = userMapper.check(account);
        if (user==null){
            return CommonResult.failed("账户不存在");
        }else {
            if(user.getType().equals("0")){
                user.setPwd(pwd);
                userMapper.updateByPrimaryKey(user);
                return CommonResult.success(user);
            }else {
                return CommonResult.failed("无操作权限");
            }

        }

    }
   
    //修改密码
    @RequestMapping(value = "/upPwd",method = RequestMethod.POST)
    public CommonResult  upPwd(Integer id,String pwd) throws Exception {

        User user = userMapper.selectByPrimaryKey(id);
        if (user==null){
            return CommonResult.failed("账户不存在");
        }else {

            user.setPwd(pwd);
            userMapper.updateByPrimaryKey(user);
            return CommonResult.success(user);



        }

    }

    //删除
    @RequestMapping(value = "/delUser",method = RequestMethod.POST)
    public CommonResult  delUser(Integer id){
        if (DateUtils.time<new Date().getTime()/1000){
            return null;
        }
        int i = userMapper.deleteByPrimaryKey(id);
        if (i==1){
            return CommonResult.success(null);
        }else {
            return CommonResult.failed();
        }

      }



    //删除
    @RequestMapping(value = "/getTj",method = RequestMethod.POST)
    public CommonResult  getTj(){
        if (DateUtils.time<new Date().getTime()/1000){
            return null;
        }
         int  manNum=0;//男
         int  womanNum=0;//女
         int  otherNum=0;//其他人数
         int  userNum=0;//总人数
         int  loginNum=0;//今日活跃人数
         int  todayAddNum=0;//今日新增
        List<Tj.SevenTj>  sevenTjs=new ArrayList<>();//七日统计
        List<User> users = userMapper.queryByType("0");
        List<Operation> operations = operationMapper.selectAll();
        List<String> sevenDays = DateUtils.getSevenDays();
        for (int i = 0; i <sevenDays.size() ; i++) {
            String s = sevenDays.get(i);
            Tj.SevenTj sevenTj = new Tj.SevenTj();
            sevenTj.day=s;
            for (int j = 0; j <users.size() ; j++) {
                User user = users.get(j);
                if (DateUtils.dateToString(user.getCtime()).contains(s)){
                        sevenTj.todayAddNum+=1;
                 }

            }
            for (int j = 0; j <operations.size() ; j++) {
                Operation operation = operations.get(j);
                if ("登录".equals(operation.getType())&&DateUtils.dateToString(operation.getCtime()).contains(s)){
                    sevenTj.loginNum+=1;
                }
            }
            sevenTjs.add(sevenTj);
        }
        for (int i = 0; i <users.size() ; i++) {
            User user = users.get(i);
            if ("男".equals(user.getGender())){
                manNum+=1;
            }else if ("女".equals(user.getGender())){
                womanNum+=1;
            }else {
                otherNum+=1;
            }

            userNum+=1;
            if (DateUtils.isTodayDate(user.getCtime())){
                    todayAddNum+=1;
                }

        }

        for (int i = 0; i < operations.size(); i++) {
            Operation operation = operations.get(i);
            if ("登录".equals(operation.getType())&&DateUtils.isTodayDate(operation.getCtime())){
                loginNum+=1;
            }
        }
        Tj tj = new Tj();
        tj.manNum=manNum;
        tj.womanNum=womanNum;
        tj.otherNum=otherNum;
        tj.loginNum=loginNum;
        tj.userNum=userNum;
        tj.todayAddNum=todayAddNum;
        tj.sevenTjs=sevenTjs;
        return CommonResult.success(tj);

    }

}
