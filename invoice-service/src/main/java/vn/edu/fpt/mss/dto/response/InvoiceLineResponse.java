package vn.edu.fpt.mss.dto.response;

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
public class InvoiceLineResponse {

    private Integer invoiceLineId;
    private Integer invoiceId;
    private Integer trackId;
    private String trackName;
    private BigDecimal unitPrice;
    private Integer quantity;
    private BigDecimal subTotal;
}
