package resume.builder.api.service;

import resume.builder.api.entity.ResumeBean;
import resume.builder.api.model.ResumeModifyModel;

public interface ResumeService {
    ResumeBean addOrUpdateResume(ResumeModifyModel resumeModifyModel);
}
