package org.zlatenov.accountmanaging.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

/**
 * @author Angel Zlatenov
 */

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity
public class User extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate birthDate;
}
