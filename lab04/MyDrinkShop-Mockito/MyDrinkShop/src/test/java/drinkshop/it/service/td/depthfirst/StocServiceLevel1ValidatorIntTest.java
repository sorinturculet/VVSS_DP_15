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

public class StocServiceLevel1ValidatorIntTest {

    @Mock
    private Stoc stoc;

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
    void add_validMockedStoc_realValidatorAllowsSave() {
        when(stoc.getId()).thenReturn(1);
        when(stoc.getIngredient()).thenReturn("Apa");
        when(stoc.getCantitate()).thenReturn(5.0);
        when(stoc.getStocMinim()).thenReturn(1.0);
        when(stocRepo.save(stoc)).thenReturn(stoc);

        assertDoesNotThrow(() -> stocService.add(stoc));

        verify(stoc, times(1)).getId();
        verify(stoc, times(2)).getIngredient();
        verify(stoc, times(2)).getCantitate();
        verify(stoc, times(2)).getStocMinim();
        verify(stocRepo, times(1)).save(stoc);
    }

    @Test
    void add_invalidMockedStoc_realValidatorRejectsBeforeSave() {
        when(stoc.getId()).thenReturn(-1);
        when(stoc.getIngredient()).thenReturn("Apa");
        when(stoc.getCantitate()).thenReturn(5.0);
        when(stoc.getStocMinim()).thenReturn(1.0);

        assertThrows(ValidationException.class, () -> stocService.add(stoc));

        verify(stoc, times(1)).getId();
        verify(stocRepo, never()).save(any());
    }
}