package ui;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface UserIO {
    void print(String msg);

    double readDouble(String prompt);

    int readInt(String prompt);

    String readString(String prompt);

    LocalDate readLocalDate(String prompt);

    BigDecimal readBigDecimal(String prompt);
}
