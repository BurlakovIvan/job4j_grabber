package ru.job4j.ood.lsp;

/*
1. Использование методов instance of, мы не можем расширить код, нам нужно его будет дописать
 */
public class Feed {

    public void feed(Animal animal) {
        if (animal instanceof Cow) {
            System.out.println("Ест траву");
        }
    }

    class Cow extends Animal {
        public Cow(boolean isFlying, int age) {
            super(isFlying, age);
        }
    }

    class Wolf extends Animal {
        public Wolf(boolean isFlying, int age) {
            super(isFlying, age);
        }
    }
}
