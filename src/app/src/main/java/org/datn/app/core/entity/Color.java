package org.datn.app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "colors")
@Data
public class Color implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "color",cascade = CascadeType.ALL,targetEntity = ProductDetail.class)
    @JsonIgnore
    private List<ProductDetail> productDetails;
}
