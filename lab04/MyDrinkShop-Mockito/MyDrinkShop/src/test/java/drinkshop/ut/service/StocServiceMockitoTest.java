package drinkshop.ut.service;

import drinkshop.domain.Stoc;
import drinkshop.repository.Repository;
import drinkshop.service.StocService;
import drinkshop.service.validator.ValidationException;
import drinkshop.service.validator.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class StocServiceMockitoTest {

    @Mock
    private Validator<Stoc> stocValidator;

    @Mock
    private Repository<Integer, Stoc> stocRepo;

    @Mock
    private Stoc stoc;

    @InjectMocks
    private StocService stocService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void add_validStoc_validatesAndSavesWithMocks() {
        doNothing().when(stocValidator).validate(stoc);
        when(stocRepo.save(stoc)).thenReturn(stoc);

        assertDoesNotThrow(() -> stocService.add(stoc));

        verify(stocValidator, times(1)).validate(stoc);
        verify(stocRepo, times(1)).save(stoc);
    }

    @Test
    void add_invalidStoc_stopsAfterValidatorMock() {
        doThrow(new ValidationException("ID invalid!\n")).when(stocValidator).validate(stoc);

        ValidationException exception = assertThrows(ValidationException.class, () -> stocService.add(stoc));

        assertEquals("ID invalid!\n", exception.getMessage());
        verify(stocValidator, times(1)).validate(stoc);
        verify(stocRepo, never()).save(any());
    }

    @Test
    void getAll_returnsRepositoryDataFromMock() {
        when(stocRepo.findAll()).thenReturn(Collections.singletonList(stoc));

        assertEquals(1, stocService.getAll().size());

        verify(stocRepo, times(1)).findAll();
        verify(stocValidator, never()).validate(any());
    }
}
