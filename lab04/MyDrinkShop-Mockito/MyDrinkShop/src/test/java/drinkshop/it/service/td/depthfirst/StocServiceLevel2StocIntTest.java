package drinkshop.it.service.td.depthfirst;

import drinkshop.domain.Stoc;
import drinkshop.repository.Repository;
import drinkshop.service.StocService;
import drinkshop.service.validator.StocValidator;
import drinkshop.service.validator.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class StocServiceLevel2StocIntTest {

    private StocValidator stocValidator; // REAL

    @Mock
    private Repository<Integer, Stoc> stocRepo;

    private StocService stocService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        stocValidator = new StocValidator();
        stocService = new StocService(stocRepo, stocValidator);
    }

    @Test
    void add_validRealStoc_realValidatorSavesToMockRepo() {
        Stoc stoc = new Stoc(1, "Apa", 5.0, 1.0);
        when(stocRepo.save(stoc)).thenReturn(stoc);

        assertDoesNotThrow(() -> stocService.add(stoc));

        verify(stocRepo, times(1)).save(stoc);
    }

    @Test
    void add_invalidRealStoc_realValidatorRejectsBeforeMockRepo() {
        Stoc stoc = new Stoc(-1, "Apa", 5, 10);

        assertThrows(ValidationException.class, () -> stocService.add(stoc));

        verify(stocRepo, never()).save(any());
    }
}