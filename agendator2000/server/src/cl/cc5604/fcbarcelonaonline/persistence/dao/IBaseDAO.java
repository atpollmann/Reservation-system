package cl.cc5604.fcbarcelonaonline.persistence.dao;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 26-04-13
 * Time: 11:57 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IBaseDAO<T, S extends Serializable> {

    List<T> findAll();

    T findById(S pk);

    void save(T object);

    void update(T object);

    void delete(T object);

    List<T> findByStatement(String statement, Object... paramPairs);

    T findOneByStatement(String statement, Object... paramPairs);

    void deleteAll();

    void flush();
}
