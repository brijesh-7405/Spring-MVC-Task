package com.User.User_Management_System.Dao;

public interface GenericDao<T> {
	
	 	T create(T t);

	    void delete(T t);

	    T find(Object id);

	    T update(T t);
}
