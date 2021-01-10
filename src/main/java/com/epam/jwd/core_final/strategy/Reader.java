package com.epam.jwd.core_final.strategy;

import com.epam.jwd.core_final.strategy.api.ReadFileStrategy;
import java.io.FileNotFoundException;

public class Reader {
    private ReadFileStrategy readFileStrategy;
    public static final Reader INSTANCE = new Reader();

    private Reader() {
    }

    public void setReadFileStrategy(ReadFileStrategy readFileStrategy) {
        this.readFileStrategy = readFileStrategy;
    }

    public void readFile(String rootDir, String fileName) throws FileNotFoundException {
        readFileStrategy.readFile(rootDir, fileName);
    }

}
