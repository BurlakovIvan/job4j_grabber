package ru.job4j.design.srp.setreport;

import ru.job4j.design.srp.Employee;
import ru.job4j.design.srp.TypeReport;

import java.text.SimpleDateFormat;
import java.util.List;

public class ReportProgrammer implements TypeReport {

    @Override
    public String textReport(List<Employee> workers, SimpleDateFormat dateFormat) {
        StringBuilder text = new StringBuilder();
        text.append("<!DOCTYPE html>").append(System.lineSeparator())
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
                .append("</tr>").append(System.lineSeparator());
        for (Employee employee : workers) {
            text.append("<tr>").append(System.lineSeparator())
                    .append("<td>").append(employee.getName()).append("</td>")
                    .append("<td>").append(dateFormat.format(employee.getHired().getTime()))
                    .append("</td>")
                    .append("<td>").append(dateFormat.format(employee.getFired().getTime()))
                    .append("</td>")
                    .append("<td>").append(employee.getSalary()).append("</td>")
                    .append("</tr>").append(System.lineSeparator());
        }
        text.append("</table>").append(System.lineSeparator())
                .append("</body>").append(System.lineSeparator())
                .append("</html>").append(System.lineSeparator());
        return text.toString();
    }
}
