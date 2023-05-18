package org.apektas.model;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@Builder
public class User {
    String name;
    String department;
    Long salary;
}
