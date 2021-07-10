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
@Table(name="ACCOUNT")
public class AccountBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ACCOUNT_ID")
    private Long accountId;

    @Column(name="USERNAME", columnDefinition = "nvarchar(64)", nullable = false, unique = true)
    private String username;

    @Column(name="AUTH_USER_ID", columnDefinition = "varchar(36)", nullable = false, unique = true)
    private String authUserId;

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
        if (!(obj instanceof AccountBean)) return false;
        AccountBean that = (AccountBean)obj;
        return this.accountId.equals(that.accountId);
    }

    @Override
    public int hashCode() {
        return 41 * (41 + accountId.intValue());
    }
}
