package com.jdc.pos.model.entity;

import com.jdc.pos.utils.FormatUtils;
import lombok.Data;

import javax.persistence.*;



@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Account usuario;
    @ManyToOne
    private Category category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    private boolean valid = true;

    private String remark;

    @ManyToOne(optional = false)
    private Account createdBy; // ✅ Nuevo campo: usuario que creó el producto

    public String getPriceStr() {
        return FormatUtils.formatNumber(price);
    }

    public String getValidStr() {
        return valid ? "Yes" : "No";
    }
}
