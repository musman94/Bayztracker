package com.bayzat.bayztracker.model;

import com.bayzat.bayztracker.enumeration.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Entity
@Table(name = "\"user\"",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User extends BaseEntity implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    private String cipher;

    @Column(name = "email")
    @NotBlank
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private UserType type;

    @PrePersist
    void preInsert() {
        if (this.type == null)
            this.type = UserType.NORMAL;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", cipher='" + cipher + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getName(), user.getName()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                getType() == user.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCipher(),
                getEmail(), getType());
    }

    // Spring Boot User Details properties
    @Transient
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(type.name()));
    }

    @Transient
    @JsonIgnore
    @Override
    public String getPassword() {
        return cipher;
    }

    @Transient
    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @Transient
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Transient
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Transient
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
