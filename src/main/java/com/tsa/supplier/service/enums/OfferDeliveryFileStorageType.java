package com.tsa.supplier.service.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum OfferDeliveryFileStorageType {

    EMAIL((byte)1), FTP((byte)2);

    private static final Map<Byte, OfferDeliveryFileStorageType> LOOK_UP = new HashMap<>();

    static {
        for(OfferDeliveryFileStorageType obj : EnumSet.allOf(OfferDeliveryFileStorageType.class))
            LOOK_UP.put(obj.getValue(), obj);
    }

    private byte value;

    OfferDeliveryFileStorageType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static OfferDeliveryFileStorageType getOfferDeliveryFileStorageType(byte value) {
        return LOOK_UP.get(value);
    }

}