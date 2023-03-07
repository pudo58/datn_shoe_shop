package org.datn.app.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Data
public class Image implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne(targetEntity = Product.class)
    private Product product;
}
