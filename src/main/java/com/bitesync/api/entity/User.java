package com.bitesync.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @NotEmpty(message = "username cannot be empty")
    @Column(name = "username")
    private String username;

    @NonNull
    @NotEmpty(message = "password cannot be empty")
    @Column(name = "password")
    private String password;

}
