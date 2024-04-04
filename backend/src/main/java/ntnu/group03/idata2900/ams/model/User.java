package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ntnu.group03.idata2900.ams.dto.SignUpDto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Schema(description = "Registered user on website", title = "User")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the user")
    private int id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "company_id", nullable = false, unique = false)
    @Schema(description = "company id of the user")
    private String companyId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "group_id", nullable = false, unique = false)
    @Schema(description = "group id of the user")
    private String groupId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "first_name", nullable = false, unique = false)
    @Schema(description = "First name of the user")
    private String firstName;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "last_name", nullable = false, unique = false)
    @Schema(description = "Last name of the user")
    private String lastName;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "email", nullable = false, unique = true, updatable = false)
    @Schema(description = "Email of the user")
    private String email;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "password", nullable = false, unique = false)
    @Schema(description = "Password of the user")
    private String password;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "phone_number", nullable = true, unique = false)
    @Schema(description = "phone number of the user")
    private String phoneNumber;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "creation_date", nullable = false, unique = false)
    @Schema(description = "the date user was created")
    private LocalDateTime creationDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "updated_date", nullable = true, unique = false)
    @Schema(description = "the date user was updated")
    private LocalDateTime updatedDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "active", nullable = false, unique = false, updatable = true)
    @Schema(description = "If user account is active or not")
    private boolean active;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    @Schema(description = "Roles the user has")
    private Set<Role> roles = new LinkedHashSet<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonBackReference
    @OneToMany(mappedBy = "user")
    @Schema(description = "services completed on asset")
    private Set<ServiceCompleted> servicesCompleted = new LinkedHashSet<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonBackReference
    @OneToMany(mappedBy = "user")
    @Schema(description = "sites user are connected to")
    private Set<Site> sites = new LinkedHashSet<>();


    /**
     * Constructor with parameters
     *
     * @param companyId    users company id.
     * @param groupId      users group id.
     * @param firstName    first and middle name(s) name of user.
     * @param lastName     lastname of user.
     * @param email        users email.
     * @param password     users password.
     * @param phoneNumber  users phone number.
     * @param creationDate creation date of user
     */
    public User(String companyId, String groupId, String firstName, String lastName, String email, String password, String phoneNumber, LocalDateTime creationDate) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.companyId = companyId;
        this.groupId = groupId;
        this.phoneNumber = phoneNumber;
        this.creationDate = creationDate;
        this.active = true;
    }

    /**
     * Creates a new instance of User.
     *
     * @param userInfo information provided by SignUpDto instance
     */
    public User(SignUpDto userInfo) {
        this.email = userInfo.getEmail();
        this.firstName = userInfo.getFirstName();
        this.lastName = userInfo.getLastName();
        this.password = userInfo.getPassword();
        this.companyId = userInfo.getCompanyId();
        this.groupId = userInfo.getGroupId();
        this.phoneNumber = userInfo.getPhoneNumber();
        this.creationDate = LocalDateTime.now();
        this.active = true;
    }

    public User() {

    }


}
