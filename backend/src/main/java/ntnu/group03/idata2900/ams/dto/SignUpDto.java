package ntnu.group03.idata2900.ams.dto;

import lombok.Getter;
import lombok.Setter;
import ntnu.group03.idata2900.ams.model.Company;

/**
 * Represents data from user needed to sign up
 */
@Setter
@Getter
public class SignUpDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private Company company;

    /**
     * Creates a new instance of SignUpDto
     *
     * @param firstName    firstName   of user
     * @param lastName     lastName    of user
     * @param email        email       of user
     * @param password     password    of user
     * @param phoneNumber  phoneNumber of user
     * @param phoneNumber  company     of user
     */
    public SignUpDto(String firstName, String lastName, String email, String password, String phoneNumber, Company company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.company = company;
    }

    public SignUpDto(){

    }

}
