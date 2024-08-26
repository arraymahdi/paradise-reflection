package com.tech.altoubli.museum.art.user;

import com.tech.altoubli.museum.art.post.Post;
import com.tech.altoubli.museum.art.role.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate birthDay;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;

    @ManyToMany
    @JoinTable(
            name = "user_following",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_user_id")
    )
    private List<User> following = new ArrayList<>();

    @ManyToMany(mappedBy = "following", fetch = EAGER)
    private List<User> followers = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_subscribing",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscribing_user_id")
    )
    private List<User> subscribing = new ArrayList<>();

    @ManyToMany(mappedBy = "subscribing", fetch = EAGER)
    private List<User> subscribers = new ArrayList<>();

    @ManyToOne(fetch = EAGER)
    private Role role;

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}
