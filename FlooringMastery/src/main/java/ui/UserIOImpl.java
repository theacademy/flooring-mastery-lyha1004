package ui;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserIOImpl implements UserIO{
    Scanner scanner = new Scanner(System.in);

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public double readDouble(String prompt) {
        System.out.println(prompt);
        return scanner.nextDouble();
    }

    @Override
    public int readInt(String prompt) {
        System.out.println(prompt);
        return scanner.nextInt();
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    @Override
    public LocalDate readLocalDate(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        System.out.println(prompt);
        String dateString = scanner.nextLine();
        return LocalDate.parse(dateString, formatter);
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine();
        return new BigDecimal(input);
    }


}
