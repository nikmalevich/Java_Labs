package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        CommandProcessor processor = new CommandProcessor("Cp866");
        processor.execute();
    }
}
