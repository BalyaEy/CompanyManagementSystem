package supplier;
//supplier ı unique identifier ve isimle gösterir, özelliklerine erişim ve değişiklik yapma yöntemleri sağlamak.
public class Supplier {
    private int supplierId;
    private String supplierName;

    public Supplier(int supplierId, String supplierName) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    
}