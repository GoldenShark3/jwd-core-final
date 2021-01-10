package com.epam.jwd.core_final.context.impl.menu.view;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.context.impl.menu.NassaApplicationMenu;
import com.epam.jwd.core_final.util.ConsoleColors;

public class ViewEntitiesMenu {

    public static void menu() {
        System.out.println("----------------------------");
        System.out.println("1) Show crew members");
        System.out.println("2) Show spaceships");
        System.out.println("3) Show flight missions");
        System.out.println("4) Back to menu");
        handleUserInput(ApplicationMenu.receiveUserChoose());
    }

    private static void handleUserInput(int choose) {
        switch (choose) {
            case 1:
                ShowCrewMembersMenu.menu();
                break;
            case 2:
                ShowSpaceshipsMenu.menu();
                break;
            case 3:
                ShowMissionsMenu.menu();
                break;
            case 4:
                NassaApplicationMenu.getInstance().printAvailableOptions();
                break;
            default:
                System.out.println(ConsoleColors.RED_BOLD + "It seems you made a mistake." +
                        "Check given menu and enter value:" + ConsoleColors.RESET);
                break;
        }
        menu();
    }


}