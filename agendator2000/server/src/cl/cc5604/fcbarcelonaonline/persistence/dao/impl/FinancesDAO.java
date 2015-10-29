package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.persistence.dao.IFinancesDAO;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 12-05-13
 * Time: 02:55 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class FinancesDAO extends BaseDAO<Void, Integer> implements IFinancesDAO {

    public FinancesDAO() {
        super(Void.class);
    }

    private int doQuery(String hql) {
        Query query = session().createQuery(hql);
        Object o = query.uniqueResult();
        // si no hay registros, uniqueResult retorna null
        if(o == null){
            return 0;
        }
        return (int)Double.parseDouble(o.toString()); // castear resultado de cualquier tipo, a int
    }

    @Override
    public int getTotalPassives() {
        String hql = "select sum(pe.value) from PassiveEntity pe where pe.passiveStatus.status='Pendiente'";
        return doQuery(hql);
    }

    @Override
    public int getTotalActives() {
        String hql1 = "select floor(1.0*sum(a.baseValue)) as total from StaffEntity a join a.staffType st where st.type = 'arquero'";
        String hql2 = "select floor(1.2*sum(a.baseValue)) as total from StaffEntity a join a.staffType st where st.type = 'defensa'";
        String hql3 = "select floor(1.5*sum(a.baseValue)) as total from StaffEntity a join a.staffType st where st.type = 'mediocampista'";
        String hql4 = "select floor(2.0*sum(a.baseValue)) as total from StaffEntity a join a.staffType st where st.type = 'delantero'";
        String hql5 = "select floor(sum(a.value)) as total from ActiveEntity a";
        // TODO: iterar sobre el map de coeficientes en StaffAdministrationBean y construir queries dinamicamente
        return doQuery(hql1) + doQuery(hql2) + doQuery(hql3) + doQuery(hql4) + doQuery(hql5);
    }

    @Override
    public int getTotalActives2() {
        String hql = "select sum(a.baseValue*" +
            "case " +
            "   when st.type='Arquero'       then 1.0 " +
            "   when st.type='Defensa'       then 1.2 " +
            "   when st.type='Mediocampista' then 1.5 " +
            "   when st.type='Delantero'     then 2.0 " +
            "   else 0.0 " +
            "end" +
        ") as total " +
        "from StaffEntity a join a.staffType st";
        String hql2 = "select floor(sum(a.value)) as total from ActiveEntity a";
        return doQuery(hql)+doQuery(hql2);
    }

}
