package org.datn.app.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "carts")
@Data
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = ProductDetail.class)
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;

    private Integer quantity;

    private Double price;

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
}
