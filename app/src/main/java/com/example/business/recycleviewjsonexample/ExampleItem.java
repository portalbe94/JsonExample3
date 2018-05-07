package com.example.business.recycleviewjsonexample;

public class ExampleItem {
    private String mImageUrl;
    private String mCreator;
    private int mLikes;

    public ExampleItem(String imageUrl, String Product, int SalePrice) {
        mImageUrl = imageUrl;
        mCreator = Product;
        mLikes = SalePrice;

    }

    public String getImageUrl() {
       return mImageUrl;
    }

    public String getCreator() {
        return mCreator;
    }

    public int getLikeCount() {
        return mLikes;
    }

}
