package com.store.itauseguros.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.HashMap;

public class TariffCalculator {

    private static final Map<String, Double[]> TAX_RATES;

    static {
        TAX_RATES = new HashMap<>();
        TAX_RATES.put("VIDA", new Double[]{0.01, 0.022, 0.0});
        TAX_RATES.put("AUTO", new Double[]{0.055, 0.04, 0.01});
        TAX_RATES.put("VIAGEM", new Double[]{0.02, 0.04, 0.01});
        TAX_RATES.put("RESIDENCIAL", new Double[]{0.04, 0.0, 0.03});
        TAX_RATES.put("PATRIMONIAL", new Double[]{0.05, 0.03, 0.00});
    }

    private TariffCalculator() {
        // Private constructor to hide the implicit public one
    }

    public static double calculateTariffPrice(double basePrice, String category) {
        Double[] taxRates = TAX_RATES.get(category.toUpperCase());
        if (taxRates == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Category not found");
        }

        double rawPrice = basePrice
                + (basePrice * taxRates[0])
                + (basePrice * taxRates[1])
                + (basePrice * taxRates[2]);

        BigDecimal bd = new BigDecimal(Double.toString(rawPrice));
        bd = bd.setScale(2, RoundingMode.DOWN);
        return bd.doubleValue();
    }
}