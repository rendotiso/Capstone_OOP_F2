package Model.Entities;

public class Food extends Item implements Expirable {
  private String expiryDate;
    private boolean isCanned;
    private boolean isPerishable;
}

