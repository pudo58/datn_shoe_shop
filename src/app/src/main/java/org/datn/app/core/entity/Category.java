package org.datn.app.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "`name`")
    private String name;

    private String description;

    private Boolean isTrash = Boolean.FALSE;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Attribute> attributes;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Product> products;
}
