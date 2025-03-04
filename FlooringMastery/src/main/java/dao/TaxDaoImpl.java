package dao;

import dto.Tax;

import java.math.BigDecimal;
import java.util.List;

public class TaxDaoImpl implements TaxDao{
    @Override
    public List<Tax> getAllTaxInfo() {
        return List.of();
    }

    @Override
    public BigDecimal getStateTax(String state) {
        return null;
    }

    @Override
    public Tax unmarshallStateTax(String stateTaxInfo) {
        return null;
    }

    @Override
    public void loadTaxInfo() {

    }
}
