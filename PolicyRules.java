package com.example.enumbit;

import java.util.EnumSet;
import java.util.List;

public enum PolicyRules {
    NO_RULE, EDIT_A, READ_A, EDIT_B, READ_B;

    public final long getValue() {
        return ordinal() <= 1
                ? ordinal()
                : 2 << (ordinal() - 2);
    }

    public static final long MAX_VALUE = (2L << PolicyRules.values().length - 2) - 1;

    public static Iterable<PolicyRules> getRules(long value) throws Exception {
        if(value < 0 || value > MAX_VALUE) {
            throw new Exception("Bad value");
        }

        return value == 0
                ? List.of(PolicyRules.NO_RULE)
                : getListRules(value);
    }

    public static long getValue(Iterable<PolicyRules> rules) {
        if(rules == null) 
            throw new NullPointerException("rules1 or rules2 cannot be null");
        
        long result = 0L;

        for (PolicyRules r : rules)
            result |= r.getValue();

        return result;
    }

    public static Iterable<PolicyRules> compare(Iterable<PolicyRules> rules1, Iterable<PolicyRules> rules2) {
        if(rules1 == null || rules2 == null) 
            throw new NullPointerException("rules1 or rules2 cannot be null");
        
        return getListRules(getValue(rules1) & getValue(rules2));
    }

    private static Iterable<PolicyRules> getListRules(long value) {
        return EnumSet.allOf(PolicyRules.class)
                .stream()
                .filter(r -> (value & r.getValue()) > 0)
                .toList();
    }
}
