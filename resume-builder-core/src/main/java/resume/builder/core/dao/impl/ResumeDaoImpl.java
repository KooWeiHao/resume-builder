package resume.builder.core.dao.impl;

import com.querydsl.sql.ColumnMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import resume.builder.api.entity.QResumeBean;
import resume.builder.api.entity.ResumeBean;
import resume.builder.api.model.ResumeSearchModel;
import resume.builder.api.model.ResumeSearchResultModel;
import resume.builder.core.dao.custom.ResumeDaoCustom;
import resume.builder.util.DateUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ResumeDaoImpl implements ResumeDaoCustom {
    private static final Logger logger = LoggerFactory.getLogger(ResumeDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResumeSearchResultModel find(ResumeSearchModel resumeSearchModel) {
        final int pageNumber = resumeSearchModel.getPageNumber() == 0 ? 1 : resumeSearchModel.getPageNumber();
        final int pageSize = resumeSearchModel.getPageSize() == 0 ? 10 : resumeSearchModel.getPageSize();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ResumeBean> criteriaQuery = criteriaBuilder.createQuery(ResumeBean.class);
        Root<ResumeBean> resume = criteriaQuery.from(ResumeBean.class);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        List<Predicate> predicates = new ArrayList<>();

        //Conditions
        Optional.ofNullable(resumeSearchModel.getCode())
                .ifPresent(code -> predicates.add(criteriaBuilder.equal(resume.get(ColumnMetadata.getName(QResumeBean.resumeBean.code)), code)));
        Optional.ofNullable(resumeSearchModel.getName())
                .ifPresent(name -> predicates.add(criteriaBuilder.like(resume.get(ColumnMetadata.getName(QResumeBean.resumeBean.name)), String.format("%s%%", name))));
        Optional.ofNullable(resumeSearchModel.getCreatedDateStart())
                .ifPresent(createdDateStart -> predicates.add(criteriaBuilder.greaterThanOrEqualTo(resume.get(ColumnMetadata.getName(QResumeBean.resumeBean.createdDate)), DateUtil.setZeroTime(createdDateStart))));
        Optional.ofNullable(resumeSearchModel.getCreatedDateEnd())
                .ifPresent(createdDateEnd -> predicates.add(criteriaBuilder.lessThanOrEqualTo(resume.get(ColumnMetadata.getName(QResumeBean.resumeBean.createdDate)), DateUtil.setMaxTime(createdDateEnd))));


        //Result
        criteriaQuery.select(resume)
                .where(predicates.toArray(new Predicate[0]))
                .orderBy(criteriaBuilder.desc(resume.get(ColumnMetadata.getName(QResumeBean.resumeBean.createdDate))));
        final List<ResumeBean> result = entityManager.createQuery(criteriaQuery)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();

        //TotalResultCount
        countQuery.select(criteriaBuilder.count(countQuery.from(ResumeBean.class)));
        entityManager.createQuery(countQuery);
        countQuery.where(predicates.toArray(new Predicate[0]));
        final long totalResultCount = entityManager.createQuery(countQuery).getSingleResult();

        ResumeSearchResultModel resumeSearchResultModel = new ResumeSearchResultModel();
        resumeSearchResultModel.setResult(result);
        resumeSearchResultModel.setCurrentPage(totalResultCount > 0 ? pageNumber : 0);
        resumeSearchResultModel.setTotalResultCount(totalResultCount);
        resumeSearchResultModel.setTotalPages((int) Math.ceil((double) totalResultCount / (double) pageSize));

        return resumeSearchResultModel;
    }
}
