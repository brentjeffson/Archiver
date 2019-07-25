package models.novel;

public class Chapter {
    //required
    private String chapterId;
    private String title;
    private String url;

    //optional
    private String content;
    private String releaseDate;

    private Chapter() {}

    private Chapter(String chapterId,String title, String url) {
        this.chapterId = chapterId;
        this.title = title;
        this.url = url;
    }

    private Chapter(Builder builder) {
        this.chapterId = builder.chapterId;
        this.title = builder.title;
        this.url = builder.url;
        this.content = builder.content;
        this.releaseDate = builder.releaseDate;
    }

    public String getTitle() {
        return this.title;
    }

    public String getChapterId() {
        return this.chapterId;
    }

    public String getUrl() {
        return url;
    }

    public String getContent() {
        return this.content;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public static class Builder {
        //required
        private String title;
        private String chapterId;
        private String url;

        //optional
        private String content = "";
        private String releaseDate = "";

        public Builder(String chapterId, String title, String url) {
            this.chapterId = chapterId;
            this.title = title;
            this.url = url;
        }

        public Builder(Chapter chapter) {
            this.chapterId = chapter.getChapterId();
            this.title = chapter.getTitle();
            this.url = chapter.getUrl();
        }

        public Chapter build() {
            return new Chapter(this);
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }
    }
}
