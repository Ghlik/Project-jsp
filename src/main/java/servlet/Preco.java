package servlet;

public class Preco {
    public Preco() {
    }

    public static double calcularValor(long diarias) {
        return (double)diarias * 120.0;
    }
}