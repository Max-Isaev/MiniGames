package games;

import lombok.SneakyThrows;
import org.slf4j.Logger;

import java.util.Scanner;

public class Choice {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Choice.class);

    static char getCharacterFromUser(){
        return new Scanner(System.in).next().charAt(0);
    }

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Выберите игру:\n1 - однорукий бандит, 2 - пьяница, 3 - блэкджэк");
        switch (System.in.read()) {
            case '1':
                Slot.main();
                break;
            case '2':
                Drunkard.main();
                break;
            case '3':
                BlackJack.main();
                break;
            default:
                log.info("Игры с таким номером нет!");
        }
    }
}
