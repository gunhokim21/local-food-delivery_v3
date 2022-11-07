package localfooddelivery.external;

import lombok.Data;
import java.util.Date;
@Data
public class Order {

    private Long orderId;
    private String menu;
    private Integer qty;
    private String status;
    private String userId;
    private String address;
}


