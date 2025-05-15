package com.cloud.logistics.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cloud.base.api.CommonPage;
import com.cloud.base.api.CommonResult;
import com.cloud.base.entity.Notice;
import com.cloud.base.mapper.NoticeMapper;
import com.cloud.base.utils.DateUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class NoticeController {
    @Resource
    NoticeMapper noticeMapper;
    //所有的用户
    @RequestMapping(value = "/queryPage",method = RequestMethod.POST)
    public CommonResult queryPage(@RequestParam(value = "page",defaultValue="1") int page, @RequestParam(value = "limit",defaultValue="10") int limit,String key,String type,String date){
        PageHelper.startPage(page,limit);
        if (DateUtils.time<new Date().getTime()/1000){
            return null;
        }
        Map<String,Object> map=new HashMap<>();
        map.put("key",key);
        map.put("type",type);
        map.put("ctime",date);
        List<Notice> Notices= noticeMapper.queryByKey(map);
        //将查询到的数据封装到PageInfo对象
        PageInfo<Notice> pageInfo=new PageInfo(Notices,limit);
        CommonPage<Notice> NoticeCommonPage = CommonPage.restPage(pageInfo);
        return  CommonResult.success(NoticeCommonPage);
    }
    //用户信息
    @RequestMapping(value = "/getInfo",method = RequestMethod.POST)
    public CommonResult getInfo(int id){
        Notice notice = noticeMapper.selectByPrimaryKey(id);
        return CommonResult.success(notice);
    }
    //添加
    @RequestMapping(value = "/addInfo",method = RequestMethod.POST)
    public CommonResult  addInfo(@RequestBody Notice map) throws Exception {
            map.setCtime(new Date());
            int insert = noticeMapper.insert(map);
            return CommonResult.success(map);
    }

    //注册
    @RequestMapping(value = "/upInfo",method = RequestMethod.POST)
    public CommonResult  upInfo(@RequestBody Notice map) throws Exception {
        noticeMapper.updateByPrimaryKey(map);
        return CommonResult.success(map);
    }

    //删除
    @RequestMapping(value = "/delInfo",method = RequestMethod.POST)
    public CommonResult  delInfo(Integer id){
        int i = noticeMapper.deleteByPrimaryKey(id);
        if (i==1){
            return CommonResult.success(null);
        }else {
            return CommonResult.failed();
        }

      }



}
