package org.datn.app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name")
    private String name;

    private Double price;

    private Boolean isTrash =Boolean.FALSE;

    private Double discount;

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = Category.class)
    @JoinColumn(name = "category_id")
    private Category category;


    @ManyToOne(cascade = CascadeType.ALL,targetEntity = Size.class)
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = Publisher.class)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,targetEntity = Voucher.class)
    @JsonIgnore
    private List<Voucher> vouchers;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,targetEntity = OrderDetail.class)
    @JsonIgnore
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,targetEntity = Image.class)
    @JsonIgnore
    private List<Image> images;
}
