package ObjectClass;

public class TransactionRecord {
    private long transactionId;
    private String productId;
    private String productName;
    private double productPrice;
    private int productQuantity;
    private String productCategory;
    private String customerId;
    private String transactionDate;
    private Double totalPayAmount;

    // Getters and setters
    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductQuantity(int productQuantity){
        this.productQuantity = productQuantity;
    }

    public int getProductQuantity(){
        return productQuantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice){
        this.productPrice = productPrice;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductCategory(){
        return this.productCategory;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getTotalPayAmount(){
        return totalPayAmount;
    }

    public void setTotalPayAmount(double totalPayAmount){
        this.totalPayAmount = totalPayAmount;
    }



    @Override
    public String toString() {
        return "TransactionRecord{" +
                "transactionId=" + transactionId +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice + '\'' +
                ", productQuantity=" + productQuantity + '\'' +
                ", totalPayAmount=" + totalPayAmount + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", customerId='" + customerId + '\'' +
                ", transactionDate='" + transactionDate + '\'' +
                '}';
    }
}


