package parsers.anime.providers;

import models.ProviderSite;
import models.Website;
import models.anime.Video;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mp4Upload {
    private static final String CODEC = "codec";
    private static final String RESOLUTION = "resolution";
    private static final String FRAMERATE = "framerate";
    private static final String BITRATE = "bitrate";
    private static final String AUDIO_INFO = "audio_info";

    private static final String VIDEO_URL_PATTERN = "(mp4)\\|(video)\\|(\\w+)\\|(282)\\|src";
    private static final String URL_TEMPLATE = "https://www9.mp4upload.com:%s/d/%s/%s.%s";

    private static final String FILE_INFO_SELECTOR = "div.fileinfo > ul > li > span";

    public static HashMap<String, String> getFileInfos(Document doc) {
        HashMap<String, String> infos = new HashMap<>();

        Elements fileInfoElements = doc.select(FILE_INFO_SELECTOR);

        infos.put(CODEC, fileInfoElements.next().get(0).text());
        infos.put(RESOLUTION, fileInfoElements.next().get(1).text());
        infos.put(FRAMERATE, fileInfoElements.next().get(2).text());
        infos.put(BITRATE, fileInfoElements.next().get(3).text());
        infos.put(AUDIO_INFO, fileInfoElements.next().get(4).text());

        return infos;
    }

    public Video getVideo(Document doc) {
        Matcher matcher = Pattern
                .compile(VIDEO_URL_PATTERN)
                .matcher(doc.html());

        String videoUrl = "";
        if (matcher.find()) {
            videoUrl = String.format(
                    URL_TEMPLATE,
                    matcher.group(4),
                    matcher.group(3),
                    matcher.group(2),
                    matcher.group(1)
            );
        }
        Elements elms = doc.select("div");
        Element qualityElement = doc.selectFirst("div.fileinfo ul li:nth-child(3) span:nth-child(2)");
        String quality = qualityElement.ownText();
        String qualityX = quality.split(" x ")[0].trim();
        String qualityY = quality.split(" x ")[1].trim();

        return new Video(videoUrl, new String[]{qualityX, qualityY});
    }
}
