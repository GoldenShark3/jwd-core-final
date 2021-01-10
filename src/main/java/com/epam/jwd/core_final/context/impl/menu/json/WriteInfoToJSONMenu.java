package com.epam.jwd.core_final.context.impl.menu.json;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.context.impl.menu.NassaApplicationMenu;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.service.impl.SimpleCrewMemberService;
import com.epam.jwd.core_final.service.impl.SimpleMissionService;
import com.epam.jwd.core_final.service.impl.SimpleSpaceshipService;
import com.epam.jwd.core_final.util.ConsoleColors;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class WriteInfoToJSONMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(WriteInfoToJSONMenu.class);
    private static final NassaApplicationMenu NASSA_MENU = NassaApplicationMenu.getInstance();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(SerializationFeature.INDENT_OUTPUT, true);

    private WriteInfoToJSONMenu() {
    }

    public static void menu() {
        System.out.println("----------------------------");
        System.out.println("Write info about:");
        System.out.println("1) Crew members");
        System.out.println("2) Spaceships");
        System.out.println("3) Missions");
        System.out.println("4) Back to menu");
        handleUserInput(ApplicationMenu.receiveUserChoose());
    }

    private static void handleUserInput(int choose) {
        String outputRootDir = PropertyReaderUtil.getProperties().getProperty("outputRootDir");
        switch (choose) {
            case 1:
                outputCrewMembersToJSON(outputRootDir);
                break;
            case 2:
                outputSpaceshipsToJSON(outputRootDir);
                break;
            case 3:
                outputMissionsToJSON(outputRootDir);
                break;
            case 4:
                NASSA_MENU.printAvailableOptions();
                break;
            default:
                System.out.println(ConsoleColors.RED_BOLD + "It seems you made a mistake. " +
                        "Check given menu and enter value: " + ConsoleColors.RESET);
                break;
        }
        menu();
    }

    private static void outputCrewMembersToJSON(String outputRootDir) {
        Collection<CrewMember> crewMembersList = SimpleCrewMemberService.INSTANCE.findAllCrewMembers();
        File outputFile = new File("src" + File.separator + "main" + File.separator
                + "resources" + File.separator + outputRootDir + File.separator + "crewMembers.json");
        try {
            OBJECT_MAPPER.writeValue(outputFile, crewMembersList);
        } catch (IOException e) {
            LOGGER.error("Problem with output info about crew members. Check for the output folder: " + outputRootDir);
        }
        LOGGER.info("Output info about crew members to " + outputFile.getName());
    }

    private static void outputSpaceshipsToJSON(String outputRootDir) {
        Collection<Spaceship> spaceshipsList = SimpleSpaceshipService.INSTANCE.findAllSpaceships();
        File outputFile = new File("src" + File.separator + "main" + File.separator
                + "resources" + File.separator + outputRootDir + File.separator + "spaceships.json");
        try {
            OBJECT_MAPPER.writeValue(outputFile, spaceshipsList);
        } catch (IOException e) {
            LOGGER.error("Problem with output info about spaceships. Check for the output folder: " + outputRootDir);
        }
        LOGGER.info("Output info about spaceships to " + outputFile.getName());
    }

    private static void outputMissionsToJSON(String outputRootDir) {
        Collection<FlightMission> missionsList = SimpleMissionService.INSTANCE.findAllMissions();
        for (FlightMission mission : missionsList) {
            try {
                OBJECT_MAPPER.writeValue(new File("src" + File.separator + "main"
                        + File.separator + "resources" + File.separator + outputRootDir
                        + File.separator + mission.getName() + ".json"), mission);
            } catch (IOException e) {
                LOGGER.error("Problem with output info about all missions. Check for the output folder: " + outputRootDir);
            }
        }
        LOGGER.info("Output info about missions to " + outputRootDir);
    }


}
