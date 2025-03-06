package com.sg.flooringmastery.dao;

import dao.TaxDao;
import dto.Tax;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class TaxDaoStubImpl implements TaxDao {
    private List<Tax> taxes = Arrays.asList(
            new Tax("TX", new BigDecimal("6.25")),
            new Tax("CA", new BigDecimal("7.50"))
    );

    @Override
    public Tax getStateTax(String state) {
        return taxes.stream()
                .filter(tax -> tax.getStateAbbreviation().equalsIgnoreCase(state))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void unmarshallStateTax() {

    }

    @Override
    public List<Tax> getAllTaxInfo() {
        return taxes;
    }
}
