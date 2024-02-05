package com.BagaBikes.Baga_bike_rent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bikes")
public class Bike {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false, updatable = false)
        private Long id;

        @NotNull(message = "Missing required field model")
        @Column(name = "model", nullable = false)
        private String model;

        @NotNull(message = "Missing required field brand")
        @Column(name = "brand", nullable = false)
        private String brand;

        @NotNull(message = "Missing required field color")
        @Column(name = "color", nullable = false)
        private String color;

}
