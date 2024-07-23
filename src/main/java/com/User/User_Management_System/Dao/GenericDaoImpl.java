package com.User.User_Management_System.Dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;


public class GenericDaoImpl<T> implements GenericDao<T> {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	private Class<T> type;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }
	
	@Transactional
	public T create(T t)
	{
		hibernateTemplate.save(t);
		return t;
	}
	@Transactional
	public void delete(T t)
	{
		hibernateTemplate.delete(t);
	}
	@Transactional
	public T update(T t)
	{
		hibernateTemplate.merge(t);
		return t;
	}
	@Transactional
	public T find(Object id) {
        return (T)this.hibernateTemplate.get(type,(Integer)id);
    }
}
