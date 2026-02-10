package models.api;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserJsonPutResponse {
    private String name;
    private String job;
    private String updatedAt;
}
