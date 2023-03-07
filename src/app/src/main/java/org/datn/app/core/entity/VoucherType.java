package org.datn.app.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "voucher_types")
@Data
public class VoucherType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "voucher_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voucher_type_name")
    private String name;

    private Boolean isTrash = Boolean.FALSE;

    @OneToMany(mappedBy = "voucherType",cascade = CascadeType.ALL)
    private List<Voucher> vouchers;

}
