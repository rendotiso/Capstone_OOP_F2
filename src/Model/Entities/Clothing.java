package Model.Entities;

import Model.Enums.Category;
import Model.Enums.Location;

public class Clothing extends Item {
  
    private String condition;
    private String fabricType;
    private char size;

    public Clothing(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, Location location, String condition, String fabricType, char size){
        super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.valueOf("Clothing"), location);
          this.condition = condition;
          this.fabricType = fabricType;
          this.size = size;
    }
    public String getCondition(){
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getFabricType() {
        return fabricType;
    }

    public void setFabricType(String fabricType) {
        this.fabricType = fabricType;
    }

    public char getSize() {
        return size;
    }

    public void setSize(char size) {
        this.size = size;
    }

    @Override
    public void updateQuantity(int n) {
        setQuantity(getQuantity() + 1);
    }

    @Override
    public double calculateValue() {
        return getPurchasePrice() * getQuantity();
    }
}
