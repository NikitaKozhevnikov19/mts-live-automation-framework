package models.mobile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponseDto {

    @JsonProperty("total_count")
    private Integer totalCount;

    private List<EventItem> items;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EventItem {
        private String id;
        private String title;
        private String city;
    }
}
