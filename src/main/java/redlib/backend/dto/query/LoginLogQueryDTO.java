package redlib.backend.dto.query;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginLogQueryDTO extends PageQueryDTO {
    private String userCode;
    private String ipAddress;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;
    private String orderBy;
}
