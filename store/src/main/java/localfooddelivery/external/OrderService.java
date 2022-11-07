package localfooddelivery.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name = "order", url = "${api.url.order}")
public interface OrderService {
    @RequestMapping(method= RequestMethod.GET, path="/orders/{orderId}")
    public Order getOrder(@PathVariable("orderId") Long orderId);
}

