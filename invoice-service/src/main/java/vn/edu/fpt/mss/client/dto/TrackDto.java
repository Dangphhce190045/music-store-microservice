package vn.edu.fpt.mss.client.dto;

import java.math.BigDecimal;
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
public class TrackDto {

    private Integer trackId;
    private String name;
    private BigDecimal unitPrice;
    private Integer milliseconds;
}
