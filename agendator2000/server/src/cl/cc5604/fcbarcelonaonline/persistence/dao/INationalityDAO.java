package cl.cc5604.fcbarcelonaonline.persistence.dao;

import cl.cc5604.fcbarcelonaonline.entity.NationalityEntity;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 26-04-13
 * Time: 11:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface INationalityDAO extends IBaseDAO<NationalityEntity, Integer> {

    public NationalityEntity findByName(String name);

}
