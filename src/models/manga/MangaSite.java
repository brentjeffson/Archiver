package models.manga;

import models.Website;
import org.jsoup.nodes.Document;
import models.manga.Manga.Chapter;

import java.util.List;

public interface MangaSite {
    Manga manga();
    MangaSite extract(String url);
    List<Manga> search(String keyword);
    Chapter getChapterImages(Chapter chapter);
}
