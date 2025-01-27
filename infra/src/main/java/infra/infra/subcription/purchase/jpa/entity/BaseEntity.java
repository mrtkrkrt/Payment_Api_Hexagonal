package infra.infra.subcription.purchase.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 3657643043432447168L;

    @Id
    private String id;

    @CreatedDate
    @Field(name = "createdDate")
    private Date createdDate = getLocalCurrentTimeStamp();


    @LastModifiedDate
    @Field(name = "updatedDate")
    @JsonIgnore
    private Date updatedDate = getLocalCurrentTimeStamp();


    @LastModifiedBy
    @Field(name = "updated_by")
    private String updatedBy;


    public static Timestamp getLocalCurrentTimeStamp() {
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Europe/Istanbul"));
        return Timestamp.from(zdt.toInstant());
    }
}
