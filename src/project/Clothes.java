package project;

public class Clothes {

    private int id = -1;
    private String category = "";
    private String name = "";
    private String material = "";
    private int size = 0;
    private double price = 0.0;

    public Clothes(int id, String category, String name, String material, int size, double price) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.material = material;
        this.size = size;
        this.price = price;
    }


    public Clothes() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
