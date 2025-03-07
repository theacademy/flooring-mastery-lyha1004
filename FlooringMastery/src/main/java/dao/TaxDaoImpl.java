package dao;

import dto.Tax;
import service.TaxFileIOException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaxDaoImpl implements TaxDao {
    private static final String TAX_FILE = "/SampleFileData/Data/Taxes.txt";
    private static final String DELIMITER = ",";
    private Map<String, Tax> taxes = new HashMap<>();

    public TaxDaoImpl() {
        unmarshallStateTax();
    }

    @Override
    public List<Tax> getAllTaxInfo() {
        return new ArrayList<>(taxes.values());
    }

    @Override
    public Tax getStateTax(String state) {
        return taxes.get(state);
    }

    @Override
    public void unmarshallStateTax() throws TaxFileIOException{
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(TAX_FILE)))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(DELIMITER);
                if (tokens.length == 3) {
                    try {
                        String stateAbbreviation = tokens[0];
                        BigDecimal taxRate = new BigDecimal(tokens[2]);

                        Tax tax = new Tax(stateAbbreviation, taxRate);
                        taxes.put(stateAbbreviation, tax);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format in line: " + line);
                    }
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            throw new TaxFileIOException("Could not load tax data.", e);
        }
    }


}