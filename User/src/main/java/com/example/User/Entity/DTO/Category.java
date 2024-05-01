package com.example.User.Entity.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class Category {
    private long categoryId;
    private String categoryName;
    private String imageUrl;
}
