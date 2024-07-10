package org.example.vegetableshopping.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.vegetableshopping.enums.OrderStatus;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "`order`")
@Setter
@Getter
public class Order extends Auditable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "address_shipping")
    private String addressShipping;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "payment_status")
    private Boolean paymentStatus;

    @Column(name = "payment_method")
    private Boolean paymentMethod;

    @Column(name = "shipping_fee")
    private Double shippingFee;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "note")
    private String note;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails;
}
