package Model.Entities;

import Model.Enums.Category;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Food extends Item{
    private String expiryDate;
    private String dietaryInfo;
    private boolean isPerishable;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final int EXPIRING_SOON_DAYS = 7;

    public Food(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, String location, String expiryDate, String dietaryInfo, boolean isPerishable){
        super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.FOOD, location);
        this.expiryDate = expiryDate;
        this.dietaryInfo = dietaryInfo;
        this.isPerishable = isPerishable;
    }

    //GETTERS
    public String getExpiryDate() {
        return expiryDate;
    }
    public String getDietaryInfo(){
        return dietaryInfo;
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
    public void setDietaryInfo(String dietaryInfo){
        this.dietaryInfo = dietaryInfo;
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
        StringBuilder details = new StringBuilder(super.descriptionDetails());

        // Add Perishable status
        details.append("\nType: ").append(getIsPerishable() ? "Perishable" : "Non-Perishable");

        // Add Dietary Info if not empty or "N/A"
        if (dietaryInfo != null && !dietaryInfo.trim().isEmpty() && !dietaryInfo.equals("N/A")) {
            details.append("\nDietary Info: ").append(dietaryInfo);
        }

        // Add Expiry Date
        details.append("\nExpiry Date: ").append(expiryDate);

        // Add Status
        if (isExpired()) {
            details.append("\nStatus: EXPIRED (").append(Math.abs(getDaysUntilExpiry())).append(" days expired)");
        } else if (isExpiringSoon()) {
            details.append("\nStatus: Expiring Soon (").append(getDaysUntilExpiry()).append(" days left)");
        } else if (getDaysUntilExpiry() == 0) {
            details.append("\nStatus: Expires Today!");
        } else {
            details.append("\nStatus: OK (").append(getDaysUntilExpiry()).append(" days left)");
        }

        return details.toString();
    }

    @Override
    public double calculateValue(){
        return getPurchasePrice() * getQuantity();
    }
}