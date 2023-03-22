package org.datn.app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng",regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;

    private String phoneNumber;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    private String avatar;

    private String role = "ROLE_USER";

    @Column(name = "status",columnDefinition = "int default 0")
    private Integer status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified = new Date();

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = Rank.class)
    @JoinColumn(name = "rank_id",referencedColumnName = "rank_id")
    private Rank rank;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,targetEntity = Order.class)
    @JsonIgnore
    private List<Order> orders;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,targetEntity = Cart.class)
    @JsonIgnore
    private List<Cart> carts;

}
