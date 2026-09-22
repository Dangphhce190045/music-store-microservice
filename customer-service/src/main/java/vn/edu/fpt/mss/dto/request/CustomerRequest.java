package vn.edu.fpt.mss.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class CustomerRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 40, message = "First name must not exceed 40 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 20, message = "Last name must not exceed 20 characters")
    private String lastName;

    @Size(max = 80, message = "Company must not exceed 80 characters")
    private String company;

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

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 60, message = "Email must not exceed 60 characters")
    private String email;

    private Integer supportRepId;
}
