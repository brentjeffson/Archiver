package parsers.anime;

import models.Website;
import models.anime.Anime;
import models.anime.Anime.Episode;
import models.anime.AnimeSite;
import models.anime.Video;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoGoAnime extends Website implements AnimeSite {
    private static final String BASE_URL = "https://www20.gogoanimes.tv";

    //apis
    private static final String SEARCH_API = BASE_URL + "//search.html";
    private static final String EPISODE_LIST_API = BASE_URL + "//load-list-episode";

    //episode selectors
    private static final String EPISODE_CONTAINER_SELECTOR = "ul#episode_related > li";

    //anime selectors
    private static final String ANIME_TITLE_SELECTOR = "div.anime_info_body_bg > h1";
    private static final String ANIME_COVER_IMAGE_URL_SELECTOR = "div.anime_info_body_bg > img";
    private static final String ANIME_ID_SELECTOR = "div.anime_info_episodes_next > input#movie_id";
    private static final String ANIME_DEFAULT_EPISODE_SELECTOR = "div.anime_info_episodes_next > input#default_ep";
    private static final String ANIME_RANGE_SELECTOR = "ul#episode_page > li > a";

    //video selectors
    private static final String VIDEO_URL_SELECTOR = "li.%s > a";

    //search anime selectors
    private static final String SEARCHED_ANIME_CONTAINER_SELECTOR = "ul.items > li";
    private static final String SEARCHED_ANIME_URL_SELECTOR = "div.img > a";
    private static final String SEARCHED_ANIME_COVER_IMG_SELECTOR = "div.img > a > img";

    private HashMap<Provider, String> mProviderValueMap = new HashMap<Provider, String>(){{
        put(Provider.MP4UPLOAD, "mp4");
        put(Provider.RAPIDVIDEO, "rapid");
    }};

    private Anime mAnime;

    @Override
    public Anime anime() {
        return mAnime;
    }

    @Override
    public AnimeSite extract(String url) {
        //get document from url
        Document doc = get(url);

        //get anime information
        String animeTitle = doc.selectFirst(ANIME_TITLE_SELECTOR).ownText();
        String animeCoverImageUrl = doc.selectFirst(ANIME_COVER_IMAGE_URL_SELECTOR).attr("src");

        //get query parameter values
        String animeId = doc.selectFirst(ANIME_ID_SELECTOR)
                .attr("value");
        String defaultEpisode = doc.selectFirst(ANIME_DEFAULT_EPISODE_SELECTOR)
                .attr("value");
        String episodeStart = doc.selectFirst(ANIME_RANGE_SELECTOR)
                .attr("ep_start");
        String episodeEnd = doc.selectFirst(ANIME_RANGE_SELECTOR)
                .attr("ep_end");

        //get list of episodes
        HashMap<String, String> params = new HashMap<>();
        params.put("ep_start", episodeStart);
        params.put("ep_end", episodeEnd);
        params.put("id", animeId);
        params.put("default_ep", defaultEpisode);

        Document episodesDoc = get(EPISODE_LIST_API, params, Payload.QUERY);

        //find all episodes of the anime
        List<Episode> episodes = new ArrayList<>();
        Elements episodeContainers = episodesDoc.select(EPISODE_CONTAINER_SELECTOR);
        for (Element episodeContainer : episodeContainers) {
            String epId = episodeContainer.selectFirst("li > a").attr("href");
            epId = epId.trim().split("-")[epId.trim().split("-").length-1];

            String epTitle = episodeContainer.selectFirst("li > a > div.name").ownText();

            String epUrl = episodeContainer.selectFirst("li > a").attr("href").trim();

            episodes.add(new Episode.Builder(epUrl, epTitle, epId)
                    .build()
            );
        }
        mAnime = new Anime.Builder(animeTitle, url)
                .setCoverImageUrl(animeCoverImageUrl)
                .setEpisodes(episodes)
                .build();
        return this;
    }

    @Override
    public List<Anime> search(String keyword) {
        ArrayList<Anime> animes = new ArrayList<>();

        HashMap<String, String> query = new HashMap<>();
        query.put("keyword", keyword);

        Document doc = get(SEARCH_API, query, Payload.QUERY);
        if (doc != null) {
            Elements animeContainers = doc.select(SEARCHED_ANIME_CONTAINER_SELECTOR);
            for (Element animeContainer : animeContainers) {

                String aTitle = doc.selectFirst(SEARCHED_ANIME_URL_SELECTOR).attr("title");

                String aUrl = doc.selectFirst(SEARCHED_ANIME_URL_SELECTOR).attr("href");

                String aCoverImgUrl = doc.selectFirst(SEARCHED_ANIME_COVER_IMG_SELECTOR).attr("src");

                animes.add(new Anime.Builder(aTitle, aUrl)
                        .setCoverImageUrl(aCoverImgUrl)
                        .build());
            }
        }
        return animes;
    }

    @Override
    public Episode getEpisodeVideo(Episode episode, Provider provider) {
        Document doc = get(GoGoAnime.BASE_URL + episode.getUrl());

        //get video file url
        String videoSrcUrl = doc.selectFirst(String.format(
                VIDEO_URL_SELECTOR,
                mProviderValueMap.get(provider)
        )).attr("data-video");

        doc = get(videoSrcUrl);



        switch (provider) {
            case MP4UPLOAD:
                videoSrcUrl = videoSrcUrl.replace("embed-", "");
                doc = get(videoSrcUrl);
                break;
        }

        //get video quality
        String quality = doc.selectFirst("div.fileinfo li:nth-child(3) > span:nth-child(2)").ownText();
        quality = quality.split(" x ")[1];

        return null;
//        return new Episode.Builder(episode)
//                .setVideo(new Video("", quality))
//                .build();
    }

}
