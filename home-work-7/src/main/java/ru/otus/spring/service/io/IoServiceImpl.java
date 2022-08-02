package ru.otus.spring.service.io;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class IoServiceImpl implements IoService {
    private final PrintStream output;
    private final Scanner input;

    public IoServiceImpl(InputStream inputStream, PrintStream outputStream) {
        this.output = outputStream;
        input = new Scanner(inputStream);
    }

    @Override
    public int readIntWithPrompts(String... prompts) {
        Arrays.stream(prompts)
                .forEach(this::printString);
        return Integer.parseInt(input.nextLine());
    }

    @Override
    public String readStringWithPrompts(String... prompts) {
        Arrays.stream(prompts)
                .forEach(this::printString);
        return input.nextLine();
    }

    @Override
    public void printString(String string) {
        output.println(string);
    }
}
