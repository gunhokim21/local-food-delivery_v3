package localfooddelivery.domain;

import localfooddelivery.domain.*;
import localfooddelivery.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class DeliveryStarted extends AbstractEvent {

    private Long orderId;
    private String userid;
    private String menu;
    private String address;
    private String qty;
    private String status;

    public DeliveryStarted(Store aggregate){
        super(aggregate);
    }
    public DeliveryStarted(){
        super();
    }
}
