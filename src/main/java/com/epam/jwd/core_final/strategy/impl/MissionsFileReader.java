package com.epam.jwd.core_final.strategy.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.DuplicateEntityException;
import com.epam.jwd.core_final.factory.impl.MissionFactory;
import com.epam.jwd.core_final.service.impl.SimpleMissionService;
import com.epam.jwd.core_final.strategy.api.ReadFileStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class MissionsFileReader implements ReadFileStrategy {
    private static MissionsFileReader instance;
    private final MissionFactory MISSION_FACTORY = MissionFactory.INSTANCE;
    private final SimpleMissionService MISSION_SERVICE = SimpleMissionService.INSTANCE;
    private final Logger LOGGER = LoggerFactory.getLogger(MissionsFileReader.class);

    private MissionsFileReader() {
    }

    public static MissionsFileReader getInstance() {
        if (instance == null) {
            instance = new MissionsFileReader();
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
                createFlightMission(nextLine.split("[;]"));
            }
        }
        scanner.close();
    }

    private void createFlightMission(String[] args) {
        FlightMission flightMission = MISSION_FACTORY.create(args[0],
                LocalDateTime.parse(args[1], NassaContext.getInstance().receiveDateFormat()),
                LocalDateTime.parse(args[2], NassaContext.getInstance().receiveDateFormat()),
                Long.parseLong(args[3]));
        try {
            MISSION_SERVICE.createMission(flightMission);
        } catch (DuplicateEntityException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
