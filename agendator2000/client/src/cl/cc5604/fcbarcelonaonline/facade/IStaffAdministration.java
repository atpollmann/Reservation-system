package cl.cc5604.fcbarcelonaonline.facade;

import cl.cc5604.fcbarcelonaonline.entity.NationalityEntity;
import cl.cc5604.fcbarcelonaonline.entity.StaffEntity;
import cl.cc5604.fcbarcelonaonline.entity.StaffTypeEntity;

import java.util.List;

public interface IStaffAdministration {

    void createStaff(StaffEntity staff);

    List<StaffEntity> findAllStaffs();

    StaffEntity findById(int idStaff);

    void updateStaff(StaffEntity staff);

    void deleteStaff(int idStaff);

    // staff types

    List<StaffTypeEntity> findAllStaffTypes();

    List<NationalityEntity> findAllNationalities();

    NationalityEntity findNationalityById(int idNationality);

    StaffTypeEntity findStaffTypeById(int idStaffType);

    //double getMarketValue(StaffEntity staff);
}
