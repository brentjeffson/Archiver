package models;

import models.anime.Video;

import java.util.ArrayList;

public abstract class ProviderSite extends Website{

    public abstract ArrayList<Video> getVideoUrls(String episodeUrl);
}
