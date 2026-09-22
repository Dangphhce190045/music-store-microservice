package vn.edu.fpt.mss.dto.response;

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
public class EmployeeResponse {

    private Integer employeeId;
    private String lastName;
    private String firstName;
    private String title;
    private Integer reportsToId;
    private String reportsToName;
    private LocalDateTime birthDate;
    private LocalDateTime hireDate;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phone;
    private String fax;
    private String email;
}
