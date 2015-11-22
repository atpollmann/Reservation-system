package cl.usach.ingesoft.agendator.business.dao.base;

import java.io.Serializable;
import java.util.List;

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
