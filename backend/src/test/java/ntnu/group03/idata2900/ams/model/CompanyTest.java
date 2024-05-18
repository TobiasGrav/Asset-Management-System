package ntnu.group03.idata2900.ams.model;

import ntnu.group03.idata2900.ams.dto.CompanyDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {

    private Company company;
    private CompanyDto companyDto;

    @BeforeEach
    public void setUp() {
        company = new Company("testName");
        companyDto = new CompanyDto("testName");
    }

    @Test
    void testCompanyConstructorWithParameters() {
        assertEquals("testName", company.getName());
    }

    @Test
    void testCompanyConstructorWithDto() {
        Company newCompany = new Company(companyDto);

        assertEquals("testName", newCompany.getName());
    }

    @Test
    void testCompanyDefaultConstructor() {
        Company defaultCompany = new Company();
        assertNull(defaultCompany.getName());
    }

    @Test
    void testCompanySettersAndGetters() {
        company.setName("newTestName");
        assertEquals("newTestName", company.getName());

        Set<Site> sites = new LinkedHashSet<>();
        company.setSites(sites);
        assertEquals(sites, company.getSites());

        Set<User> users = new LinkedHashSet<>();
        company.setUsers(users);
        assertEquals(users, company.getUsers());
    }

    @Test
    void testCompanyDtoConstructorWithParameters() {
        assertEquals("testName", companyDto.getName());
    }

    @Test
    void testCompanyDtoDefaultConstructor() {
        CompanyDto defaultCompanyDto = new CompanyDto();
        assertNull(defaultCompanyDto.getName());
    }

    @Test
    void testCompanyDtoSettersAndGetters() {
        companyDto.setName("newTestName");
        assertEquals("newTestName", companyDto.getName());
    }
}
