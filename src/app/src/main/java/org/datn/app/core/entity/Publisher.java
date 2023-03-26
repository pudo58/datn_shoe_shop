package org.datn.app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "publishers")
@Data
public class Publisher implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "publisher_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`website`")
    private String website;

    @OneToMany(mappedBy = "publisher",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;
}
