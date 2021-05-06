package games;

import static java.lang.Math.*;

public class Slot {

    public static void main(String[] args) {

        int money = 100;
        int size = 3;
        int bet = 10;
        int win = 1000;

        while (money >= 0) {
            int[] counters = fillCounters(size);

            System.out.println("У Вас " + money + ", ставка - $10. Крутим барабаны! Розыгрыш принёс следующие результаты: "
                    + counters[0] + " " + counters[1] + " " + counters[2]);

            money -= bet;

            if (checkCounters(counters)) {
                money += win;
                System.out.println("Вы выиграли! Ваш приз - $1`000, и ваш капитал теперь составляет:" + money);
            }

        }

    }

    static int[] fillCounters (int size) {

        int[] counters = new int[size];

        for (int i = 0; i < counters.length; i++)
            counters[i] = (int) round(random() * 100) % 7;

        return counters;
    }

    static boolean checkCounters (int[] counters) {

        boolean result = true;

        for (int i = 0; i < counters.length - 1; i++) {
          result = counters[i] == counters[i+1] && result;
        }

        return result;
    }

}