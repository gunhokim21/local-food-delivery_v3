package localfooddelivery.infra;

import localfooddelivery.domain.*;
import localfooddelivery.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class OrderListViewHandler {


    @Autowired
    private OrderListRepository orderListRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderPlaced_then_CREATE_1 (@Payload OrderPlaced orderPlaced) {
        try {

            if (!orderPlaced.validate()) return;

            // view 객체 생성
            OrderList orderList = new OrderList();
            // view 객체에 이벤트의 Value 를 set 함
            orderList.setOrderId(orderPlaced.getOrderId());
            orderList.setMenu(orderPlaced.getMenu());
            orderList.setQty(orderPlaced.getQty());
            orderList.setOrderStatus(orderPlaced.getStatus());
            orderList.setUserId(orderPlaced.getUserId());
            orderList.setAddress(orderPlaced.getAddress());
            // view 레파지 토리에 save
            orderListRepository.save(orderList);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenPaymentApproved_then_UPDATE_1(@Payload PaymentApproved paymentApproved) {
        try {
            if (!paymentApproved.validate()) return;
                // view 객체 조회
            Optional<OrderList> orderListOptional = orderListRepository.findByOrderId(paymentApproved.getOrderId());

            if( orderListOptional.isPresent()) {
                 OrderList orderList = orderListOptional.get();
            // view 객체에 이벤트의 eventDirectValue 를 set 함
                orderList.setPayStatus(paymentApproved.getStatus());    
                // view 레파지 토리에 save
                 orderListRepository.save(orderList);
                }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPaymentCanceled_then_UPDATE_2(@Payload PaymentCanceled paymentCanceled) {
        try {
            if (!paymentCanceled.validate()) return;
                // view 객체 조회
            Optional<OrderList> orderListOptional = orderListRepository.findByOrderId(paymentCanceled.getOrderId());

            if( orderListOptional.isPresent()) {
                 OrderList orderList = orderListOptional.get();
            // view 객체에 이벤트의 eventDirectValue 를 set 함
                orderList.setPayStatus(paymentCanceled.getStatus());    
                // view 레파지 토리에 save
                 orderListRepository.save(orderList);
                }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

