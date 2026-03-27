package drinkshop.service;

import drinkshop.domain.CategorieBautura;
import drinkshop.domain.Product;
import drinkshop.domain.TipBautura;
import drinkshop.repository.AbstractRepository;
import drinkshop.repository.Repository;
import drinkshop.service.validator.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("BBT for ProductService.updateProduct")
@Tag("BBT")
class ProductServiceBBTTest {

    private ProductService buildServiceWithSeedData() {
        Repository<Integer, Product> repo = new AbstractRepository<>() {
            @Override
            protected Integer getId(Product entity) {
                return entity.getId();
            }
        };

        repo.save(new Product(1, "Limonada", 10.0, CategorieBautura.JUICE, TipBautura.WATER_BASED));
        repo.save(new Product(2, "Cafea", 8.0, CategorieBautura.CLASSIC_COFFEE, TipBautura.BASIC));
        return new ProductService(repo);
    }

    @Nested
    @DisplayName("ECP")
    @Tag("ECP")
    class EcpTests {

        @ParameterizedTest(name = "{0}")
        @CsvSource(
                delimiter = '|',
                textBlock = """
                        valid: id>0 and price>0|1|10.5|true|false|false
                        invalid: id<=0 and price>0|0|10.5|false|true|false
                        invalid: id>0 and price<=0|1|0.0|false|false|true
                        invalid: id<=0 and price<=0|0|0.0|false|true|true
                        """
        )
        @DisplayName("Validate equivalence classes for id and price")
        void updateProduct_shouldRespectEcpPartitions(
                String caseName,
                int id,
                double price,
                boolean shouldBeValid,
                boolean expectIdError,
                boolean expectPriceError
        ) {
            // Arrange
            ProductService service = buildServiceWithSeedData();
            Product previous = id > 0 ? service.findById(id) : null;
            double previousPrice = previous != null ? previous.getPret() : 0.0;

            // Act + Assert
            if (shouldBeValid) {
                assertDoesNotThrow(() ->
                        service.updateProduct(id, "NumeNou", price, CategorieBautura.JUICE, TipBautura.WATER_BASED));

                Product updated = service.findById(id);
                assertNotNull(updated);
                assertEquals("NumeNou", updated.getNume());
                assertEquals(price, updated.getPret(), 0.0001);
                return;
            }

            ValidationException ex = assertThrows(ValidationException.class, () ->
                    service.updateProduct(id, "NumeNou", price, CategorieBautura.JUICE, TipBautura.WATER_BASED));

            if (expectIdError) {
                assertTrue(ex.getMessage().contains("ID invalid!"));
            }
            if (expectPriceError) {
                assertTrue(ex.getMessage().contains("Pret invalid!"));
            }
            if (previous != null) {
                assertEquals(previousPrice, service.findById(id).getPret(), 0.0001);
            }
        }
    }

    @Nested
    @DisplayName("BVA")
    @Tag("BVA")
    class BvaTests {

        @ParameterizedTest(name = "{0}")
        @CsvSource(
                delimiter = '|',
                textBlock = """
                        valid boundary: id=1, price=0.01|1|0.01|true|false|false
                        valid interior: id=2, price=1.00|2|1.00|true|false|false
                        invalid boundary: id=0, price=0.01|0|0.01|false|true|false
                        invalid boundary: id=1, price=0.00|1|0.00|false|false|true
                        """
        )
        @DisplayName("Validate lower boundaries for id and price")
        void updateProduct_shouldRespectBvaBoundaries(
                String caseName,
                int id,
                double price,
                boolean shouldBeValid,
                boolean expectIdError,
                boolean expectPriceError
        ) {
            // Arrange
            ProductService service = buildServiceWithSeedData();
            Product previous = id > 0 ? service.findById(id) : null;
            double previousPrice = previous != null ? previous.getPret() : 0.0;

            // Act + Assert
            if (shouldBeValid) {
                assertDoesNotThrow(() ->
                        service.updateProduct(id, "ProdusBVA", price, CategorieBautura.JUICE, TipBautura.WATER_BASED));

                Product updated = service.findById(id);
                assertNotNull(updated);
                assertEquals("ProdusBVA", updated.getNume());
                assertEquals(price, updated.getPret(), 0.0001);
                return;
            }

            ValidationException ex = assertThrows(ValidationException.class, () ->
                    service.updateProduct(id, "ProdusBVA", price, CategorieBautura.JUICE, TipBautura.WATER_BASED));

            if (expectIdError) {
                assertTrue(ex.getMessage().contains("ID invalid!"));
            }
            if (expectPriceError) {
                assertTrue(ex.getMessage().contains("Pret invalid!"));
            }
            if (previous != null) {
                assertEquals(previousPrice, service.findById(id).getPret(), 0.0001);
            }
        }
    }
}
