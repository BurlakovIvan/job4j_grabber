package ru.job4j.ood.ocp;

/*
Здесь без изменения метода мы не сможем добавить новые приветствия
 */
public class Greet {
    private String formality;

    public void printGreet() {
        if ("formal".equals(this.formality)) {
            System.out.println("Добрый день");
        } else if ("casual".equals(formality)) {
            System.out.println("Как дела?");
        } else {
            System.out.println("Привет.");
        }
    }

    public Greet(String formality) {
        this.formality = formality;
    }

    public static void main(String[] args) {
        Greet greet = new Greet("casual");
        greet.printGreet();
    }

}
