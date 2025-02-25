package com.luicode.keysy.keysyservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "user_passwords")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, name="entry_name")
    private String entryName;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, name = "crypted_password")
    private String cryptedPassword;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;


}
