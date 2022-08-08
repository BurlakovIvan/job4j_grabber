package ru.job4j.design.srp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.design.srp.setreport.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringJoiner;

import static org.assertj.core.api.Assertions.*;

public class ReportEngineTest {

    public final static List<Employee> WORKERS = new ArrayList<>(5);
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd:MM:yyyy HH:mm");
    public static Calendar now;

    @BeforeAll
    public static void initialization() {
        Store store = new MemStore();
        now = Calendar.getInstance();
        initWorkers("Dmitriy", now, now, 181.78, store);
        initWorkers("Petr", now, now, 224.46, store);
        initWorkers("Alexandr", now, now, 123.29, store);
    }

    public static void initWorkers(String name, Calendar hired,
                                       Calendar fired, double salary, Store store) {
        Employee worker = new Employee(name, hired, fired, salary);
        WORKERS.add(worker);
        store.add(worker);
    }

    @Test
    public void whenOldGenerated() {
        var dateHiredFired = DATE_FORMAT.format(now.getTime());
        StringBuilder expect = new StringBuilder()
                .append("Name; Hired; Fired; Salary;").append(System.lineSeparator())
                .append("Dmitriy").append(";")
                .append(dateHiredFired).append(";").append(dateHiredFired).append(";")
                .append(181.78).append(";")
                .append(System.lineSeparator())
                .append("Petr").append(";")
                .append(dateHiredFired).append(";").append(dateHiredFired).append(";")
                .append(224.46).append(";")
                .append(System.lineSeparator())
                .append("Alexandr").append(";")
                .append(dateHiredFired).append(";").append(dateHiredFired).append(";")
                .append(123.29).append(";")
                .append(System.lineSeparator());
        Store store = new MemStore();
        for (Employee employee : WORKERS) {
            store.add(employee);
        }
        Report engine = new ReportEngine(store);
        assertThat(engine.generate(em -> true)).isEqualTo(expect.toString());
    }

    @Test
    public void whenGeneratedJSON() {
        Store store = new MemStore();
        Employee worker = new Employee("Dmitriy", now, now, 181.78);
        store.add(worker);
        worker = new Employee("Petr", now, now, 224.46);
        store.add(worker);
        worker = new Employee("Alexandr", now, now, 123.29);
        store.add(worker);
        StringBuilder expect = new StringBuilder();
        expect.append("[{\"name\":\"Dmitriy\",\"hired\":{\"year\":").append(now.get(Calendar.YEAR))
                .append(",\"month\":").append(now.get(Calendar.MONTH)).append(",\"dayOfMonth\":")
                .append(now.get(Calendar.DAY_OF_MONTH)).append(",\"hourOfDay\":")
                .append(now.get(Calendar.HOUR_OF_DAY)).append(",\"minute\":")
                .append(now.get(Calendar.MINUTE)).append(",\"second\":")
                .append(now.get(Calendar.SECOND))
                .append("},\"fired\":{\"year\":").append(now.get(Calendar.YEAR))
                .append(",\"month\":").append(now.get(Calendar.MONTH))
                .append(",\"dayOfMonth\":")
                .append(now.get(Calendar.DAY_OF_MONTH))
                .append(",\"hourOfDay\":").append(now.get(Calendar.HOUR_OF_DAY))
                .append(",\"minute\":").append(now.get(Calendar.MINUTE))
                .append(",\"second\":")
                .append(now.get(Calendar.SECOND)).append("},\"salary\":181.78},")
                .append("{\"name\":\"Petr\",\"hired\":{\"year\":").append(now.get(Calendar.YEAR))
                .append(",\"month\":").append(now.get(Calendar.MONTH)).append(",\"dayOfMonth\":")
                .append(now.get(Calendar.DAY_OF_MONTH)).append(",\"hourOfDay\":")
                .append(now.get(Calendar.HOUR_OF_DAY)).append(",\"minute\":")
                .append(now.get(Calendar.MINUTE)).append(",\"second\":").append(now.get(Calendar.SECOND))
                .append("},\"fired\":{\"year\":").append(now.get(Calendar.YEAR))
                .append(",\"month\":").append(now.get(Calendar.MONTH)).append(",\"dayOfMonth\":")
                .append(now.get(Calendar.DAY_OF_MONTH)).append(",\"hourOfDay\":").append(now.get(Calendar.HOUR_OF_DAY))
                .append(",\"minute\":").append(now.get(Calendar.MINUTE)).append(",\"second\":")
                .append(now.get(Calendar.SECOND)).append("},\"salary\":224.46},")
                .append("{\"name\":\"Alexandr\",\"hired\":{\"year\":").append(now.get(Calendar.YEAR))
                .append(",\"month\":").append(now.get(Calendar.MONTH)).append(",\"dayOfMonth\":")
                .append(now.get(Calendar.DAY_OF_MONTH)).append(",\"hourOfDay\":")
                .append(now.get(Calendar.HOUR_OF_DAY)).append(",\"minute\":")
                .append(now.get(Calendar.MINUTE)).append(",\"second\":").append(now.get(Calendar.SECOND))
                .append("},\"fired\":{\"year\":").append(now.get(Calendar.YEAR))
                .append(",\"month\":").append(now.get(Calendar.MONTH)).append(",\"dayOfMonth\":")
                .append(now.get(Calendar.DAY_OF_MONTH)).append(",\"hourOfDay\":").append(now.get(Calendar.HOUR_OF_DAY))
                .append(",\"minute\":").append(now.get(Calendar.MINUTE)).append(",\"second\":")
                .append(now.get(Calendar.SECOND)).append("},\"salary\":123.29}]");
        Report engine = new ReportToJSON(store);
        assertThat(engine.generate(em -> true)).isEqualTo(expect.toString());
    }

