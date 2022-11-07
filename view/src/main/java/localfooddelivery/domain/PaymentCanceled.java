package localfooddelivery.domain;

import localfooddelivery.infra.AbstractEvent;
import lombok.Data;
import java.util.*;

@Data
public class PaymentCanceled extends AbstractEvent {

    private Long payid;
    private Long orderId;
    private Double amount;
    private String status;
}
