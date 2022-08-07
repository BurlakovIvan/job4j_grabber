package ru.job4j.design.srp.setreport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.design.srp.Employee;
import ru.job4j.design.srp.Report;
import ru.job4j.design.srp.Store;

import java.util.function.Predicate;

public class ReportToJSON implements Report {
    private Store store;

    public ReportToJSON(Store store) {
        this.store = store;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        final Gson gson = new GsonBuilder().create();
        return gson.toJson(store.findBy(filter));
    }
}
