package vn.edu.fpt.mss.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaylistResponse {
    private Integer playlistId;
    private Integer customerId;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private List<Integer> trackIds;
}
