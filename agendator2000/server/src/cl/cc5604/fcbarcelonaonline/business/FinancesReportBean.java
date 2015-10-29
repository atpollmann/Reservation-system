package cl.cc5604.fcbarcelonaonline.business;

import cl.cc5604.fcbarcelonaonline.facade.IFinancesReport;
import cl.cc5604.fcbarcelonaonline.persistence.dao.impl.FinancesDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class FinancesReportBean implements IFinancesReport {

    @Resource private FinancesDAO financesDAO;

    // --------------------------------

    @Override
    public int getTotalActives() {
        return financesDAO.getTotalActives();
    }

    @Override
    public int getTotalPassives() {
        return financesDAO.getTotalPassives();
    }

    @Override
    public int getTotalNet() {
        return financesDAO.getTotalActives() - financesDAO.getTotalPassives();
    }

    @Override
    @Transactional
    public int getTotalActives2() {
        return financesDAO.getTotalActives2();
    }
}
