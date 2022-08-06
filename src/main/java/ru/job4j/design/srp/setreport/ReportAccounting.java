package ru.job4j.design.srp.setreport;

import ru.job4j.design.srp.Employee;
import ru.job4j.design.srp.TypeReport;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;

public class ReportAccounting implements TypeReport {

    @Override
    public String textReport(List<Employee> workers, SimpleDateFormat dateFormat) {
        StringBuilder text = new StringBuilder();
        text.append("Name; Hired; Fired; Salary;")
                .append(System.lineSeparator());
        for (Employee employee : workers) {
            text.append(employee.getName()).append(";")
                    .append(dateFormat.format(employee.getHired().getTime())).append(";")
                    .append(dateFormat.format(employee.getFired().getTime())).append(";")
                    .append(Math.round(employee.getSalary())).append(";")
                    .append(System.lineSeparator());
        }
        return text.toString();
    }
}
