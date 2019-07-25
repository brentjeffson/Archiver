package models;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;

public class Website {
    private int TIMEOUT = 600*1000;

    private String mUrl;
    private Document mDocument;

    public enum Payload {
        QUERY,
        HEADER,
        NONE
    }

    protected Document get(String url) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .headers(headers)
                    .maxBodySize(0)
                    .timeout(TIMEOUT)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    protected final Document get(String url, HashMap<String, String> payload, Payload type) {
        Document doc = null;
        try {
            switch (type) {
                case QUERY:
                    doc = Jsoup.connect(url).data(payload).timeout(TIMEOUT).get();
                    break;
                case HEADER:
                    doc = Jsoup.connect(url).headers(payload).timeout(TIMEOUT).get();
                    break;
                case NONE:
                    doc = Jsoup.connect(url).timeout(TIMEOUT).get();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public final void setTimeout(int seconds) {
        this.TIMEOUT = seconds*1000;
    }
}
