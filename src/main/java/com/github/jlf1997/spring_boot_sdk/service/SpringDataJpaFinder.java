package com.github.jlf1997.spring_boot_sdk.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * 自定义查询条件
 * @author Administrator
 *
 * @param <T>
 */
public interface SpringDataJpaFinder<T> {

	public void where(List<Predicate>  predicates,Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb,T...t);
}
