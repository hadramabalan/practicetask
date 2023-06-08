package rondos.xdev.practicetask.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class Product {
    private Long id;
    private String name;
    private ProductCategory category;
    private LocalDate validFrom;
    private LocalDate validTo;
}
