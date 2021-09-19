package resume.builder.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="RESUME")
public class ResumeBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="RESUME_ID")
    private Long resumeId;

    @Column(name="CODE", columnDefinition = "nvarchar(32)", nullable = false, unique = true)
    private String code;

    @Column(name="STATUS", columnDefinition = "nvarchar(16)", nullable = false)
    private String status;

    @Column(name="NAME", columnDefinition = "nvarchar(64)", nullable = false)
    private String name;

    @Column(name="TITLE", columnDefinition = "nvarchar(128)")
    private String title;

    @Column(name="EMAIL", columnDefinition = "nvarchar(254)", nullable = false)
    private String email;

    @Column(name="MOBILE_NUMBER", columnDefinition = "nvarchar(16)", nullable = false)
    private String mobileNumber;

    @Column(name="DATE_OF_BIRTH", columnDefinition="date", nullable = false)
    private Date dateOfBirth;

    @Column(name="NATIONALITY", columnDefinition = "nvarchar(128)", nullable = false)
    private String nationality;

    @Column(name="CAREER_OBJECTIVE", columnDefinition = "nvarchar(300)")
    private String careerObjective;

    @Column(name="PROFILE_PICTURE", columnDefinition = "nvarchar(32)", nullable = false)
    private String profilePicture;

    @Column(name="CREATED_BY", columnDefinition = "nvarchar(64)", nullable = false, updatable = false)
    private String createdBy;

    @Column(name="CREATED_DATE", columnDefinition="datetime", nullable = false, updatable = false)
    private Date createdDate;

    @Column(name="UPDATED_BY", columnDefinition = "nvarchar(64)", nullable = false)
    private String updatedBy;

    @Column(name="UPDATED_DATE", columnDefinition="datetime", nullable = false)
    private Date updatedDate;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ResumeBean)) return false;
        ResumeBean that = (ResumeBean)obj;
        return this.resumeId.equals(that.resumeId);
    }

    @Override
    public int hashCode() {
        return 41 * (41 + resumeId.intValue());
    }

    public enum CodePrefix{
        RES("RES");

        private final String codePrefix;
        CodePrefix(String codePrefix) {
            this.codePrefix = codePrefix;
        }

        @Override
        public String toString() {
            return this.codePrefix;
        }
    }

    public enum Status{
        ACTIVE("active"),
        INACTIVE("inactive");

        private final String status;
        Status(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return this.status;
        }
    }
}
