package com.tsa.supplier.service.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum OfferDeliveryType {

    FILE((byte)1), API((byte)2);

    private static final Map<Byte, OfferDeliveryType> LOOK_UP = new HashMap<>();

    static {
        for(OfferDeliveryType method : EnumSet.allOf(OfferDeliveryType.class))
            LOOK_UP.put(method.getValue(), method);
    }

    private byte value;

    OfferDeliveryType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static OfferDeliveryType getOfferDeliveryType(byte value) {
        return LOOK_UP.get(value);
    }

}