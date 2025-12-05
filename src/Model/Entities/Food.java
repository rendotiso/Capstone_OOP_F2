package Model.Entities;

import Model.Enums.Category;
import Model.Enums.Location;

public class Food extends Item{
    private String expiryDate;
    private boolean isCanned;
    private boolean isPerishable;

    public Food(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, Location location, String expiryDate, boolean isCanned, boolean isPerishable){
        super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.valueOf("FOOD"), location);
        this.expiryDate = expiryDate;
        this.isCanned = isCanned;
        this.isPerishable = isPerishable;
    }

    //GETTERS
    public String getExpiryDate() {
        return expiryDate;
    }
    public boolean getIsCanned(){
        return isCanned;
    }
    public boolean getIsPerishable(){
        return isPerishable;
    }

    //SETTERS
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    public void setIsCanned(boolean isCanned){
        this.isCanned = isCanned;
    }
    public void setIsPerishable(boolean perishable) {
        isPerishable = perishable;
    }


    //METHODS
    //MISSING METHODS ; CHECK CLASS DIAGRAM PLSSSSSSSS
    @Override
    public double calculateValue(){
        return getPurchasePrice() * getQuantity();
    }

}

