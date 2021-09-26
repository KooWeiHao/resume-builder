package resume.builder.api.service;

import resume.builder.api.entity.ResumeBean;
import resume.builder.api.model.ResumeModifyModel;
import resume.builder.api.model.ResumeSearchModel;
import resume.builder.api.model.ResumeSearchResultModel;

public interface ResumeService {
    ResumeBean addOrUpdateResume(ResumeModifyModel resumeModifyModel);
    ResumeSearchResultModel findResume(ResumeSearchModel resumeSearchModel);
}
