package rondos.xdev.practicetask.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import rondos.xdev.practicetask.model.Status;

@Entity
@Table(name = "customers")
@AllArgsConstructor
@Setter
@Getter
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Status status;
}
