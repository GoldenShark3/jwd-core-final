package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.menu.json.WriteInfoToJSONMenu;
import com.epam.jwd.core_final.context.impl.menu.mission.AddNewMissionMenu;
import com.epam.jwd.core_final.context.impl.menu.update.UpdateEntitiesMenu;
import com.epam.jwd.core_final.context.impl.menu.view.ViewEntitiesMenu;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.util.ConsoleColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;

@FunctionalInterface
public interface ApplicationMenu {
    Logger LOGGER = LoggerFactory.getLogger(ApplicationMenu.class);
    Scanner SCANNER = new Scanner(System.in);

    ApplicationContext getApplicationContext();

    default void printAvailableOptions() {
        System.out.println("----------------------------");
        System.out.println("1) Create mission");
        System.out.println("2) View entities");
        System.out.println("3) Write entities to JSON");
        System.out.println("4) Update entities");
        System.out.println("5) Exit");
        handleUserInput(receiveUserChoose());
    }

    default void handleUserInput(int choose) {
        switch (choose) {
            case 1:
                AddNewMissionMenu.createNewMission();
                break;
            case 2:
                ViewEntitiesMenu.menu();
                break;
            case 3:
                WriteInfoToJSONMenu.menu();
                break;
            case 4:
                UpdateEntitiesMenu.menu();
                break;
            case 5:
                LOGGER.info("Application stop by user");
                System.exit(0);
            default:
                System.out.println(ConsoleColors.RED_BOLD + "It seems you made a mistake. " +
                        "Check given menu and input value:" + ConsoleColors.RESET);
                break;
        }
        printAvailableOptions();
    }

    static int receiveUserChoose() {
        boolean isCorrect;
        int choose = 0;
        do {
            try {
                choose = Integer.parseInt(SCANNER.nextLine());
                isCorrect = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a numeric value.\nYour choose: ");
                isCorrect = false;
            }
        } while (!isCorrect);
        return choose;
    }

    static int receiveRankOrRoleId() {
        int id;
        boolean isInputCorrect;
        do {
            id = receiveUserChoose();
            if (id > Role.values().length || id <= 0) {
                isInputCorrect = false;
                System.out.println(ConsoleColors.RED_BOLD + "You've entered wrong number, please try again: "
                        + ConsoleColors.RESET);
            } else {
                isInputCorrect = true;
            }
        } while (!isInputCorrect);
        return id;
    }
}
