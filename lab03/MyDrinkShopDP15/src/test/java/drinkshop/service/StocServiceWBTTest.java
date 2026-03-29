package drinkshop.service;

import drinkshop.domain.IngredientReteta;
import drinkshop.domain.Reteta;
import drinkshop.domain.Stoc;
import drinkshop.repository.AbstractRepository;
import drinkshop.repository.Repository;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * White-Box Tests for StocService.consuma(Reteta reteta)
 *
 * CFG Decisions:
 *   D1: !areSuficient(reteta)    → true: throw; false: continue
 *   D2: outer for-each           → true: has next ingredient; false: exit loop
 *   D3: inner for-each           → true: has next stoc item; false: exit inner loop
 *   D4: ramas <= 0               → true: break; false: consume from stoc item
 *
 * Cyclomatic Complexity = 5 (4 decisions + 1)
 *
 * Independent Paths:
 *   P1: D1=T → throw IllegalStateException
 *   P2: D1=F, D2=F → empty recipe, normal exit
 *   P3: D1=F, D2=T, D3=F → outer loop runs, inner loop 0 iterations
 *   P4: D1=F, D2=T, D3=T, D4=T → break on ramas<=0
 *   P5: D1=F, D2=T, D3=T, D4=F → normal consumption
 */
@DisplayName("WBT for StocService.consuma")
@Tag("WBT")
class StocServiceWBTTest {

    private Repository<Integer, Stoc> stocRepo;
    private StocService stocService;

    @BeforeEach
    void setUp() {
        stocRepo = new AbstractRepository<>() {
            @Override
            protected Integer getId(Stoc entity) {
                return entity.getId();
            }
        };
        stocService = new StocService(stocRepo);
    }

    // -----------------------------------------------------------------------
    // TC1 – P1: D1=true → throws IllegalStateException
    // Statement coverage: throw branch
    // Decision coverage:  D1=true
    // -----------------------------------------------------------------------
    @Test
    @DisplayName("TC1 [P1] – insufficient stock throws IllegalStateException")
    void consuma_insufficientStock_throwsException() {
        // Arrange: recipe needs 100ml lapte, stoc only has 50ml
        stocRepo.save(new Stoc(1, "lapte", 50, 0));
        Reteta reteta = new Reteta(1, List.of(new IngredientReteta("lapte", 100.0)));

        // Act + Assert
        assertThrows(IllegalStateException.class, () -> stocService.consuma(reteta));
    }

    // -----------------------------------------------------------------------
    // TC2 – P2: D1=false, D2=false → empty recipe, no iteration
    // Statement coverage: outer loop not entered
    // Decision coverage:  D1=false, D2=false
    // Loop coverage:      outer loop 0 iterations
    // -----------------------------------------------------------------------
    @Test
    @DisplayName("TC2 [P2] – empty recipe does nothing")
    void consuma_emptyRecipe_noConsumption() {
        // Arrange: stoc exists but recipe has no ingredients
        stocRepo.save(new Stoc(1, "lapte", 100, 0));
        Reteta reteta = new Reteta(1, List.of());

        // Act
        assertDoesNotThrow(() -> stocService.consuma(reteta));

        // Assert: stoc unchanged
        Stoc stoc = stocRepo.findOne(1);
        assertEquals(100.0, stoc.getCantitate(), 0.001);
    }

    // -----------------------------------------------------------------------
    // TC3 – P3: D1=false, D2=true, D3=false → outer loop runs, inner loop 0 iter
    // The ingredient "apa" is needed (0ml) but no stoc entry exists for it.
    // Statement coverage: outer loop body, inner loop not entered
    // Decision coverage:  D2=true, D3=false
    // Loop coverage:      outer loop 1 iteration, inner loop 0 iterations
    // -----------------------------------------------------------------------
    @Test
    @DisplayName("TC3 [P3] – ingredient not in stoc, inner loop has 0 iterations")
    void consuma_ingredientNotInStoc_innerLoopNotEntered() {
        // Arrange: recipe needs 0ml of "apa", stoc has only "lapte"
        stocRepo.save(new Stoc(1, "lapte", 100, 0));
        Reteta reteta = new Reteta(1, List.of(new IngredientReteta("apa", 0.0)));

        // Act
        assertDoesNotThrow(() -> stocService.consuma(reteta));

        // Assert: lapte stoc is unchanged
        Stoc stoc = stocRepo.findOne(1);
        assertEquals(100.0, stoc.getCantitate(), 0.001);
    }

    // -----------------------------------------------------------------------
    // TC4 – P4: D1=false, D2=true, D3=true, D4=true → break on ramas<=0
    // Two stoc entries for same ingredient; first fully satisfies need.
    // On second inner iteration ramas=0 → D4=true → break.
    // Statement coverage: break statement
    // Decision coverage:  D3=true (2nd time), D4=true
    // Loop coverage:      inner loop 2 iterations (1 normal + 1 break)
    // -----------------------------------------------------------------------
    @Test
    @DisplayName("TC4 [P4] – ramas<=0 causes break on second inner-loop iteration")
    void consuma_ramasZero_breakOnSecondIteration() {
        // Arrange: recipe needs 50ml lapte; two stoc entries (50ml + 30ml)
        stocRepo.save(new Stoc(1, "lapte", 50, 0));
        stocRepo.save(new Stoc(2, "lapte", 30, 0));
        Reteta reteta = new Reteta(1, List.of(new IngredientReteta("lapte", 50.0)));

        // Act
        assertDoesNotThrow(() -> stocService.consuma(reteta));

        // Assert: first entry is depleted, second is untouched
        double totalRemaining = stocRepo.findAll().stream()
                .filter(s -> s.getIngredient().equalsIgnoreCase("lapte"))
                .mapToDouble(Stoc::getCantitate)
                .sum();
        assertEquals(30.0, totalRemaining, 0.001);
    }

    // -----------------------------------------------------------------------
    // TC5 – P5: D1=false, D2=true, D3=true, D4=false → normal consumption
    // One stoc entry with more than enough; consumed partially.
    // Statement coverage: inner body (deScazut, setCantitate, update)
    // Decision coverage:  D4=false
    // Loop coverage:      inner loop 1 iteration (normal)
    // -----------------------------------------------------------------------
    @Test
    @DisplayName("TC5 [P5] – normal partial consumption from single stoc entry")
    void consuma_sufficientSingleEntry_consumedPartially() {
        // Arrange: recipe needs 30ml lapte; stoc has 100ml
        stocRepo.save(new Stoc(1, "lapte", 100, 0));
        Reteta reteta = new Reteta(1, List.of(new IngredientReteta("lapte", 30.0)));

        // Act
        assertDoesNotThrow(() -> stocService.consuma(reteta));

        // Assert: stoc reduced by exactly 30ml
        Stoc stoc = stocRepo.findOne(1);
        assertEquals(70.0, stoc.getCantitate(), 0.001);
    }
}
