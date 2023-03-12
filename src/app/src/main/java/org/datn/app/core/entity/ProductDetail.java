package org.datn.app.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
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
    private Size size;

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = Product.class)
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer quantity;
    private Integer status;

}
