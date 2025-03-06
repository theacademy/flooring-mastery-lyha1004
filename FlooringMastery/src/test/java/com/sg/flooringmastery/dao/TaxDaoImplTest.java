package com.sg.flooringmastery.dao;

import dao.TaxDao;
import dao.TaxDaoImpl;
import dto.Tax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaxDaoImplTest {

    private TaxDao taxDao;

    @BeforeEach
    public void setUp() {
        taxDao = new TaxDaoImpl();
    }

    @Test
    public void testReadAllTaxes() {
        List<Tax> taxes = taxDao.getAllTaxInfo();
        assertNotNull(taxes);
        assertFalse(taxes.isEmpty());
    }

    @Test
    public void testGetStateTax() {
        String state = "CA";
        Tax tax = taxDao.getStateTax(state);
        assertNotNull(tax);
        assertEquals(state, tax.getStateAbbreviation());
    }

    @Test
    public void testGetNonExistentTaxByState() {
        String state = "NonExistent";
        Tax tax = taxDao.getStateTax(state);
        assertNull(tax);
    }
}
