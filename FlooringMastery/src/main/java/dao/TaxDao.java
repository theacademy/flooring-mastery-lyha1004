package dao;

import dto.Tax;

import java.math.BigDecimal;
import java.util.List;

public interface TaxDao {
    List<Tax> getAllTaxInfo();
    BigDecimal getStateTax(String state);
    Tax unmarshallStateTax(String stateTaxInfo);
    void loadTaxInfo();
}
