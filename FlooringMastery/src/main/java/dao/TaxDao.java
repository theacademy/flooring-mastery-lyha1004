package dao;

import dto.Tax;

import java.util.List;

public interface TaxDao {
    List<Tax> getAllTaxInfo();
    Tax getStateTax(String state);
    void unmarshallStateTax() throws TaxFileIOException;
}
