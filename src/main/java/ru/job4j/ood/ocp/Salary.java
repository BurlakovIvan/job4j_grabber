package ru.job4j.ood.ocp;

import java.util.List;

/*
Не сможем расширить, если нам понадобится, например, указывать курс обмена не как целое число,
а как дробное число
 */
public class Salary {
    public static class Worker {
        private String name;
        private double salary;

        public Worker(String name, double salary) {
            this.name = name;
            this.salary = salary;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }
    }

    public static class SalaryDollars {
        private List<Worker> workers;
        private int exchangeRate;

        public SalaryDollars(List<Worker> workers, int exchangeRate) {
            this.workers = workers;
            this.exchangeRate = exchangeRate;
        }

        public List<Worker> exchange() {
            for (Worker worker : workers) {
                worker.setSalary(exchangeRate * worker.getSalary());
            }
            return workers;
        }
    }
}
