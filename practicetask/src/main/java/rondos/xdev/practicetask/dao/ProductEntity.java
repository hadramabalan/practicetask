package rondos.xdev.practicetask.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import rondos.xdev.practicetask.model.ProductCategory;

import java.time.LocalDate;

@Entity
@Table(name = "products")
@AllArgsConstructor
@Setter
@Getter
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;
}
