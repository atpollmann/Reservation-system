package cl.usach.ingesoft.agendator.business.dao.base;

import cl.usach.ingesoft.agendator.entity.base.SequenceEntity;
import org.springframework.stereotype.Repository;

@Repository
public class SequenceDao extends BaseDao<SequenceEntity, Integer> implements ISequenceDao {
    public SequenceDao() {
        super(SequenceEntity.class);
    }

    @Override
    public SequenceEntity generate() {
        SequenceEntity se = new SequenceEntity();
        save(se);
        flush();
        return se;
    }
}
