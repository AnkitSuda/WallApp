package example.androidgrid.simplewallpapersapp.model;

/**
 * Created by ankit on 30/12/17.
 */

public class Category {
    public String catId;
    public String catImage;
    public String catTitle;

    public Category() {

    }

    public Category(String catId, String catImage, String catTitle) {
        this.catId = catId;
        this.catImage = catImage;
        this.catTitle = catTitle;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public String getCatTitle() {
        return catTitle;
    }

    public void setCatTitle(String catTitle) {
        this.catTitle = catTitle;
    }
}
