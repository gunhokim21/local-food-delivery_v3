package localfooddelivery.external;

import org.springframework.stereotype.Service;

@Service
public class OrderServiceFallback implements OrderService {
    
    public Order getOrder(Long orderId){
        Order fallbackValue = new Order();
        fallbackValue.setStatus("주문상태조회오류");

        return fallbackValue;
    }
}
