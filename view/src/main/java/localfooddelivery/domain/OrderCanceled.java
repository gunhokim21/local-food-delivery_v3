package localfooddelivery.domain;

import localfooddelivery.infra.AbstractEvent;
import lombok.Data;
import java.util.*;

@Data
public class OrderCanceled  extends AbstractEvent {
    
    private Long orderId;
    private String menu;
    private Integer qty;
    private String status;
    private String userId;
    private String address;

}
