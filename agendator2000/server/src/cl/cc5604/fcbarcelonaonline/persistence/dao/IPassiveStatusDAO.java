package cl.cc5604.fcbarcelonaonline.persistence.dao;

import cl.cc5604.fcbarcelonaonline.entity.PassiveStatusEntity;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 26-04-13
 * Time: 11:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IPassiveStatusDAO extends IBaseDAO<PassiveStatusEntity, Integer> {

    public PassiveStatusEntity findByStatus(String status);
}
