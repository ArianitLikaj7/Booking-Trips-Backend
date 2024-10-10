package com.bookingtrips.booking_trips_backend.dto;


import com.bookingtrips.booking_trips_backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String firstName;
    private String lastName;
    private String username;
    private Role role;
}
