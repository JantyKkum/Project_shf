package com.janty.service;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.Map;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/12 13:59
 * @description:
 */
public interface BaseService<T> {

    Integer insert(T t);

    void delete(Long id);

    Integer update(T t);

    T getById(Serializable id);

    PageInfo<T> findPage(Map<String, Object> filters);
}