package com.safetynetalerte.safety.repository;


import java.util.List;

/**
 * The interface to configure the Crud Repository who should be used.
 *
 * @param <T> The object. Class.
 */
public interface ConfiguredCrudRepository<T> {

    List<T> getAll();

    //T save(T t);
    T save(T t);

    T findById(String key0, String key1);

    void deleteBy(String key0, String key1);

    T updateBy(T t, String key0, String key1);
}
