package com.epam.jwd.core_final.strategy.impl;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.DuplicateEntityException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.service.impl.SimpleSpaceshipService;
import com.epam.jwd.core_final.strategy.api.ReadFileStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SpaceshipsFileReader implements ReadFileStrategy {
    private static SpaceshipsFileReader instance;
    private final SimpleSpaceshipService SPACESHIP_SERVICE = SimpleSpaceshipService.INSTANCE;
    private final SpaceshipFactory SPACESHIP_FACTORY = SpaceshipFactory.INSTANCE;
    private final Logger LOGGER = LoggerFactory.getLogger(SpaceshipsFileReader.class);

    private SpaceshipsFileReader() {
    }

    public static SpaceshipsFileReader getInstance() {
        if (instance == null) {
            instance = new SpaceshipsFileReader();
        }
        return instance;
    }

    @Override
    public void readFile(String rootDir, String fileName) throws FileNotFoundException {
        String path = "src" + File.separator + "main" + File.separator
                + "resources" + File.separator + rootDir + File.separator + fileName;
        Scanner scanner = new Scanner(new File(path));
        parseFile(scanner);
    }

    private void parseFile(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            if (nextLine.charAt(0) != '#') {
                createSpaceship(nextLine.split(";"));
            }
        }
    }

    private Map<Role, Short> parseLineToMap(String line) {
        Map<Role, Short> crew = new HashMap<>();
        line = line.substring(1, line.length() - 1);
        for (String pairInLine : line.split(",")) {
            String[] numOfRole = pairInLine.split(":");
            crew.put(Role.resolveRoleById(Integer.parseInt(numOfRole[0])),
                    Short.parseShort(numOfRole[1]));
        }
        return crew;
    }

    private void createSpaceship(String[] spaceshipData) {
        try {
            Spaceship spaceship = SPACESHIP_FACTORY.create(
                    spaceshipData[0],
                    Long.parseLong(spaceshipData[1]),
                    parseLineToMap(spaceshipData[2]));

            SPACESHIP_SERVICE.createSpaceship(spaceship);
        } catch (DuplicateEntityException | UnknownEntityException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
