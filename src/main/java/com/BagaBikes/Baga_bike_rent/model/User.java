package com.BagaBikes.Baga_bike_rent.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted_at IS NULL")
@Table(name = "users")
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false, updatable = false)
        private Long id;

        @NotNull(message = "Missing required field username")
        @Column(name = "username", nullable = false)
        private String username;

        @Email
        @NotNull(message = "Missing required field email")
        @Column(name = "email", unique = true, nullable = false)
        private String email;

        @NotNull(message = "Missing required field password")
         private String password;

        public void setPassword(String password) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                this.password = passwordEncoder.encode(password);
        }


        @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
        @Enumerated(EnumType.STRING)
        @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
        private Set<Role> roles;

        public Set<Role> getRoles() {
                return roles;
        }

        public void setRoles(Set<Role> roles) {
                this.roles = roles;
        }
        @CreationTimestamp
        @Setter(AccessLevel.NONE)
        @Column(name = "created_at", nullable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Setter(AccessLevel.NONE)
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @Setter(AccessLevel.NONE)
        @Column(name = "deleted_at")
        private LocalDateTime deletedAt;
}