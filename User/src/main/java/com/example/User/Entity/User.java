package com.example.User.Entity;

import com.example.User.enums.Active;
import com.example.User.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", unique = true, nullable = false, updatable = false)
    private Long userId;
    private Long id;
    @Column(unique = true, nullable = false, updatable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    @NonNull
    @Column(name = "Phone")
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @Enumerated(EnumType.STRING)
    private Active active;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
    private Date verifiedAt;
    private boolean isEnabled;
    private String confirmationToken;
    private Date resetPasswordTokenExpiryDate;
    private String resetPasswordToken;
    //@ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
   // private Collection<UserRole> roles = new ArrayList<>();

    @ElementCollection(targetClass = UserRole.class)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

}
