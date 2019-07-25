package models.anime;

import models.anime.Anime.Episode;

import java.util.List;

public interface AnimeSite {
    public enum Provider {
        RAPIDVIDEO,
        MP4UPLOAD
    }

    Anime anime();
    AnimeSite extract(String url);
    List<Anime> search(String keyword);
    Episode getEpisodeVideo(Episode episode, Provider provider);
}
