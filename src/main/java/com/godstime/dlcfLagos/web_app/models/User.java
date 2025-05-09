package com.godstime.dlcfLagos.web_app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 50)
    private String firstName;
    
    @NotBlank
    @Size(max = 50)
    private String lastName;
    
    @NotBlank
    @Size(max = 50)
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(max = 50)
    private String username;

    @NotBlank
    @Size(max = 120)
    private String password;

    private String role;

    @Size(max = 20)
    private String phoneNumber;
    
    @Size(max = 100)
    private String lga;
    
    @Size(max = 100)
    private String cdsGroup;
    
    @Size(max = 200)
    private String placeOfPrimaryAssignment;
    
    @Size(max = 100)
    private String unitInFellowship;
    
    @Size(max = 100)
    private String spiritualStatus;
    
    @Size(max = 100)
    private String zone;
    
    @Size(max = 255)
    private String profilePictureUrl;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}