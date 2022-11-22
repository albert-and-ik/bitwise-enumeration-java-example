package com.example.enumbit;

import lombok.NonNull;

import java.util.EnumSet;
import java.util.List;

public enum PolicyRules {
    NO_RULE, EDIT_A, READ_A, EDIT_B, READ_B;

    public final long getValue() {
        if(ordinal() <= 1) return ordinal();

        return 2 << (ordinal() - 2);
    }

    public static final long MAX_VALUE = (2L << PolicyRules.values().length - 2) - 1;

    public static List<PolicyRules> getRules(long value) throws Exception {
        if(value < 0 || value > MAX_VALUE) {
            throw new Exception("Bad value");
        }

        if(value == 0) {
            return List.of(PolicyRules.NO_RULE);
        }

        return getListRules(value);
    }

    public static long getValue(@NonNull List<PolicyRules> rules) {

        return rules.stream()
                .map(PolicyRules::getValue)
                .reduce((a, b) -> a | b)
                .orElse(0L);
    }

    public static List<PolicyRules> compare(
            @NonNull List<PolicyRules> rules1,
            @NonNull List<PolicyRules> rules2
    ) {
        return getListRules(getValue(rules1) & getValue(rules2));
    }

    private static List<PolicyRules> getListRules(long value) {
        return  EnumSet.allOf(PolicyRules.class).stream()
                .filter(rule -> (value & rule.getValue()) > 0)
                .toList();
    }

}
