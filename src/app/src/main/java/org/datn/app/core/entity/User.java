package org.datn.app.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String address;

    private String avatar;

    private String role;

    @Column(name = "status",columnDefinition = "int default 0")
    private Integer status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = Rank.class)
    @JoinColumn(name = "rank_id",referencedColumnName = "rank_id")
    private Rank rank;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,targetEntity = Order.class)
    private List<Order> orders;
}
