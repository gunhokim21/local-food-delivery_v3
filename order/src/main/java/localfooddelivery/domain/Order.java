package localfooddelivery.domain;

import localfooddelivery.domain.OrderPlaced;
import localfooddelivery.domain.OrderCanceled;
import localfooddelivery.OrderApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name="Order_table")
@Data

public class Order  {

    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    private Long orderId;
    
    private String menu;
    
    private Integer qty;
    
    private String status;
    
    private String userId;
    
    private String address;

    private Integer price;


    @PostPersist
    public void onPostPersist(){

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.


        localfooddelivery.external.Payment payment = new localfooddelivery.external.Payment();
        // mappings goes here

        if (price.equals(null)) {
            price = 0;
        }
        
        payment.setOrderId(orderId);
        payment.setAmount(price * qty);
        payment.setStatus(status);

        OrderApplication.applicationContext.getBean(localfooddelivery.external.PaymentService.class)
            .approvePayment(payment);


        OrderPlaced orderPlaced = new OrderPlaced(this);
        orderPlaced.publishAfterCommit();

    }
    @PrePersist
    public void onPrePersist(){

        this.status = "주문됨";

        // OrderCanceled orderCanceled = new OrderCanceled(this);
        // orderCanceled.publishAfterCommit();

    }
    @PreRemove
    public void onPreRemove(){
        OrderCanceled orderCancelled = new OrderCanceled(this);
        orderCancelled.publishAfterCommit();
    }

    public static OrderRepository repository(){
        OrderRepository orderRepository = OrderApplication.applicationContext.getBean(OrderRepository.class);
        return orderRepository;
    }

    public static void orderStatusChange(DeliveryStarted deliveryStarted){

        /** Example 1:  new item 
        Order order = new Order();
        repository().save(order);

        */

        /** Example 2:  finding and process
        
        repository().findById(deliveryStarted.get???()).ifPresent(order->{
            
            order // do something
            repository().save(order);


         });
        */

        repository().findById(deliveryStarted.getOrderId()).ifPresent(order->{
            
            order.setStatus(deliveryStarted.getStatus());
            repository().save(order);

         });
    }

    @PostLoad
    public void makeDelay(){
        // For Circuit Breaker Test
        // try {
        //     Thread.currentThread().sleep((long) (400 + Math.random() * 220));
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
    }

}
