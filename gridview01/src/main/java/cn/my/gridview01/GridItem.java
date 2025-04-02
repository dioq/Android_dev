package cn.my.gridview01;

public class GridItem {
    private final int imageResId;
    private final String text;

    public GridItem(int imageResId, String title) {
        this.imageResId = imageResId;
        this.text = title;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getText() {
        return text;
    }
}