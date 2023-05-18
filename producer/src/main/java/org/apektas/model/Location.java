package org.apektas.model;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    String carId;
    Long timestamp;
    Integer distance;
    
}
