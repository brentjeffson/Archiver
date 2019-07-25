package parsers.manga;

import models.Website;
import models.manga.Manga.Chapter;
import models.manga.Manga;
import models.manga.MangaSite;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MangaTensei extends Website implements MangaSite{
    public static final String BASE_URL = "https://mangatensei.com";
    private static final String SEARCH_API = BASE_URL.concat("/search");

    //search selectors
    private static final String SEARCH_CONTAINER_SELECTOR = "div.item";
    private static final String SEARCH_COVER_IMAGE_SELECTOR = "a.item-cover > img";
    private static final String SEARCH_TITLE_SELECTOR = "div.item-text > a.item-title";

    //chapter selectors
    private static final String CHAPTER_CONTAINER_SELECTOR = "div.main > div";
    private static final String CHAPTER_SELECTOR = "a.chapt";
    private static final String CHAPTER_ID_SELECTOR = "b";
    private static final String CHAPTER_IMAGES_SELECTOR = "(https?:[/_\\w\\.\\-]+\\.[jpng]{3})";

    //manga info selectors
    private static final String COVER_IMAGE_SELECTOR = "div.attr-cover > img";
    private static final String TITLE_SELECTOR = "h3.item-title > a";
    private static final String RANK_SELECTOR = "div.attr-item:nth-child(1) > span";
    private static final String AUTHORS_SELECTOR = "div.attr-item:nth-child(2) > span";
    private static final String GENRES_SELECTOR = "div.attr-item:nth-child(3) > span";
    private static final String STATUS_SELECTOR = "div.attr-item:nth-child(5) > span";
    private static final String DESCRIPTION_SELECTOR = "h5.mt-3 + pre";

    private Manga mManga;

    @Override
    public Manga manga() {
        return mManga;
    }

    @Override
    public MangaSite extract(String url) {
        //get document from url
        Document doc = get(url);
        Matcher matcher;

        //extract chapters
        ArrayList<Chapter> chapters = new ArrayList<>();
        Elements chapterContainerElements = doc.select(CHAPTER_CONTAINER_SELECTOR);
        for (Element chapterContainerElement : chapterContainerElements) {
            String id = chapterContainerElement.selectFirst(CHAPTER_ID_SELECTOR)
                    .ownText().trim();
            String cUrl = chapterContainerElement.selectFirst(CHAPTER_SELECTOR)
                    .attr("href").trim();
            String title = chapterContainerElement.selectFirst(CHAPTER_SELECTOR)
                    .ownText().replace(":", "").trim();

            matcher = Pattern.compile("(\\d+)").matcher(id);
            if (matcher.find()) {
                id = matcher.group(1).trim();
            }
            chapters.add(new Chapter(cUrl, title, id));
        }


        //extract extra infos
        //cover image
        String coverImageUrl = doc.selectFirst(COVER_IMAGE_SELECTOR).attr("src");
        if (!coverImageUrl.contains("https:")) {
            coverImageUrl = coverImageUrl.trim().replace("//", "https://");
        }
        //title
        String title = doc.selectFirst(TITLE_SELECTOR).ownText();
        //rank
        String rank = doc.selectFirst(RANK_SELECTOR).ownText();
        matcher = Pattern.compile("(\\d+)th").matcher(rank);
        if (matcher.find()) {
            rank = matcher.group(1).trim();
        }
        //authors
        ArrayList<String> authors = new ArrayList<>(
                Arrays.asList(doc.selectFirst(AUTHORS_SELECTOR).text().split(" / ")));
        //genres
        ArrayList<String> genres = new ArrayList<>(
                Arrays.asList(doc.selectFirst(GENRES_SELECTOR).text().split(" / ")));
        //status
        String status = doc.selectFirst(STATUS_SELECTOR).ownText();
        //description
        String description = doc.selectFirst(DESCRIPTION_SELECTOR).ownText();

        mManga = new Manga.Builder(title, url)
                .setCoverImageUrl(coverImageUrl)
                .setRank(rank)
                .setAuthors(authors)
                .setGenres(genres)
                .setStatus(status)
                .setChapters(chapters)
                .setDescription(description)
                .build();
        return this;
    }

    @Override
    public List<Manga> search(String keyword) {
        ArrayList<Manga> mangas = new ArrayList<>();

        HashMap<String, String> params = new HashMap<>();
        params.put("q", keyword);

        Document doc = get(SEARCH_API, params, Payload.QUERY);

        Elements mangaContainers = doc.select(SEARCH_CONTAINER_SELECTOR);
        for (Element mangaContainer : mangaContainers) {
            String title = mangaContainer.selectFirst(SEARCH_TITLE_SELECTOR).text();

            String url = mangaContainer.selectFirst(SEARCH_TITLE_SELECTOR).attr("href");

            String coverImageUrl = mangaContainer.selectFirst(SEARCH_COVER_IMAGE_SELECTOR).attr("src");
            coverImageUrl = (!coverImageUrl.contains("https:"))
                    ? coverImageUrl.trim().replace("//", "https://")
                    : coverImageUrl;


            mangas.add(new Manga.Builder(title, url)
                    .setCoverImageUrl(coverImageUrl)
                    .build()
            );
        }

        return mangas;
    }

    @Override
    public Chapter getChapterImages(Chapter chapter) {
        Document doc = get(BASE_URL + chapter.getUrl());

        ArrayList<String> images = new ArrayList<>();

        Matcher matcher = Pattern.compile(CHAPTER_IMAGES_SELECTOR).matcher(doc.toString());
        while (matcher.find()) {
            String imageUrl = matcher.group(1);
            if (!imageUrl.contains(BASE_URL)) {
                images.add(imageUrl);
            }
        }

        return new Chapter.Builder(chapter)
                .setImageUrls(images)
                .build();
    }
}
