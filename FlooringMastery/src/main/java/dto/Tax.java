package dto;

import java.math.BigDecimal;

public class Tax {
    private String stateAbbreviation;
    private String stateName;
    private BigDecimal taxRate;

    public Tax(String stateAbbreviation, BigDecimal taxRate) {
        this.stateAbbreviation = stateAbbreviation;
        this.taxRate = taxRate;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public String getStateName() {
        return stateName;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }
}
