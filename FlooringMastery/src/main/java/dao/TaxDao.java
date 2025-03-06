package dao;

import dto.Tax;

import java.math.BigDecimal;
import java.util.List;

public interface TaxDao {
    List<Tax> getAllTaxInfo();

    Tax getStateTax(String state);

    void unmarshallStateTax();
}
