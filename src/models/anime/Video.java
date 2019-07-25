package models.anime;

public class Video {
    private String url;
    private String[] quality;

    public Video(String url, String[] quality) {
        this.url = url;
        this.quality = quality;
    }

    public String getUrl() {
        return url;
    }

    public String[] getQuality() {
        return quality;
    }
}
