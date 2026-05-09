package drinkshop.it.service.td.depthfirst;

import drinkshop.domain.Stoc;
import drinkshop.repository.Repository;
import drinkshop.repository.file.FileStocRepository;
import drinkshop.service.StocService;
import drinkshop.service.validator.StocValidator;
import drinkshop.service.validator.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StocServiceIntTest {

    @TempDir
    Path tempDir;

    private StocValidator stocValidator; // REAL
    private Repository<Integer, Stoc> stocRepo;
    private StocService stocService;

    @BeforeEach
    void setUp() throws IOException {
        Path stocFile = tempDir.resolve("stocuri-test.txt");
        Files.createFile(stocFile);

        stocValidator = new StocValidator();
        stocRepo = new FileStocRepository(stocFile.toString());
        stocService = new StocService(stocRepo, stocValidator);
    }

    @Test
    void add_validStoc_persistsInRealRepo() {
        Stoc stoc = new Stoc(1, "Apa", 5.0, 1.0);

        assertDoesNotThrow(() -> stocService.add(stoc));

        assertEquals(1, stocRepo.findAll().size());
        assertEquals(stoc, stocRepo.findOne(1));
        assertEquals(1, stocService.getAll().size());
    }

    @Test
    void add_invalidStoc_doesNotPersistInRealRepo() {
        Stoc stoc = new Stoc(-1, "Apa", 5.0, 10.0);

        assertThrows(ValidationException.class, () -> stocService.add(stoc));

        assertEquals(0, stocRepo.findAll().size());
        assertEquals(0, stocService.getAll().size());
    }

    @Test
    void getAll_returnsAllEntriesFromRealRepo() {
        stocRepo.save(new Stoc(1, "Apa", 5.0, 1.0));
        stocRepo.save(new Stoc(2, "Lapte", 3.0, 1.0));

        assertEquals(2, stocService.getAll().size());
    }
}