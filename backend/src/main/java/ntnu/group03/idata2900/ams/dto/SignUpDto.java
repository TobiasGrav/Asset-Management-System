package ntnu.group03.idata2900.ams.dto;

/**
 * Represents data from user needed to sign up
 */
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

    public String getCompanyId() {
        return this.companyId;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
