package rondos.xdev.practicetask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Product> products;
    @NotBlank
    @Enumerated(EnumType.STRING)
    private Status status;

    //attributes used for example matching
    //TODO change transient annotation to transient keyword after debugging (this way it shows up in query results)
    @Transient
    private int productsSizeMin;
    @Transient
    private int productsSizeMax;
    @Transient
    private int productsSize;
    @Transient
    private Long idLessThan;
    @Transient
    private Long idLessThanOrEqualTo;
    @Transient
    private Long idGreaterThan;
    @Transient
    private Long idGreaterThanOrEqualTo;
}
