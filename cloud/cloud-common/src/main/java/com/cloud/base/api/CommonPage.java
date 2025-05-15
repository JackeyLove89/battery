package com.cloud.base.api;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 通用分页数据封装类
 *
 * <p>该类用于封装分页查询的结果，提供统一的分页数据结构，便于在前后端交互中使用。</p>
 *
 * @param <T> 分页数据的类型
 * @author DING
 */
public class CommonPage<T> {
    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页显示的记录数
     */
    private Integer pageSize;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页的数据列表
     */
    private List<T> list;

    /**
     * 将 MyBatis Plus 分页结果转换为通用分页结果
     *
     * <p>该方法接收一个 {@link PageInfo} 对象，提取其中的分页信息并赋值给 {@code CommonPage} 的属性。</p>
     *
     * @param pageResult MyBatis Plus 分页结果
     * @param <T> 分页数据的类型
     * @return 封装后的通用分页对象
     */
    public static <T> CommonPage<T> restPage(PageInfo<T> pageResult) {
        CommonPage<T> result = new CommonPage<>();
        result.setPageNum(pageResult.getPageNum());
        result.setPageSize(pageResult.getSize());
        result.setTotal(pageResult.getTotal());
        result.setList(pageResult.getList());
        return result;
    }

    /**
     * 获取当前页码
     *
     * @return 当前页码
     */
    public Integer getPageNum() {
        return pageNum;
    }

    /**
     * 设置当前页码
     *
     * @param pageNum 当前页码
     */
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * 获取每页显示的记录数
     *
     * @return 每页显示的记录数
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页显示的记录数
     *
     * @param pageSize 每页显示的记录数
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取当前页的数据列表
     *
     * @return 当前页的数据列表
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 设置当前页的数据列表
     *
     * @param list 当前页的数据列表
     */
    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * 获取总记录数
     *
     * @return 总记录数
     */
    public Long getTotal() {
        return total;
    }

    /**
     * 设置总记录数
     *
     * @param total 总记录数
     */
    public void setTotal(Long total) {
        this.total = total;
    }
}