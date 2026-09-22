package vn.edu.fpt.mss.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequest {

    @NotBlank(message = "Last name is required")
    @Size(max = 20, message = "Last name must not exceed 20 characters")
    private String lastName;

    @NotBlank(message = "First name is required")
    @Size(max = 20, message = "First name must not exceed 20 characters")
    private String firstName;

    @Size(max = 30, message = "Title must not exceed 30 characters")
    private String title;

    private Integer reportsToId;

    private LocalDateTime birthDate;

    private LocalDateTime hireDate;

    @Size(max = 70)
    private String address;

    @Size(max = 40)
    private String city;

    @Size(max = 40)
    private String state;

    @Size(max = 40)
    private String country;

    @Size(max = 10)
    private String postalCode;

    @Size(max = 24)
    private String phone;

    @Size(max = 24)
    private String fax;

    @Size(max = 60)
    private String email;
}
