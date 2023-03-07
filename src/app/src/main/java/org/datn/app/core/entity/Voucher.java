package org.datn.app.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vouchers")
@Data
public class Voucher implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "voucher_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    @Column(name = "`name`")
    private String name;

    private String description;

    private String image;

    @Temporal(TemporalType.TIMESTAMP)
    private Date effectFrom;

    @Temporal(TemporalType.TIMESTAMP)
    private Date effectUntil;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    private Integer quantity;

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = VoucherType.class)
    @JoinColumn(name = "voucher_type_id")
    private VoucherType voucherType;

    @OneToMany(mappedBy = "voucher",cascade = CascadeType.ALL,targetEntity = Transaction.class)
    private List<Transaction> transactions;

    // 1 product have many voucher
    @ManyToOne(cascade = CascadeType.ALL,targetEntity = Product.class)
    @JoinColumn(name = "product_id")
    private Product product;
}
