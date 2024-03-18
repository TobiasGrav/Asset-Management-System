package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Schema(description = "Registered user on website", title = "User")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the user")
    private int id;

    @Column(name = "company_id", nullable = false, unique = false)
    @Schema(description = "company id of the user")
    private int companyId;
    @Column(name = "group_id", nullable = false, unique = false)
    @Schema(description = "group id of the user")
    private int groupId;
    @Column(name = "first_name", nullable = false, unique = false)
    @Schema(description = "First name of the user")
    private String firstName;
    @Column(name = "last_name", nullable = false, unique = false)
    @Schema(description = "Last name of the user")
    private String lastName;
    @Column(name = "email", nullable = false, unique = true, updatable = false)
    @Schema(description = "Email of the user")
    private String email;
    @Column(name = "password", nullable = false, unique = false)
    @Schema(description = "Password of the user")
    private String password;
    @Column(name = "phone_number", nullable = false, unique = false)
    @Schema(description = "phone number to the user")
    private String phoneNumber;
    @Column(name = "creation_date", nullable = false, unique = false)
    @Schema(description = "the date user was created")
    private LocalDateTime creationDate;
    @Column(name = "updated_date", nullable = true, unique = false)
    @Schema(description = "the date user was updated")
    private LocalDateTime updatedDate;
    @Column(name = "active", nullable = false, unique = false, updatable = true)
    @Schema(description = "If user account is active or not")
    private boolean active;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    @Schema(description = "Roles the user has")
    private Set<Role> roles = new LinkedHashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    @Schema(description = "services completed on asset")
    private Set<ServiceCompleted> servicesCompleted = new LinkedHashSet<>();


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
    public User(int companyId, int groupId, String firstName, String lastName, String email, String password, String phoneNumber, LocalDateTime creationDate) {
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

    public User() {

    }


}
