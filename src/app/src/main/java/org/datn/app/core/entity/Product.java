package org.datn.app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

    @Column(name = "product_name",columnDefinition = "TEXT",length = 65535)
    private String name;

    private Double price;

    private Double discount;

    private String imageThumbnail;

    private String description;

    private Integer status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = Category.class)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = Publisher.class)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,targetEntity = ProductDetail.class)
    @JsonIgnore
    private List<ProductDetail> productDetails = new ArrayList<>();

    @OneToMany(mappedBy = "product",cascade = CascadeType.MERGE,targetEntity = AttributeData.class)
    @JsonIgnore
    private List<AttributeData> attributeData = new ArrayList<>();

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,targetEntity = VoucherProductCategoryLink.class)
    @JsonIgnore
    private List<VoucherProductCategoryLink> voucherProductCategoryLinks = new ArrayList<>();

}
