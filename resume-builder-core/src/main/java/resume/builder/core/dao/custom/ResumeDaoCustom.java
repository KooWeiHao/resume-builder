package resume.builder.core.dao.custom;

import resume.builder.api.model.ResumeSearchModel;
import resume.builder.api.model.ResumeSearchResultModel;

public interface ResumeDaoCustom {
    ResumeSearchResultModel find(ResumeSearchModel resumeSearchModel);
}
