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



    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='PaymentApproved'")
    public void whenPaymentApproved_then_CREATE_1(@Payload PaymentApproved paymentApproved) {
        try {
            if (!paymentApproved.validate()) return;

                // view 객체 생성
                OrderList orderList = new OrderList();
                // view 객체에 이벤트의 Value 를 set 함
                orderList.setOrderId(paymentApproved.getOrderId());
                orderList.setPayStatus(paymentApproved.getStatus());

                // view 레파지 토리에 save
                orderListRepository.save(orderList);


            }catch (Exception e){
                e.printStackTrace();
            }
    }



    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='OrderPlaced'")
    public void whenOrderPlaced_then_UPDATE_1 (@Payload OrderPlaced orderPlaced) {
        try {

            if (!orderPlaced.validate()) return;

            Optional<OrderList> orderListOptional = orderListRepository.findById(orderPlaced.getOrderId());

            if( orderListOptional.isPresent()) {
                 OrderList orderList = orderListOptional.get();
            // view 객체에 이벤트의 eventDirectValue 를 set 함

                orderList.setMenu(orderPlaced.getMenu());
                orderList.setQty(orderPlaced.getQty());
                orderList.setOrderStatus(orderPlaced.getStatus());
                orderList.setUserId(orderPlaced.getUserId());
                orderList.setAddress(orderPlaced.getAddress());

                // view 레파지 토리에 save
                 orderListRepository.save(orderList);
                }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='PaymentCanceled'")
    public void whenPaymentCanceled_then_UPDATE_2(@Payload PaymentCanceled paymentCanceled) {
        try {
            if (!paymentCanceled.validate()) return;
                // view 객체 조회
            Optional<OrderList> orderListOptional = orderListRepository.findById(paymentCanceled.getOrderId());

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

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='OrderCanceled'")
    public void whenOrderCanceled_then_UPDATE_3(@Payload OrderCanceled orderCanceled) {
        try {
            if (!orderCanceled.validate()) return;
                // view 객체 조회
            Optional<OrderList> orderListOptional = orderListRepository.findById(orderCanceled.getOrderId());

            if( orderListOptional.isPresent()) {
                 OrderList orderList = orderListOptional.get();
            // view 객체에 이벤트의 eventDirectValue 를 set 함
                orderList.setOrderStatus("취소됨");    
                // view 레파지 토리에 save
                 orderListRepository.save(orderList);
                }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

