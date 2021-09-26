package resume.builder.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import resume.builder.api.entity.ResumeBean;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResumeSearchResultModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<ResumeBean> result;
    private int currentPage;
    private long totalResultCount;
    private int totalPages;
}
