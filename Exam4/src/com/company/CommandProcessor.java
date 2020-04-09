package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CommandProcessor {
    private static String getStringFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();

        return new String(data, StandardCharsets.UTF_8);
    }

    private String consoleEncoding;

    private FileWriter output1 = new FileWriter("output1.txt");
    private FileWriter output2 = new FileWriter("output2.txt");
    private FileWriter output3 = new FileWriter("logfile.txt");

    public CommandProcessor(String consoleEncoding) throws IOException {
        this.consoleEncoding = consoleEncoding;
    }

    public void execute() throws IOException {
        Context c = new Context();
        c.currentDirectory = new File("C:\\").getAbsoluteFile();

        List<String> commands = Arrays.asList(getStringFromFile("input.txt").split(";"));

        for (String command : commands) {
            if (command.equals("dir")) {
                Command cmd = new DirCommand();
                cmd.execute(c);
            }
            else {
                List<String> cd = Arrays.asList(command.split(" "));
                int i = 1;

                while (cd.get(i).equals("")) {
                    i++;
                }

                Command cmd = new CdCommand();
                output3.write(command + "\n");

                if (cmd.execute(c, cd.get(i))) {
                    output3.write("Successfully!");
                }
                else {
                    output3.write("Unsuccessfully!");
                }

                output3.write("\n");
            }
        }

        output2.close();
        output3.close();
    }

    interface Command {
        boolean execute(Context context, String... args);
    }

    class Context {
        private File currentDirectory;
    }

    class DirCommand implements Command {
        @Override
        public boolean execute(Context context, String... args) {
            printDir(context.currentDirectory);
            return true;
        }

        private void printDir(File dir) {
            try {
                output2.write(dir.getAbsoluteFile() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            File[] files = dir.listFiles();
            if (files != null) {
                for (File f : files) {
                    try {
                        output2.write(f.getName() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    class CdCommand implements Command {

        @Override
        public boolean execute(Context context, String... args) {
            if ((new File(args[0]).getAbsoluteFile()).listFiles() == null) {
                return false;
            }

            if (args[0].equals("..")) {
                context.currentDirectory = new File(context.currentDirectory.getAbsolutePath().
                        substring(0, context.currentDirectory.getAbsolutePath().lastIndexOf('\\'))).getAbsoluteFile();
            }
            else {
                context.currentDirectory = new File(args[0]).getAbsoluteFile();
            }

            return true;
        }
    }
}
