package com.epam.jwd.core_final.context.impl.menu.update;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.service.impl.SimpleCrewMemberService;
import com.epam.jwd.core_final.util.ConsoleColors;
import java.util.Optional;

class UpdateCrewMemberMenu {
    private final static SimpleCrewMemberService CREW_MEMBER_SERVICE = SimpleCrewMemberService.INSTANCE;

    public static void menu() {
        System.out.println("----------------------------");
        System.out.println("Enter ID of Crew Member you want to update: ");
        int id = ApplicationMenu.receiveUserChoose();
        Optional<CrewMember> optionalCrewMember = CREW_MEMBER_SERVICE.findCrewMemberByCriteria(
                CrewMemberCriteria.builder().withId(id).build());

        if (!optionalCrewMember.isPresent()) {
            System.out.println("User with id = " + id + " - not found, please check your input and try again");
            menu();
        } else {
            updateCrewMemberMenu(optionalCrewMember.get());
        }
        UpdateEntitiesMenu.menu();
    }

    private static void updateCrewMemberMenu(CrewMember crewMember) {
        System.out.println("Choose property to update: \n" +
                "1) Role \n" +
                "2) Rank");

        int choose = ApplicationMenu.receiveUserChoose();
        switch (choose) {
            case 1:
                updateCrewMemberRole(crewMember);
                break;
            case 2:
                updateCrewMemberRank(crewMember);
                break;
            default:
                System.out.println(ConsoleColors.RED_BOLD + "It seems you made a mistake. " +
                        "Check given menu and try again" + ConsoleColors.RESET);
                break;
        }
        UpdateEntitiesMenu.menu();
    }

    private static void updateCrewMemberRole(CrewMember crewMember) {
        System.out.println("Choose new role: \n" +
                " 1) MISSION_SPECIALIST\n" +
                " 2) FLIGHT_ENGINEER\n" +
                " 3) PILOT\n" +
                " 4) COMMANDER");

        Role newCrewMemberRole = Role.resolveRoleById(ApplicationMenu.receiveRankOrRoleId());
        crewMember.setRole(newCrewMemberRole);
        CREW_MEMBER_SERVICE.updateCrewMemberDetails(crewMember);
    }

    private static void updateCrewMemberRank(CrewMember crewMember) {
        System.out.println("Choose new rank: \n" +
                " 1) TRAINEE\n" +
                " 2) SECOND_OFFICER\n" +
                " 3) FIRST_OFFICER\n" +
                " 4) CAPTAIN");
        Rank newCrewMemberRank = Rank.resolveRankById(ApplicationMenu.receiveRankOrRoleId());
        crewMember.setRank(newCrewMemberRank);
        CREW_MEMBER_SERVICE.updateCrewMemberDetails(crewMember);
    }
}
