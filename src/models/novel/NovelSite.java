package models.novel;

import models.Website;
import java.util.List;

public interface NovelSite {

    Novel novel();
    NovelSite extract(String url);
    List<Novel> search(String keyword);
    Chapter getChapterContent(Chapter chapter);
}
