package vn.edu.fpt.mss.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.mss.dto.request.PlaylistRequest;
import vn.edu.fpt.mss.dto.response.PlaylistResponse;
import vn.edu.fpt.mss.entity.Playlist;
import vn.edu.fpt.mss.entity.PlaylistTrack;
import vn.edu.fpt.mss.entity.PlaylistTrackId;
import vn.edu.fpt.mss.common.exception.ResourceNotFoundException;
import vn.edu.fpt.mss.repository.PlaylistRepository;
import vn.edu.fpt.mss.repository.PlaylistTrackRepository;
import vn.edu.fpt.mss.service.PlaylistService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistTrackRepository playlistTrackRepository;

    @Override
    @Transactional
    public PlaylistResponse createPlaylist(PlaylistRequest request) {
        log.info("Creating playlist '{}' for customer {}", request.getName(), request.getCustomerId());
        Playlist playlist = Playlist.builder()
                .customerId(request.getCustomerId())
                .name(request.getName())
                .description(request.getDescription())
                .build();
        return mapToResponse(playlistRepository.save(playlist));
    }

    @Override
    @Transactional(readOnly = true)
    public PlaylistResponse getPlaylistById(Integer playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found with ID: " + playlistId));
        return mapToResponse(playlist);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlaylistResponse> getPlaylistsByCustomerId(Integer customerId) {
        return playlistRepository.findByCustomerId(customerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PlaylistResponse addTrackToPlaylist(Integer playlistId, Integer trackId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found with ID: " + playlistId));

        PlaylistTrackId id = new PlaylistTrackId(playlistId, trackId);
        if (!playlistTrackRepository.existsById(id)) {
            PlaylistTrack playlistTrack = PlaylistTrack.builder()
                    .id(id)
                    .playlist(playlist)
                    .build();
            playlistTrackRepository.save(playlistTrack);
        }

        return getPlaylistById(playlistId);
    }

    @Override
    @Transactional
    public void removeTrackFromPlaylist(Integer playlistId, Integer trackId) {
        PlaylistTrackId id = new PlaylistTrackId(playlistId, trackId);
        if (playlistTrackRepository.existsById(id)) {
            playlistTrackRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public void deletePlaylist(Integer playlistId) {
        if (!playlistRepository.existsById(playlistId)) {
            throw new ResourceNotFoundException("Playlist not found with ID: " + playlistId);
        }
        playlistRepository.deleteById(playlistId);
    }

    private PlaylistResponse mapToResponse(Playlist entity) {
        List<Integer> trackIds = playlistTrackRepository.findByIdPlaylistId(entity.getPlaylistId())
                .stream()
                .map(pt -> pt.getId().getTrackId())
                .collect(Collectors.toList());

        return PlaylistResponse.builder()
                .playlistId(entity.getPlaylistId())
                .customerId(entity.getCustomerId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .trackIds(trackIds)
                .build();
    }
}
