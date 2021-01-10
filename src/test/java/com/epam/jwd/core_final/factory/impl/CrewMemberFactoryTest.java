package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CrewMemberFactoryTest {
    private static CrewMemberFactory crewMemberFactory;

    @BeforeClass
    public static void setUp(){
        crewMemberFactory = CrewMemberFactory.getInstance();
    }

    @Test
    public void create_returnNewCrewMember_always() {
        String crewMemberName = "Jackson";
        Role role = Role.COMMANDER;
        Rank rank = Rank.SECOND_OFFICER;

        CrewMember crewMember = crewMemberFactory.create(role, crewMemberName, rank);

        assertEquals("1", crewMember.getName());
        assertEquals(role, crewMember.getRole());
        assertEquals(rank, crewMember.getRank());
    }
}