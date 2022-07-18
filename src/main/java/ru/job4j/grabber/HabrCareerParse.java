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
    private static final int COUNT_PAGE = 5;
    private final DateTimeParser dateTimeParser;

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    private String retrieveDescription(String link) {
        String description = "";
        try {
            Connection connection = Jsoup.connect(link);
            Document document = connection.get();
            Elements rows = document.select(".style-ugc");
            description = rows.text();
        } catch (IOException ex) {
            LOG.error("Exception ", ex);
        }
        return description;
    }

    @Override
    public List<Post> list(String link) {
        List<Post> list = new ArrayList<>();
        int id = 0;
        for (int page = 1; page <= COUNT_PAGE; page++) {
            Connection connection = Jsoup
                    .connect(String.format("%s/vacancies/java_developer?page=%d", link, page));
            try {
                Document document = connection.get();
                Elements rows = document.select(".vacancy-card__inner");
                for (Element row : rows) {
                    Element titleElement = row.select(".vacancy-card__title").first();
                    Element linkElement = titleElement.child(0);
                    String vacancyLink = String.format("%s%s", link, linkElement.attr("href"));
                    list.add(new Post(id++, titleElement.text(),
                            vacancyLink, retrieveDescription(vacancyLink),
                            dateTimeParser
                                    .parse(row.select(".basic-date")
                                            .first().attr("datetime"))));
                }
            } catch (IOException ex) {
                LOG.error("Exception ", ex);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        HabrCareerParse habrCareerParse = new HabrCareerParse(new HabrCareerDateTimeParser());
        List<Post> postList = habrCareerParse.list(SOURCE_LINK);
        for (Post post : postList) {
            System.out.println(post);
        }
    }
}
