package resume.builder.core.mvc.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignUpForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private String username;

    @NotNull
    private String password;
}
