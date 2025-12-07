    package Model.Entities;

    import Model.Enums.Category;

    public class Miscellaneous extends Item {

        private String itemType;
        private String usage;
        private boolean isCondition;

        public Miscellaneous(String name, String description, int quantity, double purchasePrice,
                             String purchaseDate, String vendor, String location, String itemType,
                             String usage, boolean isCondition) {
            super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.MISCELLANEOUS, location);
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
        @Override
        public String descriptionDetails() {
            String condition;
            if(isCondition) condition = "Intact";
            else condition = "Damage";
            return super.descriptionDetails() +
                    String.format("\nType: %s \n Usage: %s\nCondition: %s", itemType, usage,condition);
        }

        @Override
        public double calculateValue() {
            return getPurchasePrice() * getQuantity();
        }
    }
