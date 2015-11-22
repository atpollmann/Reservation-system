package cl.usach.ingesoft.agendator.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EntityAccessorsTest {

    private UserEntity user;

    @Before
    public void setUp() {
        user = new UserEntity();
    }

    @Test
    public void testUserEntity() {
        assertNotNull(user);

        // id
        assertNull(user.getId());
        Integer id = 8;
        user.setId(id);
        assertEquals(id, user.getId());

        // password
        assertNull(user.getPassword());
        user.setPassword("foo");
        assertEquals("foo", user.getPassword());

        // username
        assertNull(user.getUsername());
        user.setUsername("bar");
        assertEquals("bar", user.getUsername());
    }

}
