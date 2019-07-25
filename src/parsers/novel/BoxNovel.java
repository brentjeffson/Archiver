package parsers.novel;

import models.Website;
import org.jsoup.nodes.Document;
import models.novel.Chapter;
import models.novel.Novel;
import models.novel.NovelSite;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BoxNovel extends Website implements NovelSite {
    private static final String BASE_URL = "https://boxnovel.com";

    private static final String NOVEL_COVER_IMAGE_SELECTOR = "div.summary_image > a > img";
    private static final String NOVEL_RATING_SELECTOR = "div.post-rating > div > span";
    private static final String NOVEL_RANKING_SELECTOR = "div.post-content_item:nth-child(4) div.summary-content";
    private static final String NOVEL_AUTHORS_SELECTOR = "div.post-content_item:nth-child(6) div.author-content";
    private static final String NOVEL_ARTISTS_SELECTOR = "div.post-content_item:nth-child(7) div.artist-content";
    private static final String NOVEL_GENRES_SELECTOR = "div.post-content_item:nth-child(8) div.genres-content";
    private static final String NOVEL_RELEASE_SELECTOR = "div.post-content_item:nth-child(1) div.summary-content";
    private static final String NOVEL_STATUS_SELECTOR = "div.post-content_item:nth-child(2) div.summary-content";

    //chapters selectors
    private static final String NOVEL_CHAPTER_CONTAINER_SELECTOR = "li.wp-manga-chapter";
    private static final String NOVEL_CHAPTER_URL_SELECTOR = "a";
    private static final String NOVEL_CHAPTER_RELEASE_SELECTOR = "span.chapter-release-date";

    //chapter content selectors
    private static final String CHAPTER_CONTENT_CONTAINER_SELECTOR = "div.cha-content p";

    //pattenrs
    private static final String LIST_SPLITTER = "\\s?,{1}\\s?";
    private static final String CHAPTER_NAME_PATTERN = "\\w+\\s+(\\d+)\\s?[-:]?\\s?(.+)?";

    //search chapter selectors
    private static final String SEARCH_CHAPTER_CONTAINER_SELECTOR = "div.c-tabs-item__content";

    private Novel mNovel = null;

    @Override
    public Novel novel() {
        return mNovel;
    }

    @Override
    public NovelSite extract(String url) {
        Document doc = get(url);
        if (doc != null) {
            //get informations about the novel
            String title = doc.title();

            String coverImgUrl = doc.selectFirst(NOVEL_COVER_IMAGE_SELECTOR)
                    .attr("src");

            String rating = doc.selectFirst(NOVEL_RATING_SELECTOR)
                    .ownText();

            String ranking = doc.selectFirst(NOVEL_RANKING_SELECTOR)
                    .ownText();
            Matcher matcher = Pattern.compile("(\\d)+th").matcher(ranking);
            ranking = (matcher.find()) ? matcher.group(1) : "";

            String strAuthors = doc.selectFirst(NOVEL_AUTHORS_SELECTOR).ownText();
            String[] authors = strAuthors.split(LIST_SPLITTER);

            String strArtists = doc.selectFirst(NOVEL_ARTISTS_SELECTOR).ownText();
            String[] artists = strAuthors.split(LIST_SPLITTER);

            String strGenres = doc.selectFirst(NOVEL_GENRES_SELECTOR).ownText();
            String[] genres = strGenres.split(LIST_SPLITTER);

            String release = doc.selectFirst(NOVEL_RELEASE_SELECTOR).ownText();
            String status = doc.selectFirst(NOVEL_STATUS_SELECTOR).ownText();

            //get all the chapters of the novel
            ArrayList<Chapter> chapters = new ArrayList<>();
            Elements chapterContainers = doc.select(NOVEL_CHAPTER_CONTAINER_SELECTOR);

            for (Element chapterContainer : chapterContainers) {
                Element urlElement = chapterContainer.selectFirst(NOVEL_CHAPTER_URL_SELECTOR);

                String chapterUrl = urlElement.attr("href");

                String chapterTitle = urlElement.ownText().trim();
                matcher = Pattern.compile(CHAPTER_NAME_PATTERN).matcher(chapterTitle);
                chapterTitle = ((matcher.find())
                        ? ((matcher.group(2) != null)
                        ? matcher.group(2)
                        : matcher.group(1)
                )
                        : chapterTitle);

                String chapterID = ((matcher.group(1) != null)
                        ? matcher.group(1)
                        : chapterUrl.split("/")[5].split("-")[2]
                );

                chapters.add(new Chapter
                        .Builder(chapterID, chapterTitle, chapterUrl)
                        .build()
                );
            }

            mNovel = new Novel.Builder(title, url)
                    .setCoverImageUrl(coverImgUrl)
                    .setRating(rating)
                    .setRanking(ranking)
                    .setAuthors(Arrays.asList(authors))
                    .setArtists(Arrays.asList(artists))
                    .setGenres(Arrays.asList(genres))
                    .setReleaseDate(release)
                    .setStatus(status)
                    .setChapters(chapters)
                    .build();
        }

        return this;
    }

    @Override
    public List<Novel> search(String keyword) {
        ArrayList<Novel> novels = new ArrayList<>();
        HashMap<String, String> params = new HashMap<>();

        params.put("s", keyword);
        params.put("post_type", "wp-manga");
        Document doc = get(BASE_URL, params, Payload.QUERY);

        Elements chapterContainers = doc.select(SEARCH_CHAPTER_CONTAINER_SELECTOR);
        for (Element chapterContainer : chapterContainers) {
            String title = chapterContainer.selectFirst("a").attr("title").trim();

            String url = chapterContainer.selectFirst("a").attr("href").trim();

            String coverImgUrl = chapterContainer.selectFirst("a > img").attr("src");

            novels.add(new Novel.Builder(title, url)
                    .setCoverImageUrl(coverImgUrl)
                    .build()
            );
        }

        return novels;
    }

    @Override
    public Chapter getChapterContent(Chapter chapter) {
        Document docs = get(chapter.getUrl());
        ;
        StringBuilder contentBuilder = new StringBuilder();
        Elements contentContainers = null;

        contentContainers = docs.select(CHAPTER_CONTENT_CONTAINER_SELECTOR);

        if (contentContainers.size() != 0) {
            for (Element contentContainer : contentContainers) {
                if (contentContainer.ownText() != null) {
                    contentBuilder.append(
                            String.format(
                                    "%s\n",
                                    ((contentContainer.ownText() != null)
                                            ? contentContainer.ownText()
                                            : "")
                            )
                    );
                }

            }
        }

        return new Chapter.Builder(chapter)
                .setContent(contentBuilder.toString())
                .build();
    }
}

