package rondos.xdev.practicetask.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private Status status;
}
