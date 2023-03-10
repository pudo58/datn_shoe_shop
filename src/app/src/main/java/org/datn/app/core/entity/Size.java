package org.datn.app.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "sizes")
@Data
public class Size implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "size_id")
    private Long id;
    private String size;

    @OneToMany(mappedBy = "size", cascade = CascadeType.ALL)
    private List<ProductDetail> productDetails;
}
