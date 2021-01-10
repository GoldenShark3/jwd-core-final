package com.epam.jwd.core_final.context.impl.menu.view;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.service.impl.SimpleCrewMemberService;
import com.epam.jwd.core_final.util.ConsoleColors;
import java.util.List;
import java.util.Scanner;

class ShowCrewMembersMenu {
    private static final Scanner SCANNER = new Scanner(System.in);
    private final static SimpleCrewMemberService CREW_MEMBER_SERVICE = SimpleCrewMemberService.INSTANCE;

    public static void menu() {
        int choose;
        System.out.println("----------------------------");
        System.out.println("1) Show all crew members");
        System.out.println("2) Show crew members by criteria");
        System.out.println("3) Back");

        choose = ApplicationMenu.receiveUserChoose();

        switch (choose) {
            case 1:
                showAllCrewMembers();
                break;
            case 2:
                showCrewMembersByCriteria(CrewMemberCriteria.builder());
                break;
            case 3:
                ViewEntitiesMenu.menu();
                break;
            default:
                System.out.println(ConsoleColors.RED_BOLD + "It seems you made a mistake. " +
                        "Check given menu and enter value: " + ConsoleColors.RESET);
                break;
        }
        menu();
    }

    private static void showAllCrewMembers() {
        List<CrewMember> crewMemberList = CREW_MEMBER_SERVICE.findAllCrewMembers();
        displayCrewMembersList(crewMemberList);
    }

    private static void showCrewMembersByCriteria(CrewMemberCriteria.CrewMemberBuilder builder) {
        System.out.println("----------------------------");
        System.out.println("Choose which criteria you want to search by: \n" +
                "0) Back \n" +
                "1) Name \n" +
                "2) Rank \n" +
                "3) Role \n" +
                "4) Id \n" +
                "5) Show");

        switch (ApplicationMenu.receiveUserChoose()) {
            case 0:
                ViewEntitiesMenu.menu();
                break;
            case 1:
                System.out.println("Enter name of crew member: ");
                builder.withName(SCANNER.nextLine());
                break;
            case 2:
                builder.withRank(chooseCrewMemberRank());
                break;
            case 3:
                builder.withRole(chooseCrewMemberRole());
                break;
            case 4:
                System.out.println("Enter the id of crew member: ");
                builder.withId(SCANNER.nextInt());
                break;
            case 5:
                List<CrewMember> crewMemberList = CREW_MEMBER_SERVICE.findAllCrewMembersByCriteria(builder.build());
                if (crewMemberList == null) {
                    System.out.println(ConsoleColors.RED_BOLD + "There is not a single match for your criteria"
                            + ConsoleColors.RESET);
                } else {
                    displayCrewMembersList(crewMemberList);
                }
                break;
            default:
                System.out.println(ConsoleColors.RED_BOLD + "It seems you made a mistake. Check given menu."
                        + ConsoleColors.RESET);
                break;
        }
        showCrewMembersByCriteria(builder);
    }

    private static void displayCrewMembersList(List<CrewMember> crewMemberList) {
        for (CrewMember crewMember : crewMemberList) {
            String nameColor;
            if (!crewMember.isReadyForNextMissions()) {
                nameColor = ConsoleColors.RED_BOLD;
            } else {
                nameColor = ConsoleColors.GREEN_BOLD;
            }

            System.out.print(nameColor + "id: " + crewMember.getId() + ", " + crewMember.getName()
                    + " - " + ConsoleColors.PURPLE_BOLD + crewMember.getRole() + ", "
                    + crewMember.getRank() + ";\n" + ConsoleColors.RESET);
        }
    }

    private static Rank chooseCrewMemberRank() {
        System.out.println("Choose crew member rank: \n" +
                "1) TRAINEE\n" +
                "2) SECOND_OFFICER\n" +
                "3) FIRST_OFFICER\n" +
                "4) CAPTAIN");
        return Rank.resolveRankById(ApplicationMenu.receiveRankOrRoleId());
    }

    private static Role chooseCrewMemberRole() {
        System.out.println("Choose crew member role: \n" +
                "1) MISSION_SPECIALIST\n" +
                "2) FLIGHT_ENGINEER\n" +
                "3) PILOT\n" +
                "4) COMMANDER");
        return Role.resolveRoleById(ApplicationMenu.receiveRankOrRoleId());
    }
}