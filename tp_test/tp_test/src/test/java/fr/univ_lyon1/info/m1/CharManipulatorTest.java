package fr.univ_lyon1.info.m1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CharManipulatorTest {

    private CharManipulator manipulator;

    @BeforeEach
    public void setUp() {
        manipulator = new CharManipulator();
    }

    @Test
    public void orderNormalString() {
        assertEquals("A", manipulator.invertOrder("A"));
        assertEquals("DCBA", manipulator.invertOrder("ABCD"));
        assertEquals("321DCBA", manipulator.invertOrder("ABCD123"));
    }

    @Test
    public void orderEmptyString() {
        assertEquals("", manipulator.invertOrder(""));
    }

    @Test
    public void caseNormalString() {
        assertEquals("a", manipulator.invertCase("A"));
        assertEquals("aBCd", manipulator.invertCase("AbcD"));
        assertEquals("aBCd123", manipulator.invertCase("AbcD123"));
    }

    @Test
    public void caseEmptyString() {
        assertEquals("", manipulator.invertCase(""));
    }

        @Test
        public void testAbcd() {
            // Given
            CharManipulator manipulator = new CharManipulator();

            // When
            String invert = manipulator.invertOrder("ABCD");

            // Then
            assertThat( invert,is("DCBA"));
        }

    @Test
    public void testRemovePattern() {
        assertEquals("cc", manipulator.removePattern("coucou", "ou"));
        assertEquals("coucou", manipulator.removePattern("coucou", ""));
        assertEquals("", manipulator.removePattern("ab", "ab"));


    }



}