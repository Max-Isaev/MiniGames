package games;

import org.slf4j.Logger;

import java.io.IOException;

import static games.Choice.getCharacterFromUser;

public class BlackJack {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BlackJack.class);

    private static int[] cards; // Основная колода
    private static int cursor; // Счётчик карт основной колоды

    private static int[][] playersCards; // карты игроков. Первый индекс - номер игрока
    private static int[] playersCursors; // курсоры карт игроков. Индекс - номер игрока

    private static int[] playersMoney = {100, 100};

    private static final int MAX_VALUE = 21;
    private static final int MAX_CARDS_COUNT = 8;
    private static final int PLAYER_ONE = 0;
    private static final int PLAYER_TWO = 1;
    private static final int WIN = 10;

    public static void main() throws IOException {

        while (checkPlayersMoney()) {

            initRound();

            for (int i = 0; i < 2; i++) {
                addCard2Player(PLAYER_ONE);
            }

            while (confirm("Берём ещё?")) {
                addCard2Player(PLAYER_ONE);

                if (getFinalSum(PLAYER_ONE) == 0) {
                    break;
                }
            }

            for (int i = 0; i < 2; i++) {
                addCard2Player(PLAYER_TWO);
            }

            while (dealerLogic()) {
                addCard2Player(PLAYER_TWO);
            }

            log.info("Сумма ваших очков - {}, компьютера - {}", getSumCards(PLAYER_ONE), getSumCards(PLAYER_TWO));

            if (getFinalSum(PLAYER_ONE) == getFinalSum(PLAYER_TWO)) {
                log.info("Ничья!");
            } else if (getFinalSum(PLAYER_ONE) > getFinalSum(PLAYER_TWO)) {
                log.info("Вы выиграли раунд! Получаете 10$");
                addMoney(PLAYER_ONE);
                reduceMoney(PLAYER_TWO);
            } else {
                log.info("Вы проиграли раунд! Теряете 10$");
                addMoney(PLAYER_TWO);
                reduceMoney(PLAYER_ONE);
            }

        }

        if (playersMoney[0] > 0)
            log.info("Вы выиграли! Поздравляем!");
        else
            log.info("Вы проиграли. Соболезнуем...");

    }

    private static void initRound() {
        log.info("\nУ Вас {}$, у компьютера - {}$. Начинаем новый раунд!", playersMoney[0], playersMoney[1]);
        cards = CardUtils.getShaffledCards();
        playersCards = new int[2][MAX_CARDS_COUNT];
        playersCursors = new int[]{0, 0};
        cursor = 0;
    }

    static int getSumCards(int player) {
        var sumCard = 0;

        for (int i = 0; i < playersCursors[player]; i++) {
            sumCard += CardUtils.value(playersCards[player][i]);
        }

        return sumCard;
    }

    static int getFinalSum(int player) {
        var finalSum = getSumCards(player);
        return finalSum > MAX_VALUE ? 0 : finalSum;
    }

    private static boolean checkPlayersMoney() {
        return playersMoney[PLAYER_ONE] != 0 && playersMoney[PLAYER_TWO] != 0;
    }

    private static void addCard2Player(int player) {
        var card = cards[cursor];
        playersCards[player][playersCursors[player]] = card;
        cursor++;
        playersCursors[player]++;

        var whoPlayer = player == 0 ? "Вам" : "Компьютеру";

        log.info("{} выпала карта {}", whoPlayer, CardUtils.toString(card));
    }

    static boolean confirm(String message) {
        log.info(message + " Y - Да, {любой другой символ} - нет (Чтобы выйти из игры, нажмите Ctrl + C)");
        switch (getCharacterFromUser()) {
            case 'Y':
            case 'y':
                return true;
            default:
                return false;
        }
    }

    static boolean dealerLogic() {
        return getFinalSum(PLAYER_TWO) < 17 && getFinalSum(PLAYER_TWO) != 0;
    }

    private static void addMoney(int player) {
        playersMoney[player] += WIN;
    }

    private static void reduceMoney(int player) {
        playersMoney[player] -= WIN;
    }
}
