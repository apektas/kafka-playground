package org.apektas.model;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class User {
    String name;
    String department;
    Long salary;
}
