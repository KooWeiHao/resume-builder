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
@Table(name="SMART_SEQUENCE")
public class SmartSequenceBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SMART_SEQUENCE_ID")
    private Long smartSequenceId;

    @Column(name="sequence", columnDefinition = "int", nullable = false)
    private Integer sequence = 0;

    @Column(name="prefix", columnDefinition = "nvarchar(8)", nullable = false)
    private String prefix;

    @Column(name="for_date", columnDefinition="date")
    private Date forDate;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof SmartSequenceBean)) return false;
        SmartSequenceBean that = (SmartSequenceBean)obj;
        return this.smartSequenceId.equals(that.smartSequenceId);
    }

    @Override
    public int hashCode() {
        return 41 * (41 + smartSequenceId.intValue());
    }
}
