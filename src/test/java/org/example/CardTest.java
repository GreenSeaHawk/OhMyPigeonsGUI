package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    private Card card;
    private boolean actionExecuted;

    @BeforeEach
    void setUp() {
        actionExecuted = false;
        card = new Card(
                "AHHHH!!",
                "All other players return 1 pigeon to the flock",
                () -> actionExecuted = true // mock action
        );
    }

    @Test
    void testGetName() {
        assertEquals("AHHHH!!", card.getName());
    }

    @Test
    void testGetDescription() {
        assertEquals("All other players return 1 pigeon to the flock", card.getDescription());
    }

    @Test
    void testPerformActionRunsAction() {
        assertFalse(actionExecuted, "Action should not be executed before calling performAction()");
        card.performAction();
        assertTrue(actionExecuted, "Action should have been executed after calling performAction()");
    }


}
