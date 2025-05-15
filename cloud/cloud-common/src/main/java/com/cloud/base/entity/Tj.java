package com.cloud.base.entity;

import java.util.List;

public class Tj {
   public int  manNum;//男
   public int  womanNum;//女
   public int  otherNum;//其他人数

    public int  userNum;//总人数
    public int  loginNum;//今日活跃人数
    public int  todayAddNum;//今日新增


    public List<SevenTj>  sevenTjs;//七日统计
    public static  class SevenTj{
        public int  loginNum;//今日活跃人数
        public int  todayAddNum;//今日新增
        public String  day;//日期
    }

}
