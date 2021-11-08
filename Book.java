package cn.edu.swu.ws.book;

public class Book {
    /**
     * 书在当当网上的编号
     */
    private String code;

    /**
     * 书名
     */
    private String name;

    /**
     * 书价格
     */
    private float price;

    /**
     * 书图片的 URL 地址
     */
    private String imageUrl;

    /**
     * 保存图片的文件路径
     */
    private String imageFile;


    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setImageFile(String file) {
        this.imageFile = file;
    }

    public String getImageFile() {
        return this.imageFile;
    }


    public String toString() {
        return this.getCode() + ", " + this.getName() + ", " + this.getPrice() + ", " + this.getImageUrl();
    }
}
