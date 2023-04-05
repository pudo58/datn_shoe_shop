package org.datn.app.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String address;

    private String phoneNumber;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date modified = new Date();

    private String paymentMethod;

    private String note;

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer status;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,targetEntity = OrderDetail.class)
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,targetEntity = Transaction.class)
    private List<Transaction> transactions;
}
