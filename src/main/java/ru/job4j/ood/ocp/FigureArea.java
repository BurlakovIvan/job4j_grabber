package ru.job4j.ood.ocp;

/*
В случае возникновения необходимости добавления новых фигур, и вычисления их площадей
придется добавлять новые, наследование здесь тоже не поможет
 */
public class FigureArea {

    public static class Circle {
        private double radius;

        public Circle(double radius) {
            this.radius = radius;
        }

        public double area() {
            return Math.PI * Math.pow(radius, 2);
        }
    }

    public static void main(String[] args) {
        Circle circle = new Circle(2);
        System.out.println(circle.area());
    }
}
