package cl.cc5604.fcbarcelonaonline.persistence.dao;

import cl.cc5604.fcbarcelonaonline.entity.StaffEntity;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 26-04-13
 * Time: 11:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IStaffDAO extends IBaseDAO<StaffEntity, Integer> {

    public StaffEntity findByContract(int idContract);

}
