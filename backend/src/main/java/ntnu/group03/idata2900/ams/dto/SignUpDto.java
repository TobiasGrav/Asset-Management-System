package ntnu.group03.idata2900.ams.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents data from user needed to sign up
 */
@Setter
@Getter
public class SignUpDto {

    private String companyId;
    private String groupId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;

    /**
     * Creates a new instance of SignUpDto
     *
     * @param companyId    companyId   of user
     * @param groupId      groupId     of user
     * @param firstName    firstName   of user
     * @param lastName     lastName    of user
     * @param email        email       of user
     * @param password     password    of user
     * @param phoneNumber  phoneNumber of user
     */
    public SignUpDto(String companyId, String groupId, String firstName, String lastName, String email, String password, String phoneNumber) {
        this.companyId = companyId;
        this.groupId = groupId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

}
