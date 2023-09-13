package com.pinsoft.mobilbank.domain.user.impl;

import com.pinsoft.mobilbank.library.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = User.TABLE)
public class User extends AbstractEntity {
    public static final String TABLE="usr";
    private static final String COL_FIRSTS_NAME ="firsts-name";
    private static final String COL_LAST_NAME="last-name";
    private static final String COL_EMAIL="email";
    private static final String COL_PASSWORD="password";
    private static final String COL_STATUS="status";
    private static final String COL_ROLE="role";
    private static final String COL_AMOUNT="amount";

    @Column(name = COL_FIRSTS_NAME)
    private String firstName;
    @Column(name = COL_LAST_NAME)
    private String lastName;
    @Column(name = COL_EMAIL,nullable = false,unique = true)
    private String email;
    @Column(name = COL_PASSWORD,nullable = false)
    private String password;
    @Column(name = COL_STATUS)
    private Boolean status;
    @Column(name = COL_AMOUNT)
    private Double amount;
    @Column(name = COL_ROLE)
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @ManyToMany
    @JoinTable(name = "user_friends",
            joinColumns = @JoinColumn(name = "usr_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<User> friends;





}
