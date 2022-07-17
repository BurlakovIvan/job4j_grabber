package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class HabrCareerParse {

    private static final Logger LOG = LogManager.getLogger(HabrCareerParse.class.getName());
    private static final String SOURCE_LINK = "https://career.habr.com";
    private static final String PAGE_LINK = String.format("%s/vacancies/java_developer?page=", SOURCE_LINK);
    private static final int COUNT_PAGE = 5;

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

    public static void main(String[] args) throws IOException {
        HabrCareerDateTimeParser dateTimeParser = new HabrCareerDateTimeParser();
        HabrCareerParse habrCareerParse = new HabrCareerParse();
        for (int page = 1; page <= COUNT_PAGE; page++) {
            System.out.printf("%nДанные с %d страницы:%n", page);
            Connection connection = Jsoup.connect(String.format("%s%d", PAGE_LINK, page));
            Document document = connection.get();
            Elements rows = document.select(".vacancy-card__inner");
            rows.forEach(row -> {
                Element titleElement = row.select(".vacancy-card__title").first();
                Element linkElement = titleElement.child(0);
                String vacancyName = titleElement.text();
                Element dateElement = row.select(".basic-date").first();
                LocalDateTime vacancyDate = dateTimeParser.parse(dateElement.attr("datetime"));
                String link = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
                String description = habrCareerParse.retrieveDescription(link);
                System.out.printf("%s (%s). Описание: %s. Опубликовано: %s%n", vacancyName, link, description, vacancyDate);
            });
        }
    }
}
