package Model.Entities;

import Model.Enums.Category;
import Model.Enums.Location;

public class Clothing extends Item {
  
    private String condition;
    private String fabricType;
    private String size;

    public Clothing(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, Location location, String condition, String fabricType, String size) {
        super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.valueOf("CLOTHING"), location);
        this.condition = condition;
        this.fabricType = fabricType;
        this.size = size;
    }

    //GETTERS
    public String getCondition(){
        return condition;
    }

    public String getFabricType() {
        return fabricType;
    }
    public char getSize() {
        return size;
    }

    //SETTERS
    public void setCondition(String condition) {
         //ADD SPECIFIC CONTDITIONS, IT SHOULD BE THAT USER CAN ONLY IMPLEMENT GOOD, FAIR, BAD, OR ITLL RETURN NULL;
        this.condition = condition;
    }
    public void setFabricType(String fabricType) {   
        this.fabricType = fabricType;
    }
    public void setSize(String size) {
        //ADD SPECIFIC CONTDITIONS, IT SHOULD BE THAT USER CAN ONLY IMPLEMENT XS,S,M,L,XL,XXL, OR ITLL RETURN NULL;
        this.size = size;
    }

    //METHODS
    @Override
    public double calculateValue() {
        return getPurchasePrice() * getQuantity();
    }

    // MISSING METHOD DESCRIPTIONDETAILS;
}
