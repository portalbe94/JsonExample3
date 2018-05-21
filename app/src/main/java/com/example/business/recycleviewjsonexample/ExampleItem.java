package com.example.business.recycleviewjsonexample;

public class ExampleItem {
    private String mImageUrl;
    private String mCreator;
    private int mLikes;
    private int mNumber;

    public ExampleItem(String imageUrl, String Product, int SalePrice, int itemId) {
        mImageUrl = imageUrl;
        mCreator = Product;
        mLikes = SalePrice;
        mNumber = itemId;

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

    public int getmNumber() { return mNumber; }

}
