package org.datn.app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
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

    private Integer limitPerUser;

    private Integer discountType;

    private Integer discountValue;

    private Boolean isAutoApply; // TODO : tự động sử dụng khi nguời dùng đủ điều kiện

    private Integer status;

    @OneToMany(mappedBy = "voucher",targetEntity = Transaction.class)
    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "voucher",targetEntity = VoucherProductCategoryLink.class)
    @JsonIgnore
    private List<VoucherProductCategoryLink> voucherProductCategoryLinkList = new ArrayList<>();
}
