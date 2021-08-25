package resume.builder.doc.core.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import resume.builder.doc.core.dao.custom.SmartSequenceDaoCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.Date;

@Repository
public class SmartSequenceDaoImpl implements SmartSequenceDaoCustom {
    private static final Logger logger = LoggerFactory.getLogger(SmartSequenceDaoImpl.class);

    private static final class Sql{
        static final String ADD_NEW_SMART_SEQUENCE = "insert into smart_sequence (prefix, for_date) values (:prefix, :forDate)";

        static final String GET_LAST_INSERT_SMART_SEQUENCE_ID = "select last_insert_id()";

        static final String GET_SEQUENCE_BY_SMART_SEQUENCE_ID = "select sequence from smart_sequence where smart_sequence_id = :smartSequenceId";
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Integer getSequence(String prefix, Date forDate) {
        // TRIGGER `smart_sequence_BEFORE_INSERT` will update the sequence

        entityManager.createNativeQuery(Sql.ADD_NEW_SMART_SEQUENCE)
                .setParameter("prefix", prefix)
                .setParameter("forDate", forDate)
                .executeUpdate();

        final BigInteger smartSequenceId = (BigInteger) entityManager.createNativeQuery(Sql.GET_LAST_INSERT_SMART_SEQUENCE_ID)
                .getSingleResult();

        return (Integer) entityManager.createNativeQuery(Sql.GET_SEQUENCE_BY_SMART_SEQUENCE_ID)
                .setParameter("smartSequenceId", smartSequenceId)
                .getSingleResult();
    }
}
