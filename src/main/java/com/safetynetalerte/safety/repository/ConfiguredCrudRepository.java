package com.safetynetalerte.safety.repository;


import java.util.List;

public interface ConfiguredCrudRepository<T> {
	
	List<T> getAll();
	
	T save(T t);
	
	T findBy(String key);
	
	void deleteBy(T t);
}
