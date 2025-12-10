    package Model.Entities;

    import Model.Enums.Category;

    public class Miscellaneous extends Item {

        private String itemType;
        private String usage;
        private String condition;

        public Miscellaneous(String name, String description, int quantity, double purchasePrice,
                             String purchaseDate, String vendor, String location, String itemType,
                             String usage, String isCondition) {
            super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.MISCELLANEOUS, location);
            this.itemType = itemType;
            this.usage = usage;
            this.condition = isCondition;
        }

        //GETTERS
        public String getItemType() {
            return itemType;
        }
        public String getUsage() {
            return usage;
        }
        public String getCondition() {
            return condition;
        }

        //SETTERS
        public void setItemType(String itemType) {
            this.itemType = itemType;
        }
        public void setUsage(String usage) {
            this.usage = usage;
        }
        public void setCondition(String isCondition) {
            this.condition = isCondition;
        }

        //METHODS
        @Override
        public String descriptionDetails() {
            return super.descriptionDetails() +
                    String.format("Type: %s\n Usage: %s\nCondition: %s", itemType, usage,condition);
        }

        @Override
        public double calculateValue() {
            return getPurchasePrice() * getQuantity();
        }
    }
