package com.tsa.supplier.service.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum OfferDeliveryFileFormat {

    CSV((byte)1), TXT((byte)2), XLSX((byte)3);

    private static final Map<Byte, OfferDeliveryFileFormat> LOOK_UP = new HashMap<>();

    static {
        for(OfferDeliveryFileFormat obj : EnumSet.allOf(OfferDeliveryFileFormat.class))
            LOOK_UP.put(obj.getValue(), obj);
    }

    private byte value;

    OfferDeliveryFileFormat(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static OfferDeliveryFileFormat getOfferDeliveryFileFormat(byte value) {
        return LOOK_UP.get(value);
    }

}