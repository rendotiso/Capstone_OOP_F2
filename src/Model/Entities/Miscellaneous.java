package Model.Entities;

public class Miscellaneous extends Item {
  
    private String condition;
    private String fabricType;
    private char size;

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
    public double calculateValue() {
        return purchasePrice * quantity;
    }
}
