package service;

import dao.ProductsFileIOException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import dao.TaxFileIOException;

class ExceptionTest {

    @Test
    void testFlooringPersistenceException() {
        Exception exception = assertThrows(FlooringPersistenceException.class, () -> {
            throw new FlooringPersistenceException("File not found");
        });

        assertEquals("File not found", exception.getMessage());
    }

    @Test
    void testTaxFileIOException() {
        Exception exception = assertThrows(TaxFileIOException.class, () -> {
            throw new TaxFileIOException("Tax file is corrupted");
        });

        assertEquals("Tax file is corrupted", exception.getMessage());
    }

    @Test
    void testProductsFileIOException() {
        Exception exception = assertThrows(ProductsFileIOException.class, () -> {
            throw new ProductsFileIOException("Products file missing");
        });

        assertEquals("Products file missing", exception.getMessage());
    }


}
