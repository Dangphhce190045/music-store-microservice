package vn.edu.fpt.mss.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.mss.dto.request.AddTrackToPlaylistRequest;
import vn.edu.fpt.mss.dto.request.PlaylistRequest;
import vn.edu.fpt.mss.dto.response.PlaylistResponse;
import vn.edu.fpt.mss.service.PlaylistService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/playlists")
@RequiredArgsConstructor
@Tag(name = "Playlists", description = "Customer custom playlist management APIs")
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a custom playlist")
    public PlaylistResponse createPlaylist(@Valid @RequestBody PlaylistRequest request) {
        return playlistService.createPlaylist(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get playlist by ID with track list")
    public PlaylistResponse getPlaylistById(@PathVariable Integer id) {
        return playlistService.getPlaylistById(id);
    }

    @GetMapping
    @Operation(summary = "Get playlists belonging to a customer")
    public List<PlaylistResponse> getPlaylistsByCustomer(@RequestParam Integer customerId) {
        return playlistService.getPlaylistsByCustomerId(customerId);
    }

    @PostMapping("/{id}/tracks")
    @Operation(summary = "Add track to playlist")
    public PlaylistResponse addTrack(@PathVariable Integer id, @Valid @RequestBody AddTrackToPlaylistRequest request) {
        return playlistService.addTrackToPlaylist(id, request.getTrackId());
    }

    @DeleteMapping("/{id}/tracks/{trackId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove track from playlist")
    public void removeTrack(@PathVariable Integer id, @PathVariable Integer trackId) {
        playlistService.removeTrackFromPlaylist(id, trackId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete playlist")
    public void deletePlaylist(@PathVariable Integer id) {
        playlistService.deletePlaylist(id);
    }
}
