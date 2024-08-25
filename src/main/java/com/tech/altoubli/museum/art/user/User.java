package com.tech.altoubli.museum.art.user;

import com.tech.altoubli.museum.art.post.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDay;
    @OneToMany
    private List<Post> posts = new ArrayList<>();

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}
