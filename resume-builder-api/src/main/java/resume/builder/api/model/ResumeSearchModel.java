package resume.builder.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResumeSearchModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String name;
    private Date createdDateStart;
    private Date createdDateEnd;

    private int pageNumber;
    private int pageSize;
}
