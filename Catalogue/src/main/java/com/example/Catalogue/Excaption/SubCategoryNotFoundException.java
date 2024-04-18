package com.example.Catalogue.Excaption;

public class SubCategoryNotFoundException extends RuntimeException{
    public SubCategoryNotFoundException(String message) {
        super(message);
    }
}
