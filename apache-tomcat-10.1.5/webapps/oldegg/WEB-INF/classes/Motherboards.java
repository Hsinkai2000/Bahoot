public class Motherboards {
    int id;
    String brand,model,socket;
    Float price;
    int qty;
    String formfactor,link;
    
    public Motherboards(int id, String brand, String model, String socket, Float price, int qty, String formfactor,
            String link) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.socket = socket;
        this.price = price;
        this.qty = qty;
        this.formfactor = formfactor;
        this.link = link;
    }

    
}
