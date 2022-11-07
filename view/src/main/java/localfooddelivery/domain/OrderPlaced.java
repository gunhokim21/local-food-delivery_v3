package localfooddelivery.domain;

import localfooddelivery.infra.AbstractEvent;
import lombok.Data;
import java.util.*;

@Data
public class OrderPlaced extends AbstractEvent {

    private Long orderID;
    private String menu;
    private Integer qty;
    private String userId;
    private String address;
    private String status;
}
