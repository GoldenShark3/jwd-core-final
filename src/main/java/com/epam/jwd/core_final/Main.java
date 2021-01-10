package com.epam.jwd.core_final;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.exception.ApplicationPropertiesException;
import com.epam.jwd.core_final.exception.InvalidStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ApplicationMenu menu = null;
        try {
            menu = Application.start();
        } catch (InvalidStateException | ApplicationPropertiesException e) {
            LOGGER.error(e.getMessage(), e);
            System.exit(1);
        }
        menu.printAvailableOptions();

    }
}