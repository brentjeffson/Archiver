package parsers.anime.providers;

import models.ProviderSite;
import models.anime.Video;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Rapidvideo extends ProviderSite {

    public ArrayList<Video> getVideoUrls(String markup) {
        ArrayList<Video> videoUrls = new ArrayList<>();

        Document doc = Jsoup.parse(markup);

//        Elements videoElements = parse(url).select("video#videojs>source");
//
//        for (int index = 0; index < videoElements.size(); index++) {
//            Element sourceElement = videoElements.get(index);
//
//            String videoUrl = sourceElement.attr("src");
//            String type = sourceElement.attr("type");
//            String label = sourceElement.attr("label");
//            String dataRes = sourceElement.attr("data-res");
//
//            videoUrls.add(new Video(videoUrl, dataRes));
//        }

        return videoUrls;
    }
}
