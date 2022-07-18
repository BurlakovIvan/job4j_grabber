package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class HabrCareerParse implements Parse {

    private static final Logger LOG = LogManager.getLogger(HabrCareerParse.class.getName());
    private static final String SOURCE_LINK = "https://career.habr.com";
    private static final String PAGE_LINK = String.format("%s/vacancies/java_developer?page=", SOURCE_LINK);
    private static final int COUNT_PAGE = 5;
    private final DateTimeParser dateTimeParser;

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    private String retrieveDescription(String link) {
        String description;
        try {
            Connection connection = Jsoup.connect(link);
            Document document = connection.get();
            Elements rows = document.select(".style-ugc");
            description = rows.text();
        } catch (IOException ex) {
            LOG.error("Exception ", ex);
            throw new IllegalArgumentException(ex);
        }
        return description;
    }

    private Post createPost(Element element) {
        var titleElement = element.select(".vacancy-card__title").first();
        var linkElement = titleElement.child(0);
        var title = titleElement.text();
        var link = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
        var description = retrieveDescription(link);
        var created = dateTimeParser.parse(element.select(".basic-date")
                .first().attr("datetime"));
        return new Post(title, link, description, created);
    }

    @Override
    public List<Post> list(String link) {
        List<Post> list = new ArrayList<>();
        for (int page = 1; page <= COUNT_PAGE; page++) {
            Connection connection = Jsoup.connect(String.format("%s%d", link, page));
            try {
                Document document = connection.get();
                Elements rows = document.select(".vacancy-card__inner");
                rows.forEach(row -> list.add(createPost(row)));
            } catch (IOException ex) {
                LOG.error("Exception ", ex);
                throw new IllegalArgumentException(ex);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        HabrCareerParse habrCareerParse = new HabrCareerParse(new HabrCareerDateTimeParser());
        List<Post> list = habrCareerParse.list(PAGE_LINK);
        for (Post post : list) {
            System.out.println(post);
        }
    }
}
