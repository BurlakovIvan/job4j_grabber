package ru.job4j.design.srp;

import java.text.SimpleDateFormat;
import java.util.List;

public interface TypeReport {
    String textReport(List<Employee> workers, SimpleDateFormat dateFormat);
}
