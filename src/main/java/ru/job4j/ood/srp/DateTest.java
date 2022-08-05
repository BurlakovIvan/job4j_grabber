package ru.job4j.ood.srp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
Класс знает какой вид даты ему поступает
что будет припятствовать расширению программы
 */
public class DateTest {

    public void printDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsingDate;
        try {
            parsingDate = simpleDateFormat.parse(date);
            System.out.println(parsingDate);
        } catch (ParseException e) {
           e.printStackTrace();
        }
    }
}
