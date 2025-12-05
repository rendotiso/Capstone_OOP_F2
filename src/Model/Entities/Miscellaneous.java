    package Model.Entities;

    import Model.Enums.Category;
    import Model.Enums.Location;

    public class Miscellaneous extends Item {

        private String itemType;
        private String usage;
        private boolean isCondition;

        public Miscellaneous(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, Location location, String itemType, String usage, boolean isCondition) {
            super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.valueOf("MISCELLANEOUS"), location);
            this.itemType = itemType;
            this.usage = usage;
            this.isCondition = isCondition;
        }

        //GETTERS
        public String getItemType() {
            return itemType;
        }
        public String getUsage() {
            return usage;
        }
        public String getIsCondition() {
            return isCondition ? "Yes" : "No";
        }

        //SETTERS
        public void setItemType(String itemType) {
            this.itemType = itemType;
        }
        public void setUsage(String usage) {
            this.usage = usage;
        }
        public void setIsCondition(boolean isCondition) {
            this.isCondition = isCondition;
        }

        //METHODS
        // MISSING METHODS
        @Override
        public String descriptionDetails() {
            return super.descriptionDetails() +
                    String.format(" | Quantity: %d units, Storage Location: %s", getQuantity(), getLocation()) +
                    String.format(" | Type: %s, Usage: %s, Good Condition: %s", itemType, usage, getIsCondition());
        }

        @Override
        public double calculateValue() {
            return getPurchasePrice() * getQuantity();
        }
    }
