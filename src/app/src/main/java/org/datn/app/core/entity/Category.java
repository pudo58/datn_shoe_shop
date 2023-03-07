package org.datn.app.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = Attribute.class)
    private Attribute attribute;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
}
