package models.manga;

import java.util.ArrayList;

public class Manga {
    //required
    private String url;
    private String title;

    //optional
    private String coverImageUrl;
    private String description;
    private String rank;
    private String releaseDate;
    private String status;
    private ArrayList<String> artists;
    private ArrayList<String> authors;
    private ArrayList<String> genres;
    private ArrayList<Chapter> chapters;

    private Manga() {

    }

    private Manga(Manga manga) {
        this.url = manga.url;
        this.title = manga.title;
        this.coverImageUrl = manga.coverImageUrl;
        this.description = manga.description;
        this.rank = manga.rank;
        this.releaseDate = manga.releaseDate;
        this.status = manga.status;
        this.artists = manga.artists;
        this.authors = manga.authors;
        this.genres = manga.genres;
        this.chapters = manga.chapters;
    }

    private Manga(Builder builder) {
        this.url = builder.url;
        this.title = builder.title;
        this.coverImageUrl = builder.coverImageUrl;
        this.description = builder.description;
        this.rank = builder.rank;
        this.releaseDate = builder.releaseDate;
        this.status = builder.status;
        this.artists = builder.artists;
        this.authors = builder.authors;
        this.genres = builder.genres;
        this.chapters = builder.chapters;
    }

    public Chapter getChapter(int index) {
        try {
            return this.chapters.get(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Chapter Not Found.");
            return null;
        }
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getRank() {
        return rank;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<String> getArtists() {
        return artists;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public ArrayList<Chapter> getChapters() {
        return chapters;
    }

    public enum Status{
        ONGOING,
        COMPLETED,
        HIATUS
    }

    public static class Builder{
        //required
        private String url;
        private String title;

        //optional
        private String coverImageUrl;
        private String description;
        private String rank;
        private String releaseDate;
        private String status;
        private ArrayList<String> artists;
        private ArrayList<String> authors;
        private ArrayList<String> genres;
        private ArrayList<Chapter> chapters;

        public Builder(String title, String url) {
            this.title = title;
            this.url = url;
        }

        public Builder(Manga manga) {
            this.title = manga.title;
            this.url = manga.url;
        }

        public Manga build() {
            return new Manga(this);
        }

        public Builder setCoverImageUrl(String url) {
            this.coverImageUrl = url;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setRank(String rank) {
            this.rank = rank;
            return this;
        }

        public Builder setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setArtists(ArrayList<String> artists) {
            this.artists = artists;
            return this;
        }

        public Builder setAuthors(ArrayList<String> authors) {
            this.authors = authors;
            return this;
        }

        public Builder setGenres(ArrayList<String> genres) {
            this.genres = genres;
            return this;
        }

        public Builder setChapters(ArrayList<Chapter> chapters) {
            this.chapters = chapters;
            return this;
        }
    }

    public static class Chapter {
        //required
        private final String url;
        private final String title;
        private final String number;

        //optional
        private String releaseDate;
        private ArrayList<String> imageUrls;

        public Chapter(String url, String title, String number) {
            this.url = url;
            this.title = title;
            this.number = number;
        }

        private Chapter(Builder builder) {
            this.url = builder.url;
            this.title = builder.title;
            this.number = builder.number;
            this.releaseDate = builder.releaseDate;
            this.imageUrls = builder.imageUrls;
        }

        public String getUrl() {
            return url;
        }

        public String getTitle() {
            return title;
        }

        public String getNumber() {
            return number;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public ArrayList<String> getImageUrls() {
            return imageUrls;
        }

        public static class Builder {
            //required
            private String url;
            private String title;
            private String number;

            //optional
            private String releaseDate = "";
            private ArrayList<String> imageUrls = new ArrayList<>();

            public Builder(String url, String title, String number) {
                this.url = url;
                this.title = title;
                this.number = number;
            }

            public Builder(Chapter chapter) {
                this.url = chapter.url;
                this.title = chapter.title;
                this.number = chapter.number;
                this.releaseDate = chapter.releaseDate;
                this.imageUrls = chapter.imageUrls;
            }

            public Chapter build() {
                return new Chapter(this);
            }

            public Builder setReleaseDate(String releaseDate) {
                this.releaseDate = releaseDate;
                return this;
            }

            public Builder setImageUrls(ArrayList<String> imageUrls) {
                this.imageUrls = imageUrls;
                return this;
            }
        }
    }
}
