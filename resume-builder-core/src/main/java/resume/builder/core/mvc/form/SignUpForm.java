package resume.builder.core.mvc.form;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class SignUpForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    public String username;

    @NotNull
    public String password;
}
