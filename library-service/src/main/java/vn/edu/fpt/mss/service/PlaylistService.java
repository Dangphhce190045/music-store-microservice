package vn.edu.fpt.mss.service;

import vn.edu.fpt.mss.dto.request.PlaylistRequest;
import vn.edu.fpt.mss.dto.response.PlaylistResponse;
import java.util.List;

public interface PlaylistService {
    PlaylistResponse createPlaylist(PlaylistRequest request);
    PlaylistResponse getPlaylistById(Integer playlistId);
    List<PlaylistResponse> getPlaylistsByCustomerId(Integer customerId);
    PlaylistResponse addTrackToPlaylist(Integer playlistId, Integer trackId);
    void removeTrackFromPlaylist(Integer playlistId, Integer trackId);
    void deletePlaylist(Integer playlistId);
}
