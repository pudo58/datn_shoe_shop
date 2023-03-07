package org.datn.app.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "order_details")
@Data

public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "order_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private Double price;
    @ManyToOne(cascade = CascadeType.ALL,targetEntity = Order.class)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = Product.class)
    @JoinColumn(name = "product_id")
    private Product product;
}
