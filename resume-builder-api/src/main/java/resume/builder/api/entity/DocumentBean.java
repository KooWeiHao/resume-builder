package resume.builder.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="DOCUMENT")
public class DocumentBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="DOCUMENT_ID", columnDefinition = "nvarchar(36)")
    private String documentId;

    @Column(name="CODE", columnDefinition = "nvarchar(32)", nullable = false, unique = true)
    private String code;

    @Column(name="NAME", columnDefinition = "nvarchar(256)", nullable = false)
    private String name;

    @Column(name="TYPE", columnDefinition = "nvarchar(256)", nullable = false)
    private String type;

    @Lob
    @Column(name="DATA", nullable = false)
    private byte[] data;

    @Column(name="SIZE", nullable = false)
    private Long size;

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
        if (!(obj instanceof DocumentBean)) return false;
        DocumentBean that = (DocumentBean)obj;
        return this.documentId.equals(that.documentId);
    }

    @Override
    public int hashCode() {
        return 41 * (41 + documentId.hashCode());
    }

    public enum CodePrefix{
        DOC("DOC");

        private final String codePrefix;
        CodePrefix(String codePrefix) {
            this.codePrefix = codePrefix;
        }

        @Override
        public String toString() {
            return this.codePrefix;
        }
    }
}
