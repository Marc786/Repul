package ca.ulaval.glo4003;

public class Main {

    public static void main(String[] args) {
        Runnable runnable = new RepulServer();

        runnable.run();
    }
}
