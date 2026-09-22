package vn.edu.fpt.mss.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddTrackToPlaylistRequest {

    @NotNull(message = "Track ID is required")
    private Integer trackId;
}
