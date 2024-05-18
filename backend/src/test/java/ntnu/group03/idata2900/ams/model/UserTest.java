package ntnu.group03.idata2900.ams.model;

import ntnu.group03.idata2900.ams.dto.SignUpDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private SignUpDto signUpDto;

    @BeforeEach
    public void setUp() {
        user = new User("Test", "User", "Test.User@Test.com", "Testing", "123456789", LocalDateTime.now());

        Set<Site> sites = new LinkedHashSet<>();
        Set<ServiceCompleted> servicesCompleted = new LinkedHashSet<>();
        Set<Role> roles = new LinkedHashSet<>();
        Company company = new Company();
        LocalDateTime creationDate = LocalDateTime.now();

        signUpDto = new SignUpDto(
                "Test", "User", "Test.User@Test.com", "Testing", "123456789", company,
                creationDate, true, sites, servicesCompleted, roles, 2, "USER"
        );
    }

    @Test
    void testUserConstructorWithParameters() {
        assertEquals("Test", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertEquals("Test.User@Test.com", user.getEmail());
        assertEquals("Testing", user.getPassword());
        assertEquals("123456789", user.getPhoneNumber());
        assertNotNull(user.getCreationDate());
        assertTrue(user.isActive());
    }

    @Test
    void testUserConstructorWithSignUpDto() {
        User newUser = new User(signUpDto);

        assertEquals("Test", newUser.getFirstName());
        assertEquals("User", newUser.getLastName());
        assertEquals("Test.User@Test.com", newUser.getEmail());
        assertEquals("Testing", newUser.getPassword());
        assertEquals("123456789", newUser.getPhoneNumber());
        assertNotNull(newUser.getCreationDate());
        assertTrue(newUser.isActive());
        assertNotNull(newUser.getCompany());
        assertNotNull(newUser.getServicesCompleted());
        assertEquals(2, newUser.getId());
    }

    @Test
    void testDefaultConstructor() {
        User defaultUser = new User();
        assertNull(defaultUser.getFirstName());
        assertNull(defaultUser.getLastName());
        assertNull(defaultUser.getEmail());
        assertNull(defaultUser.getPassword());
        assertNull(defaultUser.getPhoneNumber());
        assertNull(defaultUser.getCreationDate());
        assertFalse(defaultUser.isActive());
    }

    @Test
    void testSettersAndGetters() {

        user.setFirstName("Test");
        assertEquals("Test", user.getFirstName());

        user.setLastName("Testing");
        assertEquals("Testing", user.getLastName());

        user.setEmail("Test.Testing@example.com");
        assertEquals("Test.Testing@example.com", user.getEmail());

        user.setPassword("TestingPassword");
        assertEquals("TestingPassword", user.getPassword());

        user.setPhoneNumber("98899889");
        assertEquals("98899889", user.getPhoneNumber());

        LocalDateTime now = LocalDateTime.now();
        user.setCreationDate(now);
        assertEquals(now, user.getCreationDate());

        user.setActive(false);
        assertFalse(user.isActive());

        Set<Role> roles = new LinkedHashSet<>();
        user.setRoles(roles);
        assertEquals(roles, user.getRoles());

        Set<Site> sites = new LinkedHashSet<>();
        user.setSites(sites);
        assertEquals(sites, user.getSites());

        Set<ServiceCompleted> servicesCompleted = new LinkedHashSet<>();
        user.setServicesCompleted(servicesCompleted);
        assertEquals(servicesCompleted, user.getServicesCompleted());

        Company company = new Company();
        user.setCompany(company);
        assertEquals(company, user.getCompany());
    }

    // Tests for SignUpDto class which is used with the User class to encapsulate data
    @Test
    void testSignUpDtoConstructorWithParameters() {
        assertEquals("Test", signUpDto.getFirstName());
        assertEquals("User", signUpDto.getLastName());
        assertEquals("Test.User@Test.com", signUpDto.getEmail());
        assertEquals("Testing", signUpDto.getPassword());
        assertEquals("123456789", signUpDto.getPhoneNumber());
        assertEquals(LocalDateTime.now().getYear(), signUpDto.getCreationDate().getYear());
        assertTrue(signUpDto.isActive());
        assertNotNull(signUpDto.getCompany());
        assertNotNull(signUpDto.getSites());
        assertNotNull(signUpDto.getServicesCompleted());
        assertNotNull(signUpDto.getRoles());
        assertEquals(2, signUpDto.getId());
        assertEquals("USER", signUpDto.getRole());
    }

    @Test
    void testSignUpDtoDefaultConstructor() {
        SignUpDto signUpDto = new SignUpDto();

        assertNull(signUpDto.getFirstName());
        assertNull(signUpDto.getLastName());
        assertNull(signUpDto.getEmail());
        assertNull(signUpDto.getPassword());
        assertNull(signUpDto.getPhoneNumber());
        assertNull(signUpDto.getCompany());
        assertNull(signUpDto.getCreationDate());
        assertFalse(signUpDto.isActive());
        assertNull(signUpDto.getSites());
        assertNull(signUpDto.getServicesCompleted());
        assertNull(signUpDto.getRoles());
        assertEquals(0, signUpDto.getId());
        assertNull(signUpDto.getRole());
    }

    @Test
    void testSignUpDtoSettersAndGetters() {
        SignUpDto signUpDto = new SignUpDto();

        signUpDto.setFirstName("Test");
        assertEquals("Test", signUpDto.getFirstName());

        signUpDto.setLastName("Testing");
        assertEquals("Testing", signUpDto.getLastName());

        signUpDto.setEmail("Test.Testing@example.com");
        assertEquals("Test.Testing@example.com", signUpDto.getEmail());

        signUpDto.setPassword("TestingPassword");
        assertEquals("TestingPassword", signUpDto.getPassword());

        signUpDto.setPhoneNumber("98899889");
        assertEquals("98899889", signUpDto.getPhoneNumber());

        Company company = new Company();
        signUpDto.setCompany(company);
        assertEquals(company, signUpDto.getCompany());

        LocalDateTime now = LocalDateTime.now();
        signUpDto.setCreationDate(now);
        assertEquals(now, signUpDto.getCreationDate());

        signUpDto.setActive(true);
        assertTrue(signUpDto.isActive());

        Set<Site> sites = new LinkedHashSet<>();
        signUpDto.setSites(sites);
        assertEquals(sites, signUpDto.getSites());

        Set<ServiceCompleted> servicesCompleted = new LinkedHashSet<>();
        signUpDto.setServicesCompleted(servicesCompleted);
        assertEquals(servicesCompleted, signUpDto.getServicesCompleted());

        Set<Role> roles = new LinkedHashSet<>();
        signUpDto.setRoles(roles);
        assertEquals(roles, signUpDto.getRoles());

        signUpDto.setId(2);
        assertEquals(2, signUpDto.getId());

        signUpDto.setRole("USER");
        assertEquals("USER", signUpDto.getRole());
    }
}