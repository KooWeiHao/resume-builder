package resume.builder.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResumeModifyModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private MultipartFile profilePicture;
    private String name;
    private String title;
    private String email;
    private String mobileNumber;
    private Date dateOfBirth;
    private String nationality;
    private String careerObjective;
    private String createdBy;
}
