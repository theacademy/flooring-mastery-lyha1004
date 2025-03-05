package dao;

import dto.Tax;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TaxDaoImpl implements TaxDao {
    private static final String TAX_FILE = "Taxes.txt";
    private static final String DELIMITER = ",";
    private List<Tax> taxes = new ArrayList<>();

    @Override
    public List<Tax> getAllTaxInfo() {
        return new ArrayList<>();
    }

    @Override
    public BigDecimal getStateTax(String state) {
        // Implementation here
        return null;
    }

    @Override
    public Tax unmarshallStateTax(String stateTaxInfo) {
        // Implementation here
        return null;
    }

    @Override
    public void loadTaxInfo() {
        // Implementation here
    }

    @Override
    public Tax getTaxByState(String state) {
        return null;
    }
}