package com.example.Catalogue.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@Entity
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private boolean isAvailable;
    private String imageUrl;
    @UpdateTimestamp
    private Date updateAt;
    @CreationTimestamp
    private Date createAt;
    @UpdateTimestamp
    private Date DeleteAt;
    @ManyToOne
    @JoinColumn(name = "subCategoryId")
    private SubCategory subCategory;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;



}
