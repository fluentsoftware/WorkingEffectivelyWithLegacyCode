package fluentsoftware;


public class Product {

    int priceInCents;
    boolean taxable;
    Integer taxClassID;
    String SKU;

    public int getPriceInCents() {
        return priceInCents;
    }

    public void setPriceInCents(int priceInCents) {
        this.priceInCents = priceInCents;
    }

    public boolean isTaxable() {
        return taxable;
    }

    public void setTaxable(boolean taxable) {
        this.taxable = taxable;
    }

    public Integer getTaxClassID() {
        return taxClassID;
    }

    public void setTaxClassID(Integer taxClassID) {
        this.taxClassID = taxClassID;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }
}
