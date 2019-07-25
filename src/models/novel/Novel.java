package models.novel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Novel {
    //required
    private String title;
    private String url;

    //optional
    private String coverImageUrl;
    private String description;
    private String releaseDate;
    private String rating;
    private String ranking;
    private String status;
    private List<String> authors;
    private List<String> artists;
    private List<String> genres;
    private List<Chapter> chapters;

    private Novel() {

    }

    private Novel(Builder builder) {
        this.title = builder.title;
        this.url = builder.url;
        this.coverImageUrl = builder.coverImageUrl;
        this.description = builder.description;
        this.releaseDate = builder.releaseDate;
        this.rating = builder.rating;
        this.ranking = builder.ranking;
        this.status = builder.status;
        this.authors = builder.authors;
        this.artists = builder.artists;
        this.genres = builder.genres;
        this.chapters = builder.chapters;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getRating() {
        return rating;
    }

    public String getRanking() {
        return ranking;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public List<String> getArtists() {
        return artists;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public static class Builder {
        //required
        private String title;
        private String url;

        //optional
        private String coverImageUrl;
        private String description;
        private String releaseDate;
        private String rating;
        private String ranking;
        private String status;
        private List<String> authors;
        private List<String> artists;
        private List<String> genres;
        private List<Chapter> chapters;

        public Builder(String title, String url) {
            this.title = title;
            this.url = url;
        }

        public Builder(Novel novel) {
            this.title = novel.title;
            this.url = novel.url;
        }

        public Novel build() {
            return new Novel(this);
        }

        public Builder setCoverImageUrl(String coverImageUrl) {
            this.coverImageUrl = coverImageUrl;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
			return this;
        }

        public Builder setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
			return this;
        }

        public Builder setRating(String rating) {
            this.rating = rating;
			return this;
        }

        public Builder setRanking(String ranking) {
            this.ranking = ranking;
			return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
			return this;
        }

        public Builder setAuthors(List<String> authors) {
            this.authors = authors;
			return this;
        }

        public Builder setArtists(List<String> artists) {
            this.artists = artists;
			return this;
        }

        public Builder setGenres(List<String> genres) {
            this.genres = genres;
			return this;
        }

        public Builder setChapters(List<Chapter> chapters) {
            this.chapters = chapters;
			return this;
        }
    }

}
