package fr.univ_lyon1.info.m1.microblog.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test class for the User class.
 */
public class UserTest {

    /**
     * Test of User creation.
     */
    @Test
    public void testUserCreation() {
        User user = new User("testUser");
        assertNotNull(user);
        assertEquals("testUser", user.getId());
    }


    /**
     * Test of User equality.
     */
    @Test
    public void testUserEquality() {
        User user1 = new User("user1");
        User user2 = new User("user1");
        User user3 = new User("user3");

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
    }
}
