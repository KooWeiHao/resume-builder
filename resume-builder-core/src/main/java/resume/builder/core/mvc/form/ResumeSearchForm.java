package resume.builder.core.mvc.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResumeSearchForm implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;

    private String name;
}
