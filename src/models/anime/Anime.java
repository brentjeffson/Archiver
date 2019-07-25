package models.anime;

import java.util.List;

public class Anime {
    //required fields
    private String title;
    private String url;

    //optional fields
    private String coverImageUrl;
    private List<Episode> episodes;
    private String description;
    private String released;
    private List<String> genres;

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public String getDescription() {
        return description;
    }

    public String getReleased() {
        return released;
    }

    public List<String> getGenres() {
        return genres;
    }

    private Anime() {

    }

    private Anime(Builder builder) {
        this.title = builder.title;
        this.url = builder.url;
        this.coverImageUrl = builder.coverImageUrl;
        this.episodes = builder.episodes;
        this.description = builder.description;
        this.released = builder.released;
        this.genres = builder.genres;
    }

    public static class Builder{
        //required fields
        private String title;
        private String url;

        //optional fields
        private String coverImageUrl;
        private List<Episode> episodes;
        private String description;
        private String released;
        private List<String> genres;

        public Builder(String title, String url) {
            this.title = title;
            this.url = url;
        }

        public Builder(Anime anime) {
            this.title = anime.title;
            this.url = anime.url;
        }
        
        public Anime build() {
            return new Anime(this);
        }

        public Builder setEpisodes(List<Episode> episodes) {
            this.episodes = episodes;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setCoverImageUrl(String coverImageUrl) {
            this.coverImageUrl = coverImageUrl;
            return this;
        }

        public Builder setReleased(String released) {
            this.released = released;
            return this;
        }

        public Builder setGenres(List<String> genres) {
            this.genres = genres;
            return this;
        }
    }

    public static class Episode {
        //require
        private String url;
        private String title;
        private String episodeID;
        //optional
        private Video video;
    
        private Episode(Builder builder) {
            this.url = builder.url;
            this.title = builder.title;
            this.episodeID = builder.episodeID;
            this.video = builder.video;
        }

        public String getUrl() {
            return url;
        }

        public String getTitle() {
            return title;
        }

        public String getEpisodeID() {
            return episodeID;
        }

        public Video getVideo() {
            return video;
        }

        public static class Builder{
            //require
            private String url;
            private String title;
            private String episodeID;
            //optional
            private Video video;

            public Builder(String url, String title, String episodeID) {
                this.url = url;
                this.title = title;
                this.episodeID = episodeID;
            }

            public Builder(Episode episode) {
                this.url = episode.url;
                this.title = episode.title;
                this.episodeID = episode.episodeID;
                this.video = episode.video;
            }

            public Episode build() {
                return new Episode(this);
            }

            public Builder setVideo(Video video) {
                this.video = video;
                return this;
            }
        }
    }
}
