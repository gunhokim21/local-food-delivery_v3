package localfooddelivery.domain;

import localfooddelivery.domain.PaymentApproved;
import localfooddelivery.domain.PaymentCanceled;
import localfooddelivery.PayApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name="Payment_table")
@Data

public class Payment  {

    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
        
    private Long payid;
    
    private Long orderId;
    
    private Integer amount;
        
    private String status;

    @PostPersist
    public void onPostPersist(){

        PaymentApproved paymentApproved = new PaymentApproved(this);
        paymentApproved.publishAfterCommit();

        // PaymentCanceled paymentCanceled = new PaymentCanceled(this);
        // paymentCanceled.publishAfterCommit();

    }

    public static PaymentRepository repository(){
        PaymentRepository paymentRepository = PayApplication.applicationContext.getBean(PaymentRepository.class);
        return paymentRepository;
    }




    public static void cancelPayment(OrderCanceled orderCanceled){

        /** Example 1:  new item 
        Payment payment = new Payment();
        repository().save(payment);

        */

        /** Example 2:  finding and process
        
        repository().findById(orderCanceled.get???()).ifPresent(payment->{
            
            payment // do something
            repository().save(payment);


         });
        */
        // modified 
        repository().findById(orderCanceled.getOrderId()).ifPresent(payment->{
            payment.setStatus("취소됨");                
            repository().save(payment);        
            // repository().delete(payment);
            
            PaymentCanceled paymentCanceled = new PaymentCanceled(payment);
            paymentCanceled.publishAfterCommit();
        });    
        
    }


}
