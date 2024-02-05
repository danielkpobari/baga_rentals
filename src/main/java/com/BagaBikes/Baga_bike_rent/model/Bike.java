package com.BagaBikes.Baga_bike_rent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE bikes SET deleted_at = NOW() where id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted_at IS NULL")
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

//        @ToString.Exclude
//        @NotNull(message = "Missing required field user_id")
//        @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
//        @JoinColumn(name = "user_id", referencedColumnName = "id")
//        private User user;

        public boolean isAvailable(LocalDateTime startDate, LocalDateTime endDate) {
                return true;

        }
}