    @Test
    public void whenGeneratedXML() {
        Store store = new MemStore();
        Employee worker = new Employee("Dmitriy", now, now, 181.78);
        store.add(worker);
        worker = new Employee("Petr", now, now, 224.46);
        store.add(worker);
        worker = new Employee("Alexandr", now, now, 123.29);
        store.add(worker);
        Report engine = new ReportToXML(store);
        StringJoiner expect = new StringJoiner("\n");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String date = formatter.format(now.getTime());
        expect.add("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
                .add("<Report>")
                .add("    <employees>")
                .add(String.format("        <fired>%s</fired>", date))
                .add(String.format("        <hired>%s</hired>", date))
                .add("        <name>Dmitriy</name>")
                .add("        <salary>181.78</salary>")
                .add("    </employees>")
                .add("    <employees>")
                .add(String.format("        <fired>%s</fired>", date))
                .add(String.format("        <hired>%s</hired>", date))
                .add("        <name>Petr</name>")
                .add("        <salary>224.46</salary>")
                .add("    </employees>")
                .add("    <employees>")
                .add(String.format("        <fired>%s</fired>", date))
                .add(String.format("        <hired>%s</hired>", date))
                .add("        <name>Alexandr</name>")
                .add("        <salary>123.29</salary>")
                .add("    </employees>")
                .add("</Report>\n");
        assertThat(engine.generate(em -> true)).isEqualTo(expect.toString());
    }

    @Test
    public void whenHRGenerated() {
        StringBuilder expect = new StringBuilder()
                .append("Name; Salary;").append(System.lineSeparator())
                .append("Petr").append(";").append(224.46).append(";")
                .append(System.lineSeparator())
                .append("Dmitriy").append(";").append(181.78).append(";")
                .append(System.lineSeparator())
                .append("Alexandr").append(";").append(123.29).append(";")
                .append(System.lineSeparator());
        Store store = new MemStore();
        for (Employee employee : WORKERS) {
            store.add(employee);
        }
        Report engine = new ReportHR(store);
        assertThat(engine.generate(em -> true)).isEqualTo(expect.toString());
    }

    @Test
    public void whenAccountingGenerated() {
        var dateHiredFired = DATE_FORMAT.format(now.getTime());
        StringBuilder expect = new StringBuilder()
                .append("Name; Hired; Fired; Salary;").append(System.lineSeparator())
                .append("Dmitriy").append(";")
                .append(dateHiredFired).append(";").append(dateHiredFired).append(";")
                .append(182).append(";")
                .append(System.lineSeparator())
                .append("Petr").append(";")
                .append(dateHiredFired).append(";").append(dateHiredFired).append(";")
                .append(224).append(";")
                .append(System.lineSeparator())
                .append("Alexandr").append(";")
                .append(dateHiredFired).append(";").append(dateHiredFired).append(";")
                .append(123).append(";")
                .append(System.lineSeparator());
        Store store = new MemStore();
        for (Employee employee : WORKERS) {
            store.add(employee);
        }
        Report engine = new ReportAccounting(store);
        assertThat(engine.generate(em -> true)).isEqualTo(expect.toString());
    }

    @Test
    public void whenProgrammerGenerated() {
        var dateHiredFired = DATE_FORMAT.format(now.getTime());
        StringBuilder expect = new StringBuilder();
        expect.append("<!DOCTYPE html>").append(System.lineSeparator())
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
                .append("</tr>").append(System.lineSeparator())
                .append("<tr>").append(System.lineSeparator())
                .append("<td>").append("Dmitriy").append("</td>")
                .append("<td>").append(dateHiredFired).append("</td>")
                .append("<td>").append(dateHiredFired).append("</td>")
                .append("<td>").append(181.78).append("</td>")
                .append("</tr>").append(System.lineSeparator())
                .append("<tr>").append(System.lineSeparator())
                .append("<td>").append("Petr").append("</td>")
                .append("<td>").append(dateHiredFired).append("</td>")
                .append("<td>").append(dateHiredFired).append("</td>")
                .append("<td>").append(224.46).append("</td>")
                .append("</tr>").append(System.lineSeparator())
                .append("<tr>").append(System.lineSeparator())
                .append("<td>").append("Alexandr").append("</td>")
                .append("<td>").append(dateHiredFired).append("</td>")
                .append("<td>").append(dateHiredFired).append("</td>")
                .append("<td>").append(123.29).append("</td>")
                .append("</tr>").append(System.lineSeparator())
                .append("</table>").append(System.lineSeparator())
                .append("</body>").append(System.lineSeparator())
                .append("</html>").append(System.lineSeparator());
        Store store = new MemStore();
        for (Employee employee : WORKERS) {
            store.add(employee);
        }
        Report engine = new ReportProgrammer(store);
        assertThat(engine.generate(em -> true)).isEqualTo(expect.toString());
    }
}