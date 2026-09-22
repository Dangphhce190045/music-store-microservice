package vn.edu.fpt.mss.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaylistRequest {

    @NotNull(message = "Customer ID is required")
    private Integer customerId;

    @NotBlank(message = "Playlist name cannot be empty")
    @Size(max = 120, message = "Name must not exceed 120 characters")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
}
