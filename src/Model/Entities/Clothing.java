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
        if (condition == null || condition.trim().isEmpty()) {
            throw new IllegalArgumentException("Condition cannot be empty");
        }
        this.condition = condition.trim();
    }

    public void setFabricType(String fabricType) {
        this.fabricType = fabricType != null ? fabricType.trim() : "";
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
        return super.descriptionDetails() + String.format("Size: %s\nCondition: %s", size, condition);
    }
}
