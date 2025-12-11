package Model.Entities;

import Model.Enums.Category;

public class Clothing extends Item {
  
    private String condition;
    private String fabricType;
    private String size;

    public Clothing(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, String location, String condition, String fabricType, String size){
        super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.CLOTHING, location);
          setCondition(condition);
          setFabricType(fabricType);
          setSize(size);
    }

    //GETTERS
    public String getCondition(){
        return condition;
    }
    public String getFabricType() {
        return fabricType;
    }
    public String getSize() {
        return size;
    }

    //SETTERS
    public void setCondition(String condition) {
        this.condition = condition;
    }
    public void setFabricType(String fabricType) {
        this.fabricType = fabricType;
    }
    public void setSize(String size) {
        this.size = size;
    }

    //METHODS
    @Override
    public double calculateValue() {
        return getPurchasePrice() * getQuantity();
    }
    @Override
    public String descriptionDetails() {
        return super.descriptionDetails() +
                String.format("Condition: %s\nSize: %s\nFabric Type: %s", condition, size, fabricType);
    }
}
