package com.example.onlinestore.Model;

public class ItemsDetail {
    private String productType,productBrand,productWarranty,productColor,productName,productWeight,productDescription;
    private Number productQuantity,productPrice,productDiscount;
    private String mainImage,auxiliaryImage;

    public ItemsDetail(String productType, String productBrand, String productWarranty, String productColor, String productName, String productWeight, String productDescription, Number productQuantity, Number productPrice, Number productDiscount, String mainImage, String auxiliaryImage) {
        this.productType = productType;
        this.productBrand = productBrand;
        this.productWarranty = productWarranty;
        this.productColor = productColor;
        this.productName = productName;
        this.productWeight = productWeight;
        this.productDescription = productDescription;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.mainImage = mainImage;
        this.auxiliaryImage = auxiliaryImage;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductWarranty() {
        return productWarranty;
    }

    public void setProductWarranty(String productWarranty) {
        this.productWarranty = productWarranty;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Number getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Number productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Number getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Number productPrice) {
        this.productPrice = productPrice;
    }

    public Number getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(Number productDiscount) {
        this.productDiscount = productDiscount;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getAuxiliaryImage() {
        return auxiliaryImage;
    }

    public void setAuxiliaryImage(String auxiliaryImage) {
        this.auxiliaryImage = auxiliaryImage;
    }
}
