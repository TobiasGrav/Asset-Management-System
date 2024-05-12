package ntnu.group03.idata2900.ams.dto;

import lombok.Getter;
import lombok.Setter;
import ntnu.group03.idata2900.ams.model.Company;
import ntnu.group03.idata2900.ams.model.Role;
import ntnu.group03.idata2900.ams.model.ServiceCompleted;
import ntnu.group03.idata2900.ams.model.Site;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Represents data from user needed to sign up
 */
@Setter
@Getter
public class SignUpDto {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private Company company;
    private LocalDateTime creationDate;
    private boolean active;
    private String role;
    private Set<Site> sites;
    private Set<ServiceCompleted> servicesCompleted;
    private Set<Role> roles;

    /**
     * Creates a new instance of SignUpDto
     *
     * @param firstName          firstName          of user
     * @param lastName           lastName           of user
     * @param email              email              of user
     * @param password           password           of user
     * @param phoneNumber        phoneNumber        of user
     * @param company            company            of user
     * @param creationDate       creationDate       of user
     * @param active             active             of user
     * @param sites              sites              of user
     * @param servicesCompleted  servicesCompleted  of user
     * @param roles              roles              of user
     * @param id                 id                 of user
     * @param role               role               of user
     */
    public SignUpDto(String firstName, String lastName, String email, String password, String phoneNumber, Company company, LocalDateTime creationDate, boolean active, Set<Site> sites,
                     Set<ServiceCompleted> servicesCompleted, Set<Role> roles, int id, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.company = company;
        this.creationDate = creationDate;
        this.active = active;
        this.sites = sites;
        this.servicesCompleted = servicesCompleted;
        this.roles = roles;
        this.id = id;
        this.role = role;
    }

    public SignUpDto(){

    }

}
