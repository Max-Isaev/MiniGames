package games;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Par {

    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11), // Валет
    QUEEN(12), // Дама
    KING(13), // Король
    ACE(14); // Туз

    private int key;
}
