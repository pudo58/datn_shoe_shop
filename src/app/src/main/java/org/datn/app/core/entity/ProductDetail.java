package org.datn.app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_details")
@Data
public class ProductDetail implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_detail_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = Size.class)
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = Product.class)
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer quantity;
    private Integer status;

    @OneToMany(mappedBy = "productDetail",cascade = CascadeType.ALL,targetEntity = Cart.class)
    @JsonIgnore
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "productDetail",cascade = CascadeType.ALL,targetEntity = OrderDetail.class)
    @JsonIgnore
    private List<OrderDetail> orderDetails = new ArrayList<>();
}
