package models.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserJsonPostResponse {
    private String id;
    private String name;
    private String job;
    private String createdAt;
}
