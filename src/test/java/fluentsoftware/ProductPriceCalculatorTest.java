package fluentsoftware;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.Console;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.powermock.api.support.membermodification.MemberMatcher.constructor;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ProductService.class,ProductPriceCalculator.class})
public class ProductPriceCalculatorTest {

    ProductPriceCalculator calculator;

    ProductService spyProductService;

    Map<String,Product> productCache;

    @Before
    public void createCalculator() throws Exception {
        spyProductService = spy(new ProductService());
        productCache = new HashMap<>();
        whenNew(ProductService.class).withAnyArguments().thenReturn(spyProductService);
        calculator = new ProductPriceCalculator();

        Field field = ProductPriceCalculator.class.getDeclaredField("productCache");
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, productCache);

    }

    @Test
    public void testCalculatingPriceOfNoProductsReturnsZero() {
        List<String> skus = new ArrayList<>();
        int total = calculator.calculateTotalProductPriceInCents(skus);
        assertEquals(0, total);
    }

    @Test
    public void testCalculatingPriceOfASingleProductReturnsTheProductPrice() throws Exception {
        List<String> skus = new ArrayList<>();
        skus.add("PROD1");

        Product product = new Product();
        product.setPriceInCents(150);

        doReturn(product).when(spyProductService).getProduct("PROD1");

        int total = calculator.calculateTotalProductPriceInCents(skus);
        assertEquals(150, total);
    }


    @Test
    public void testCalculatingPriceOfMultipleUniqueProductsReturnsTotalOfAllProducts() {
        List<String> skus = new ArrayList<>();
        skus.add("PROD1");
        skus.add("PROD2");

        Product product1 = new Product();
        product1.setPriceInCents(150);

        Product product2 = new Product();
        product2.setPriceInCents(100);

        doReturn(product1).when(spyProductService).getProduct("PROD1");
        doReturn(product2).when(spyProductService).getProduct("PROD2");

        int total = calculator.calculateTotalProductPriceInCents(skus);
        assertEquals(250, total);
    }

    @Test
    public void testCalculatingPriceOfMultipleProductsOfTheSameSkuReturnsTotalOfEachProduct() {
        List<String> skus = new ArrayList<>();
        skus.add("PROD1");
        skus.add("PROD1");

        Product product = new Product();
        product.setPriceInCents(50);

        doReturn(product).when(spyProductService).getProduct("PROD1");

        int total = calculator.calculateTotalProductPriceInCents(skus);
        assertEquals(100, total);
    }

    @Test
    public void testCalculatingPricesUsesCacheToAvoidServiceCalls() throws Exception {
        List<String> skus = new ArrayList<>();
        skus.add("PROD1");

        Product product = new Product();
        product.setPriceInCents(55);
        productCache.put("PROD1", product);


        int total = calculator.calculateTotalProductPriceInCents(skus);
        assertEquals(55, total);
    }

    // TODO cache TTL, refactoring
}
