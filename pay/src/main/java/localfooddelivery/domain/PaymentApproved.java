package localfooddelivery.domain;

import localfooddelivery.domain.*;
import localfooddelivery.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class PaymentApproved extends AbstractEvent {

    private Long payid;
    private Long orderId;
    private Double amount;
    private String status;

    public PaymentApproved(Payment aggregate){
        super(aggregate);
    }
    public PaymentApproved(){
        super();
    }
}
