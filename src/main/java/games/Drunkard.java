package games;

import lombok.val;
import org.slf4j.Logger;

import static games.CardUtils.*;

public class Drunkard {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Drunkard.class);

    private static final int PLAYER_ONE = 0;
    private static final int PLAYER_TWO = 1;
    private static final int DRAW = -1;

    private static int[][] playersCards = new int[2][CARDS_TOTAL_COUNT];

    private static int[] playerCardTails = new int[]{0, 0};
    private static int[] playerCardHeads = new int[]{CARDS_TOTAL_COUNT / 2, CARDS_TOTAL_COUNT / 2};

    private static int[] takenPlayerCards = new int[2];

    private static int lastWinPlayer;

    private static int iteration = 1;

    public static void main() {

        dealCards(); // раздаем карты

        do  {

            getCards(); // вытягиваем карты

            val compareCards = compareCards( // сравниваем карты
                    getPar(takenPlayerCards[PLAYER_ONE]),
                    getPar(takenPlayerCards[PLAYER_TWO])
            );

            log.info("Итерация №{} Игрок №1 карта: {}; игрок №2 карта: {}.",
                    iteration, CardUtils.toString(takenPlayerCards[PLAYER_ONE]), CardUtils.toString(takenPlayerCards[PLAYER_TWO]));

            if (compareCards > 0) {
                log.info("Выиграл игрок 1!");
                addCard(PLAYER_ONE);
            } else if (compareCards < 0) {
                log.info("Выиграл игрок 2!");
                addCard(PLAYER_TWO);
            } else {
                log.info("Спор - каждый остаётся при своих!");
                addCard(DRAW);
            }

            log.info("У игрока №1 {} карт, у игрока №2 {} карт", getCountPlayerCard(PLAYER_ONE), getCountPlayerCard(PLAYER_TWO));

        } while (isWin());

        log.info("Выиграл {} игрок! Количество произведённых итераций: {}", lastWinPlayer == 0 ? "первый" : "второй", iteration);
    }

    private static void getCards() {
        for (int i = 0; i < playersCards.length; i++) {
            playerCardTails[i] = incrementIndex(playerCardTails[i]);
            var tail = playerCardTails[i];
            takenPlayerCards[i] = playersCards[i][tail];
        }
    }

    private static void addCard(int player) {
        if (player != -1) {
            for (int takenPlayerCard : takenPlayerCards) {
                playerCardHeads[player] = incrementIndex(playerCardHeads[player]);
                var head = playerCardHeads[player];
                playersCards[player][head] = takenPlayerCard;
            }

            lastWinPlayer = player;
        } else { // ничья
            for (int i = 0; i < takenPlayerCards.length; i++) {
                playerCardHeads[i] = incrementIndex(playerCardHeads[i]);
                var head = playerCardHeads[i];
                playersCards[i][head] = takenPlayerCards[i];
            }
        }

        iteration++;
    }

    private static int getCountPlayerCard(int player) {

        var countCardPlayer = playerCardHeads[player] - playerCardTails[player];

        if (countCardPlayer < 0) {
            countCardPlayer = playerCardHeads[player] + CARDS_TOTAL_COUNT - playerCardTails[player];
        }

        return countCardPlayer;
    }

    private static int compareCards(Par card1, Par card2) {

        if (card1.getKey() - card2.getKey() == 8) return -1;
        if (card1.getKey() - card2.getKey() == -8) return 1;

        return card1.getKey() - card2.getKey();
    }

    private static void dealCards() {

        var cards = getShaffledCards();

        var lastIndex = 0;

        for (var i = 0; i < playersCards.length; i++) {
            for (var j = lastIndex; j < CARDS_TOTAL_COUNT; j++) {
                if (j % (CARDS_TOTAL_COUNT / playersCards.length) != 0 || j == lastIndex) {
                    playersCards[i][j - lastIndex] = cards[j];
                } else {
                    lastIndex = j;
                    break;
                }
            }
        }
    }

    private static boolean isWin() {
        return getCountPlayerCard(PLAYER_ONE) != 0  && getCountPlayerCard(PLAYER_TWO) != 0;
    }

    private static int incrementIndex(int i) {
        return (i + 1) % CARDS_TOTAL_COUNT;
    }


}
