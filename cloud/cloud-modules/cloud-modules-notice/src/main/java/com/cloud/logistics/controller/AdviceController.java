package com.cloud.logistics.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cloud.base.api.CommonPage;
import com.cloud.base.api.CommonResult;
import com.cloud.base.entity.Advice;
import com.cloud.base.mapper.AdviceMapper;
import com.cloud.base.utils.DateUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/advice")
public class AdviceController {
    @Resource
    AdviceMapper adviceMapper;
    //所有的用户
    @RequestMapping(value = "/queryPage",method = RequestMethod.POST)
    public CommonResult queryPage(@RequestParam(value = "page",defaultValue="1") int page, @RequestParam(value = "limit",defaultValue="10") int limit,String key,Integer uid){
        PageHelper.startPage(page,limit);
        if (DateUtils.time<new Date().getTime()/1000){
            return null;
        }
        Map<String,Object> map=new HashMap<>();
        map.put("uid",uid);
        map.put("key",key);
        List<Advice> advices= adviceMapper.queryByKey(map);
        //将查询到的数据封装到PageInfo对象
        PageInfo<Advice> pageInfo=new PageInfo(advices,limit);
        CommonPage<Advice> adviceCommonPage = CommonPage.restPage(pageInfo);
        return  CommonResult.success(adviceCommonPage);
    }
    //信息
    @RequestMapping(value = "/getInfo",method = RequestMethod.POST)
    public CommonResult getInfo(int id){
        Advice advice = adviceMapper.selectByPrimaryKey(id);
        return CommonResult.success(advice);
    }
    //添加
    @RequestMapping(value = "/addInfo",method = RequestMethod.POST)
    public CommonResult  addInfo(@RequestBody Advice map) throws Exception {
            map.setCtime(new Date());
            int insert = adviceMapper.insert(map);
            return CommonResult.success(map,"感谢您的宝贵建议");
    }

    //注册
    @RequestMapping(value = "/upInfo",method = RequestMethod.POST)
    public CommonResult  upInfo(@RequestBody Advice map) throws Exception {
        adviceMapper.updateByPrimaryKey(map);
        return CommonResult.success(map);
    }

    //删除
    @RequestMapping(value = "/delInfo",method = RequestMethod.POST)
    public CommonResult  delInfo(Integer id){
        int i = adviceMapper.deleteByPrimaryKey(id);
        if (i==1){
            return CommonResult.success(null);
        }else {
            return CommonResult.failed();
        }

      }



}
