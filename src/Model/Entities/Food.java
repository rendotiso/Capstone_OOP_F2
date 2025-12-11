package Model.Entities;

import Model.Enums.Category;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Food extends Item{
    private String expiryDate;
    private boolean isPerishable;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final int EXPIRING_SOON_DAYS = 7;

    public Food(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, String location, String expiryDate, boolean isPerishable){
        super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.FOOD, location);
        this.expiryDate = expiryDate;
        this.isPerishable = isPerishable;
    }

    //GETTERS
    public String getExpiryDate() {
        return expiryDate;
    }
    public boolean getIsPerishable(){
        return isPerishable;
    }

    //SETTERS
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    public void setIsPerishable(boolean perishable) {
        isPerishable = perishable;
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
        return days >= 0 && days <= EXPIRING_SOON_DAYS;
    }

    public int getDaysUntilExpiry(){
        LocalDate expiry = getExpiryLocalDate();
        if (expiry == null) return Integer.MAX_VALUE;

        long days = ChronoUnit.DAYS.between(LocalDate.now(), expiry);

        return (int) days;
    }

    @Override
    public String descriptionDetails(){
        String itemType = getIsPerishable() ? "Perishable" : "Non-Perishable";

        String status;
        if (isExpired()) {
            status = "EXPIRED";
        } else if (isExpiringSoon()) {
            status = "URGENT: Expiring Soon";
        } else {
            status = "Good";
        }

        return String.format("%sType: %s\nExpiry Date: %s\nStatus: %s (%d days remaining)",
                super.descriptionDetails(),
                itemType,
                expiryDate,
                status,
                getDaysUntilExpiry());
    }

    @Override
    public double calculateValue(){
        if (isExpired()) return 0.0;
        return getPurchasePrice() * getQuantity();
    }
}