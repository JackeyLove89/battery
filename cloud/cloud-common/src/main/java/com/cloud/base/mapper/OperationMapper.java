package com.cloud.base.mapper;

import com.cloud.base.entity.Operation;

import java.util.List;

public interface OperationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operation
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operation
     *
     * @mbg.generated
     */
    int insert(Operation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operation
     *
     * @mbg.generated
     */
    Operation selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operation
     *
     * @mbg.generated
     */
    List<Operation> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operation
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Operation record);
}