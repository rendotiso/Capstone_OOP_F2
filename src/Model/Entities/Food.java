package Model.Entities;

public class Food extends Item{
    private String expiryDate;
    private boolean isCanned;
    private boolean isPerishable;

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean getIsCanned(){

    }

    public void setIsCanned(boolean isCanned){
        this.isCanned = isCanned;
    }
    public boolean getIsPerishable(){
        return isPerishable;
    }

    public void setIsPerishable(boolean perishable) {
        isPerishable = perishable;
    }

    public boolean isExpired(){

    }

    @Override
    public double calculateValue(){

    }

}

