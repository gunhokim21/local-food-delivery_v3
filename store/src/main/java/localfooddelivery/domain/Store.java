package localfooddelivery.domain;

import localfooddelivery.domain.DeliveryStarted;
import localfooddelivery.external.Order;
import localfooddelivery.external.OrderService;
import localfooddelivery.StoreApplication;
import javax.persistence.*;

import com.fasterxml.jackson.databind.Module.SetupContext;

import javafx.application.Application;

import java.util.List;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name="Store_table")
@Data

public class Store  {

    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    private Long storeOrderId;
    
    private Long orderId;
    
    private String userid;
    
    private String menu;
    
    private String address;
    
    private Integer qty;
    
    private String status;

    @PostPersist
    public void onPostPersist(){

    }

    @PrePersist
    public void onPrePersist() {
        // Get request from Inventory
    }


    @PreUpdate
    public void onPreUpdate(){
        // Get request from Order
        //localfooddelivery.external.Order order =
        //    Application.applicationContext.getBean(localfooddelivery.external.OrderService.class)
        //    .getOrder(/** mapping value needed */);

  
        if (status.equals("배달시작")) {
            setStatus("배달시작됨");
  
        } else if ( status.equals("취소됨") ) {
            
        
        } else {
            Order order =
                StoreApplication.applicationContext.getBean(OrderService.class)
                .getOrder(getStoreOrderId());

            if(order.getStatus().equals("주문됨")) {
                setUserid(order.getUserId());
                setMenu(order.getMenu());
                setAddress(order.getAddress());
                setQty(order.getQty());
                setOrderId(getStoreOrderId());
                setStatus("조리시작됨");
                // For Circuit Breaker 
                // setStatus("주문됨");
            } else if (order.getStatus().equals("주문상태조회오류")) {
                // for fillback 
                setStatus("주문상태조회오류");
            } else 
            throw new RuntimeException("Invalid Order!");
        }

    }

    @PostUpdate
    public void onPostUpdate(){
        DeliveryStarted deliveryStarted = new DeliveryStarted(this);
        deliveryStarted.publishAfterCommit();
    }

    public static StoreRepository repository(){
        StoreRepository storeRepository = StoreApplication.applicationContext.getBean(StoreRepository.class);
        return storeRepository;
    }


    public static void orderReceive(PaymentApproved paymentApproved){

        /** Example 1:  new item 
        Store store = new Store();
        repository().save(store);

        */

        /** Example 2:  finding and process
        
        repository().findById(paymentApproved.get???()).ifPresent(store->{
            
            store // do something
            repository().save(store);


         });
        */
        Store store = new Store();

        store.setOrderId(paymentApproved.getOrderId());
        store.setStatus(paymentApproved.getStatus());

        repository().save(store);
        
    }
    public static void orderCancel(PaymentCanceled paymentCanceled){

        /** Example 1:  new item 
        Store store = new Store();
        repository().save(store);

        */

        /** Example 2:  finding and process
        
        repository().findById(paymentCanceled.get???()).ifPresent(store->{
            
            store // do something
            repository().save(store);


         });
        */

        // modified 
        repository().findById(paymentCanceled.getOrderId()).ifPresent(store->{
            store.setStatus("취소됨");                
            repository().save(store);        

//            repository().delete(order);
        });    
    }


}
