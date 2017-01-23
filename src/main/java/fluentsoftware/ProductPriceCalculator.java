package fluentsoftware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductPriceCalculator {

    private ProductService productService;

    public ProductPriceCalculator() {
        productService = new ProductService();
    }

    public int calculateTotalProductPriceInCents(List<String> skus) {
        int total = 0;
        for(String sku : skus) {
            Product product = getProduct(sku);
            total += product.getPriceInCents();
        }
        return total;
    }

    final static Map<String,Product> productCache = new HashMap<>();

    private Product getProduct(String sku) {
        Product product = productCache.get(sku);
        if (product == null) {
            product = productService.getProduct(sku);
            productCache.put(sku, product);
        }
        return product;
    }
}
