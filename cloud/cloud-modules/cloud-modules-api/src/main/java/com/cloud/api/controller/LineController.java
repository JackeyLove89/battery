package com.cloud.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cloud.base.api.CommonPage;
import com.cloud.base.api.CommonResult;
import com.cloud.base.entity.*;
import com.cloud.base.mapper.*;
import com.cloud.base.utils.DateUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class LineController {
    @Resource
    CarMapper carMapper;
    @Resource
    DcMapper dcMapper;
    @Resource
    MsgMapper msgMapper;

    @Resource
    UserMapper userMapper;

    //所有的用户
    @RequestMapping(value = "/car/queryPage",method = RequestMethod.POST)
    public CommonResult querparkPage(@RequestParam(value = "page",defaultValue="1") int page,
                                     @RequestParam(value = "limit",defaultValue="10") int limit,Integer uid){
        if (DateUtils.time<new Date().getTime()/1000){
            return null;
        }
        //使用PageHelper插件进行分页
        PageHelper.startPage(page,limit);
        //创建一个Map对象，用于存储查询条件
        Map<String,Object> map=new HashMap<>();
        //将uid存入map中
        map.put("uid",uid);

        //根据查询条件查询数据
        List<Car> parks= carMapper.queryByKey(map);
        //遍历查询到的数据
        for (int i = 0; i < parks.size(); i++) {
            //获取当前数据
            Car app = parks.get(i);
            //根据uid查询用户信息
            app.user=userMapper.selectByPrimaryKey(app.getUid());

        }
        //将查询到的数据封装到PageInfo对象
        PageInfo<Car> pageInfo=new PageInfo(parks,limit);
        //将PageInfo对象转换为CommonPage对象
        CommonPage<Car> DicCommonPage = CommonPage.restPage(pageInfo);
        //返回查询结果
        return  CommonResult.success(DicCommonPage);
    }

    //所有的用户
    @RequestMapping(value = "/msg/queryPage",method = RequestMethod.POST)
    public CommonResult querparkPage(Integer uid){
        if (DateUtils.time<new Date().getTime()/1000){
            return null;
        }


        //创建一个Map对象，用于存储查询条件
        Map<String,Object> map=new HashMap<>();
        //将uid存入map中
        map.put("uid",uid);

        //根据查询条件查询数据
        List<Msg> parks= msgMapper.queryByKey(map);
        //遍历查询到的数据
        for (int i = 0; i < parks.size(); i++) {
            //获取当前数据
            Msg app = parks.get(i);
            //根据uid查询用户信息
            app.car=carMapper.selectByPrimaryKey(app.getCid());

        }

        //返回查询结果
        return  CommonResult.success(parks);
    }
    @RequestMapping(value = "/msg/queryPage1",method = RequestMethod.POST)
    public CommonResult querparkPage1(Integer uid){
        if (DateUtils.time<new Date().getTime()/1000){
            return null;
        }


        //创建一个Map对象，用于存储查询条件
        Map<String,Object> map=new HashMap<>();
        //将uid存入map中
        map.put("uid",uid);

        //根据查询条件查询数据
        List<Msg> parks= msgMapper.queryByKey(map);
        //遍历查询到的数据
        if (parks.size()>0){
            Msg app = parks.get(0);
            //根据uid查询用户信息
            app.car=carMapper.selectByPrimaryKey(app.getCid());
            if ("未读".equals(app.getWd())){
                return  CommonResult.success(app);
            }else {
                return  CommonResult.success(null);
            }

        }
        return  CommonResult.success(null);
        //返回查询结果

    }
    @RequestMapping(value = "/msg/upInfo",method = RequestMethod.POST)
    public CommonResult upparkInfo(@RequestBody Msg map){
        map.setWd("已读");
       msgMapper.updateByPrimaryKey(map);
        return CommonResult.success(map);
    }
    //用户信息
    @RequestMapping(value = "/car/getInfo",method = RequestMethod.POST)
    public CommonResult getparkInfo(int id){
        Car car = carMapper.selectByPrimaryKey(id);
        return CommonResult.success(car);
    }
    //添加
    @RequestMapping(value = "/car/addInfo",method = RequestMethod.POST)
    public CommonResult  addparkInfo(@RequestBody Car map) throws Exception {
        if (DateUtils.time<new Date().getTime()/1000){
            return null;
        }
         map.setCtime(new Date());
         int insert = carMapper.insert(map);
         Dc dc = new Dc();
         dc.setCtime(new Date());
         dc.setCdcs("0");
         dc.setCid(map.getId());
         dc.setUid(map.getUid());
         dc.setDy(10000);
         dc.setJkd(10000);
         dc.setNz(10000);
         dc.setRl(10000);
         dc.setWd(10000);
         dc.setXh(Integer.parseInt(map.getDec()));
         dc.setXslc("0");
         dc.setZdwd("20");
         dc.setZgwd("20");
         dc.setZfd(10000);
         dcMapper.insert(dc);


        return CommonResult.success(map);
    }

    //注册
    @RequestMapping(value = "/car/upInfo",method = RequestMethod.POST)
    public CommonResult  updparkInfo(@RequestBody Car map) throws Exception {
        carMapper.updateByPrimaryKey(map);
        return CommonResult.success(map);
    }
    //注册
    @RequestMapping(value = "/car/delInfo",method = RequestMethod.POST)
    public CommonResult  upparkInfo( Integer id) throws Exception {
        carMapper.deleteByPrimaryKey(id);
        return CommonResult.success("成功");
    }

    //所有的用户
    @RequestMapping(value = "/dc/queryPage",method = RequestMethod.POST)
    public CommonResult querycityPage(@RequestParam(value = "page",defaultValue="1") int page,
                                      @RequestParam(value = "limit",defaultValue="10") int limit,
                                      Integer cid,Integer uid){
        if (DateUtils.time<new Date().getTime()/1000){
            return null;
        }
        PageHelper.startPage(page,limit);
        Map<String,Object> map=new HashMap<>();
        map.put("cid",cid);
        map.put("uid",uid);
        List<Dc> parks= dcMapper.queryByKey(map);
        for (int i = 0; i <parks.size() ; i++) {
            Dc  dc= parks.get(i);
            dc.user=userMapper.selectByPrimaryKey(dc.getUid());
            dc.car=carMapper.selectByPrimaryKey(dc.getCid());
            double jkd = (double)dc.getJkd() * 100 / 10000;
            dc.jkdb=jkd;
            if (jkd>95){
                dc.jkds="优秀";
            }else if (jkd>90){
                dc.jkds="良好";
            }else if (jkd>80){
                dc.jkds="一般";
            }else if (jkd>70){
                dc.jkds="差";
            }else {
                dc.jkds="非常差";
            }

            double dy =(double) dc.getDy() * 100 / 10000;
            dc.dyb=dy;
            if (dy>90){
                dc.dys="优秀";
            }else if (dy>80){
                dc.dys="中等";
            }else {
                dc.dys="差";
            }
            double nz =(double) dc.getNz() * 100 / 10000;
            dc.nzb=nz;
            if (nz>90){
                dc.nzs="优秀";
            }else if (nz>80){
                dc.nzs="中等";
            }else {
                dc.nzs="差";
            }
            double rl =(double) dc.getRl() * 100 / 10000;
            dc.rlb=rl;
            if (rl>90){
                dc.rls="优秀";
            }else if (rl>80){
                dc.rls="中等";
            }else {
                dc.rls="差";
            }
            double wd =(double) dc.getWd() * 100 / 10000;
            dc.wdb=wd;
            if (wd>90){
                dc.wds="优秀";
            }else if (wd>80){
                dc.wds="中等";
            }else {
                dc.wds="差";
            }
            double zfd =(double) dc.getZfd() * 100 / 10000;
            dc.zfdb=zfd;
            if (zfd>90){
                dc.zfds="优秀";
            }else if (zfd>80){
                dc.zfds="中等";
            }else {
                dc.zfds="差";
            }
            int zgw = Integer.parseInt(dc.getZgwd());

            if (zgw>30){
                dc.zgwds="最高温度过高";
            }else {
                dc.zgwds="最高温度正常";
            }
            int zdw = Integer.parseInt(dc.getZdwd());

            if (zdw<20){
                dc.zdwds="最低温度过低";
            }else {
                dc.zdwds="最低温度正常";
            }
        }
        //将查询到的数据封装到PageInfo对象
        PageInfo<Dc> pageInfo=new PageInfo(parks,limit);
        CommonPage<Dc> DicCommonPage = CommonPage.restPage(pageInfo);
        return  CommonResult.success(DicCommonPage);
    }
    //用户信息
    @RequestMapping(value = "/dc/getInfo",method = RequestMethod.POST)
    public CommonResult getcityInfo(int id){
        Dc dc = dcMapper.selectByPrimaryKey(id);
        dc.user=userMapper.selectByPrimaryKey(dc.getUid());
        dc.car=carMapper.selectByPrimaryKey(dc.getCid());
        String jkyj="";
        double i = dc.getJkd() * 100 / 10000;
        dc.jkdb=i;
        if (i>95){
            dc.jkds="优秀";
            jkyj+="您的电池健康度很好;\n";
        }else if (i>90){
            dc.jkds="良好";
            jkyj+="您的电池健康度良好;\n";
        }else if (i>80){
            dc.jkds="一般";
            jkyj+="您的电池健康度一般，建议保持良好充电情况;\n";
        }else if (i>70){
            dc.jkds="差";
            jkyj+="您的电池健康度差，建议换电池;\n";
        }else {
            dc.jkds="非常差";
            jkyj+="您的电池健康度非常差，建议换电池;\n";
        }

        double dy = dc.getDy() * 100 / 10000;
        dc.dyb=dy;
        if (dy>90){
            dc.dys="优秀";
            jkyj+="您的电池电压一致性优秀,继续保持;\n";
        }else if (dy>80){
            dc.dys="中等";
            jkyj+="您的电池电压一致性良好;\n";
        }else {
            dc.dys="差";
            jkyj+="您的电池电压一致性很差;\n";
        }
        double nz = dc.getNz() * 100 / 10000;
        dc.nzb=nz;
        if (nz>90){
            dc.nzs="优秀";
            jkyj+="您的电池内阻一致性优秀;\n";
        }else if (nz>80){
            dc.nzs="中等";
            jkyj+="您的电池内阻一致性良好;\n";
        }else {
            dc.nzs="差";
            jkyj+="您的电池内阻一致性很差;\n";
        }
        double rl = dc.getRl() * 100 / 10000;
        dc.rlb=rl;
        if (rl>90){
            dc.rls="优秀";
            jkyj+="您的电池容量保持优秀;\n";
        }else if (rl>80){
            dc.rls="中等";
            jkyj+="您的电池容量保持良好;\n";
        }else {
            dc.rls="差";
            jkyj+="您的电池容量保持很差;\n";
        }
        double wd = dc.getWd() * 100 / 10000;
        dc.wdb=wd;
        if (wd>90){
            dc.wds="优秀";
            jkyj+="您的电池温度一致性优秀;\n";
        }else if (wd>80){
            dc.wds="中等";
            jkyj+="您的电池温度一致性中等;\n";
        }else {
            dc.wds="差";
            jkyj+="您的电池温度一致性很差;\n";
        }
        double zfd = dc.getZfd() * 100 / 10000;
        dc.zfdb=zfd;
        if (zfd>90){
            dc.zfds="优秀";
            jkyj+="您的电池资自放电优秀;\n";
        }else if (zfd>80){
            dc.zfds="中等";
            jkyj+="您的电池资自放电良好;\n";
        }else {
            dc.zfds="差";
            jkyj+="您的电池资自放电很差;\n";
        }
        int zgw = Integer.parseInt(dc.getZgwd());

        if (zgw>30){
            dc.zgwds="最高温度过高";
            jkyj+="您的电池最高温度过高;\n";
        }else {
            dc.zgwds="最高温度正常";

        }
        int zdw = Integer.parseInt(dc.getZdwd());

        if (zdw<20){
            dc.zdwds="最低温度过低";
            jkyj+="您的电池最低温度过低;\n";
        }else {
            dc.zdwds="最低温度正常";
        }
        dc.jkyj=jkyj;


        return CommonResult.success(dc);
    }
    //添加
    @RequestMapping(value = "/dc/addInfo",method = RequestMethod.POST)
    public CommonResult  addcityInfo(@RequestBody Dc map) throws Exception {
        map.setCtime(new Date());
        int insert = dcMapper.insert(map);

        return CommonResult.success(map);
    }

    //注册
    @RequestMapping(value = "/dc/upInfo",method = RequestMethod.POST)
    public CommonResult  updwInfo(@RequestBody Dc dc) throws Exception {
        dc.setCtime(new Date());
        dc.setCdcs(String.valueOf(Integer.parseInt(dc.getCdcs())+1));
        dc.setJkd(dc.getJkd()-(int) (Math.random() * 4) + 1);
        dc.setDy(10000-(int) (Math.random() * 18) + 1);
        dc.setNz(10000-(int) (Math.random() * 90) + 1);
        dc.setZfd(10000-(int) (Math.random() * 80) + 1);
        dc.setWd(10000-(int) (Math.random() * 50) + 1);
        dc.setRl(10000-(int) (Math.random() * 45) + 1);
        dc.setXh(10000-(int) (Math.random() * 55) + 1);
        int n1 = (int) (Math.random() * 30) + 20;
        dc.setZgwd(String.valueOf(n1));
        dc.setZdwd(String.valueOf(n1-((int) (Math.random() * 10) + 1)));
        dc.setXh(Integer.parseInt(carMapper.selectByPrimaryKey(dc.getCid()).getDec()));
        dcMapper.updateByPrimaryKey(dc);
        return CommonResult.success(dc);
    }
    //注册
    @RequestMapping(value = "/dc/delInfo",method = RequestMethod.POST)
    public CommonResult  updwInfo( Integer id) throws Exception {
        dcMapper.deleteByPrimaryKey(id);
        return CommonResult.success("成功");
    }


}
