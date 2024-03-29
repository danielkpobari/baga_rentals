package com.BagaBikes.Baga_bike_rent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE bookings SET deleted_at = NOW() where id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted_at IS NULL")
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId", nullable = false, updatable = false)
    private Long bookingId;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Setter(AccessLevel.NONE)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Setter
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Long getCost() {
        if (startDate == null || endDate == null) {
            throw new IllegalStateException("Both start and end dates must be non-null");
        }
        return Duration.between(startDate, endDate).toHours();


    }

}
