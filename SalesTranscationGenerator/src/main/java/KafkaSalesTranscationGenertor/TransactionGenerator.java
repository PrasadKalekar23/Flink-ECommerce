package KafkaSalesTranscationGenertor;

import ObjectClass.TransactionRecord;
import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class TransactionGenerator {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static TransactionRecord generateSalesTransaction() {
        TransactionRecord record = new TransactionRecord();

        record.setTransactionId(System.currentTimeMillis());
        record.setProductId(randomProductId());
        record.setProductName(setProductNameAccordingToProductId(record.getProductId()));
        record.setProductQuantity(randomProductQuantity());
        record.setProductPrice(setProductPriceWRTProduct(record.getProductId()));
        record.setTotalPayAmount(calculateTotalPayAmount(record.getProductQuantity(), record.getProductPrice()));
        record.setProductCategory(setProductCategoryWRTProduct(record.getProductId()));
        record.setCustomerId(faker.name().username());
        record.setTransactionDate(currentFormattedDate());

        return record;
    }

    private static String setProductNameAccordingToProductId(String productId) {
        switch (productId){
            case "product1":
                return "laptop";
            case "product2":
                return "mobile";
            case "product3":
                return "T-shirt";
            case "product4":
                return "Vegetables";
            case "product5":
                return "Body Lotion";
            case "product6":
                return "Soap";
        }
        return "default";

    }

    private static Double setProductPriceWRTProduct(String productId){
        switch (productId){
            case "product1":
                return 50000.;
            case "product2":
                return 20000.;
            case "product3":
                return 2900.;
            case "product4":
                return 200.;
            case "product5":
                return 80.;
            case "product6":
                return 60.;
        }
        return 50.;
    }

    private static String setProductCategoryWRTProduct(String productId){
        switch (productId){
            case "product1":
                return "Electronics";
            case "product2":
                return "Electronics";
            case "product3":
                return "Fashion";
            case "product4":
                return "Grocery";
            case "product5":
                return "beauty";
            case "product6":
                return "beauty";
        }
        return "default";
    }

    private static Double calculateTotalPayAmount(int productQuantity, double productPrice){
        return productPrice*productQuantity;
    }

    private static String randomProductId() {
        return randomChoice(new String[]{"product1", "product2", "product3", "product4", "product5", "product6"});
    }

    private static int randomProductQuantity() {
        return 1 + random.nextInt(10);
    }

    private static String currentFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return dateFormat.format(new Date());
    }

    private static <T> T randomChoice(T[] items) {
        return items[random.nextInt(items.length)];
    }
}
