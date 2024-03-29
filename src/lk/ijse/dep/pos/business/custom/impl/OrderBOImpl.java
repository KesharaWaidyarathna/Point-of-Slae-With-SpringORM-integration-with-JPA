package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.OrderBO;
import lk.ijse.dep.pos.dao.custom.*;
import lk.ijse.dep.pos.dto.OrderDTO;
import lk.ijse.dep.pos.dto.OrderDTO2;
import lk.ijse.dep.pos.dto.OrderDetailDTO;
import lk.ijse.dep.pos.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class OrderBOImpl implements OrderBO {

    @Autowired
    private OrderDAO orderDAO ;
    @Autowired
    private OrderDetailDAO orderDetailDAO ;
    @Autowired
    private ItemDAO itemDAO ;
    @Autowired
    private QueryDAO queryDAO ;
    @Autowired
    private CustomerDAO customerDAO;

    @Override
    public int getLastOrderId() throws Exception {
        int lastOrderId = orderDAO.getLastOrderId();
        return lastOrderId;
    }

    @Override
    public void placeOrder(OrderDTO order) throws Exception {
        int oId = order.getId();
        orderDAO.save(new Order(oId, new java.sql.Date(new Date().getTime()),customerDAO.find(order.getCustomerId())));

        for (OrderDetailDTO orderDetail : order.getOrderDetails()) {
            orderDetailDAO.save(new OrderDetail(oId, orderDetail.getCode(),
                    orderDetail.getQty(), orderDetail.getUnitPrice()));
            Item item = itemDAO.find(orderDetail.getCode());
            item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
            itemDAO.update(item);
        }
    }

        @Override
        public List<OrderDTO2> getOrderInfo (String query) throws Exception {
            List<CustomEntity> ordersInfo = queryDAO.getOrdersInfo(query+"%");
            List<OrderDTO2> al = new ArrayList<>();
            for (CustomEntity customEntity : ordersInfo) {
                al.add(new OrderDTO2(customEntity.getOrderId(), customEntity.getOrderDate(), customEntity.getCustomerId(), customEntity.getCustomerName(), customEntity.getOrderTotal()));
            }
            return al;
        }
    }
