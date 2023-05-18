package org.apektas.model;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    String carId;
    Long timestamp;
    Integer distance;

}
