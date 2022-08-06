package ru.job4j.design.srp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.design.srp.setreport.ReportAccounting;
import ru.job4j.design.srp.setreport.ReportHR;
import ru.job4j.design.srp.setreport.ReportOld;
import ru.job4j.design.srp.setreport.ReportProgrammer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.*;

public class ReportEngineTest {

    public final static List<Employee> WORKERS = new ArrayList<>(5);
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd:MM:yyyy HH:mm");
    public static Report engine;

    @BeforeAll
    public static void initialization() {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        initWorkers("Dmitriy", now, now, 181.78, store);
        initWorkers("Petr", now, now, 224.46, store);
        initWorkers("Sergey", now, now, 150.5, store);
        initWorkers("Ivan", now, now, 99.99, store);
        initWorkers("Alexandr", now, now, 123.29, store);
        engine = new ReportEngine(store);
    }

    public static void initWorkers(String name, Calendar hired,
                                       Calendar fired, double salary, MemStore store) {
        Employee worker = new Employee(name, hired, fired, salary);
        WORKERS.add(worker);
        store.add(worker);
    }

    public StringBuilder stringReport(Employee worker) {
        return new StringBuilder()
                .append(worker.getName()).append(";")
                .append(DATE_FORMAT.format(worker.getHired().getTime())).append(";")
                .append(DATE_FORMAT.format(worker.getFired().getTime())).append(";")
                .append(worker.getSalary()).append(";")
                .append(System.lineSeparator());
    }

    public StringBuilder stringReportAccount(Employee worker) {
        /*
        Для бухгалтеров зарплата округляется
         */
        return new StringBuilder()
                .append(worker.getName()).append(";")
                .append(DATE_FORMAT.format(worker.getHired().getTime())).append(";")
                .append(DATE_FORMAT.format(worker.getFired().getTime())).append(";")
                .append(Math.round(worker.getSalary())).append(";")
                .append(System.lineSeparator());
    }

    public StringBuilder stringReportProgrammer(Employee worker) {
        return new StringBuilder()
                .append("<tr>").append(System.lineSeparator())
                .append("<td>").append(worker.getName()).append("</td>")
                .append("<td>").append(DATE_FORMAT.format(worker.getHired().getTime())).append("</td>")
                .append("<td>").append(DATE_FORMAT.format(worker.getFired().getTime())).append("</td>")
                .append("<td>").append(worker.getSalary()).append("</td>")
                .append("</tr>").append(System.lineSeparator());
    }

    public StringBuilder stringReportHR(Employee worker) {
        return new StringBuilder()
                .append(worker.getName()).append(";")
                .append(worker.getSalary()).append(";")
                .append(System.lineSeparator());
    }

    public StringBuilder expectString(String title, String conclusion, Function<Employee,
            StringBuilder> typeReport, List<Employee> workers) {
        StringBuilder expect = new StringBuilder()
                .append(title)
                .append(System.lineSeparator());
        for (Employee worker : workers) {
            expect.append(typeReport.apply(worker));
        }
        if (conclusion.length() > 0) {
            expect.append(conclusion);
        }
        return expect;
    }

    @Test
    public void whenOldGenerated() {
        assertThat(engine.generate(em -> true, new ReportOld()))
                .isEqualTo(expectString("Name; Hired; Fired; Salary;", "",
                        this::stringReport, WORKERS)
                        .toString());
    }

    @Test
    public void whenHRGenerated() {
        var workers = new ArrayList<>(WORKERS);
        workers.sort(Comparator.comparingDouble(Employee::getSalary).reversed());
        assertThat(engine.generate(em -> true, new ReportHR()))
                .isEqualTo(expectString("Name; Salary;", "",
                        this::stringReportHR, workers)
                        .toString());
    }

    @Test
    public void whenAccountingGenerated() {
        assertThat(engine.generate(em -> true, new ReportAccounting()))
                .isEqualTo(expectString("Name; Hired; Fired; Salary;", "",
                        this::stringReportAccount, WORKERS)
                        .toString());
    }

    @Test
    public void whenProgrammerGenerated() {
        StringBuilder title = new StringBuilder();
        title.append("<!DOCTYPE html>").append(System.lineSeparator())
                .append("<html>").append(System.lineSeparator())
                .append("<head>").append(System.lineSeparator())
                .append("<meta charset=\"utf-8\" />").append(System.lineSeparator())
                .append("<title>Report</title>").append(System.lineSeparator())
                .append("</head>").append(System.lineSeparator())
                .append("<body>").append(System.lineSeparator())
                .append("<table>").append(System.lineSeparator())
                .append("<tr>").append(System.lineSeparator())
                .append("<th>Name;</th> <th>Hired;</th> <th>Fired;</th> <th>Salary;</th>")
                .append(System.lineSeparator())
                .append("</tr>");
        StringBuilder conclusion = new StringBuilder();
        conclusion.append("</table>").append(System.lineSeparator())
                .append("</body>").append(System.lineSeparator())
                .append("</html>").append(System.lineSeparator());
        assertThat(engine.generate(em -> true, new ReportProgrammer()))
                .isEqualTo(expectString(title.toString(), conclusion.toString(),
                        this::stringReportProgrammer, WORKERS)
                        .toString());
    }
}