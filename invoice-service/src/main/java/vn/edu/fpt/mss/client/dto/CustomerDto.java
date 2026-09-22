package vn.edu.fpt.mss.client.dto;

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
public class CustomerDto {

    private Integer customerId;
    private String firstName;
    private String lastName;
    private String company;
    private String email;
}
