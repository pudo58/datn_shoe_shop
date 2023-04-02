package org.datn.app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

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

    private Double discount = 0.0;

    private String imageThumbnail;

    private String description;

    private Integer status;

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = Category.class)
    @JoinColumn(name = "category_id")
    private Category category;


    @ManyToOne(cascade = CascadeType.ALL,targetEntity = Publisher.class)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,targetEntity = Voucher.class)
    @JsonIgnore
    private List<Voucher> vouchers;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,targetEntity = OrderDetail.class)
    @JsonIgnore
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,targetEntity = ProductDetail.class)
    @JsonIgnore
    private List<ProductDetail> productDetails;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,targetEntity = AttributeData.class)
    //@JsonIgnore
    private List<AttributeData> attributeData;

    public void addAttributeData(Attribute attribute, String value, String type) {
        AttributeData attributeData = new AttributeData();
        attributeData.setAttribute(attribute);
        attributeData.setProduct(this);
        attributeData.setValue(value);
        attributeData.setType(type);
        this.getAttributeData().add(attributeData);
    }


}
