package resume.builder.core.mvc.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResumeAboutMeForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private MultipartFile profilePicture;

    @NotNull
    private String name;

    private String title;

    @NotNull
    private String email;

    @NotNull
    private String mobileNumber;

    @NotNull
    private Date dateOfBirth;

    @NotNull
    private String nationality;

    private String careerObjective;
}
