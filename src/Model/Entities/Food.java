package Model.Entities;

import Model.Enums.Category;
import Model.Enums.Location;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Food extends Item{
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final int EXPIRING_SOON_DAYS = 7;

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
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            throw new IllegalArgumentException("Expiry Date cannot be empty");
        }
        this.expiryDate = expiryDate;
    }
    public void setIsPerishable(boolean perishable) {
        isPerishable = perishable;
    }
    public void setIsCanned(boolean isCanned){
        this.isCanned = isCanned;
    }

    //METHODS
    private LocalDate getExpiryLocalDate() {
        if (expiryDate == null || expiryDate.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(expiryDate, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing expiry date: " + expiryDate);
            return null;
        }
    }

    public boolean isExpired(){
        LocalDate expiry = getExpiryLocalDate();
        if (expiry == null) return false;

        return LocalDate.now().isAfter(expiry);
    }

    public boolean isExpiringSoon(){
        int days = getDaysUntilExpiry();
        return days > 0 && days <= EXPIRING_SOON_DAYS;
    }

    public int getDaysUntilExpiry(){
        LocalDate expiry = getExpiryLocalDate();
        if (expiry == null) return Integer.MAX_VALUE; 

        long days = ChronoUnit.DAYS.between(LocalDate.now(), expiry);

        return (int) days;
    }

    @Override
    public String descriptionDetails(){
        String itemType = (getIsPerishable() ? "Perishable" : "Non-Perishable") + (getIsCanned() ? " (Canned)" : "");
        String status = isExpired() ? "EXPIRED" : (isExpiringSoon() ? "Expiring Soon" : "OK");
        return super.descriptionDetails() +
                String.format(" | Type: %s, Expiry Date: %s, Status: %s (%d days left)",
                        itemType, expiryDate, status, getDaysUntilExpiry());
    }

    @Override
    public double calculateValue(){
        return getPurchasePrice() * getQuantity();
    }

}

