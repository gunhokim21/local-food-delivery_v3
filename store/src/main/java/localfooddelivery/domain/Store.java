package localfooddelivery.domain;

import localfooddelivery.domain.DeliveryStarted;
import localfooddelivery.StoreApplication;
import javax.persistence.*;
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
    
    
    
    
    
    private String qty;
    
    
    
    
    
    private String status;

    @PostPersist
    public void onPostPersist(){


        DeliveryStarted deliveryStarted = new DeliveryStarted(this);
        deliveryStarted.publishAfterCommit();

    }
    @PreUpdate
    public void onPreUpdate(){
        // Get request from Order
        //localfooddelivery.external.Order order =
        //    Application.applicationContext.getBean(localfooddelivery.external.OrderService.class)
        //    .getOrder(/** mapping value needed */);

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

        
    }


}
