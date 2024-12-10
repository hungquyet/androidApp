package com.example.callapitourdulich.enums;

public enum TourType {

    ONE_DAY("1 ngày"),  // "1 day" được gán là giá trị của enum
    MULTI_DAY("Nhiều ngày");

    private final String value;

    TourType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    // Phương thức static để chuyển đổi giá trị chuỗi thành enum
    public static TourType fromString(String value) {
        for (TourType tourType : TourType.values()) {
            if (tourType.value.equalsIgnoreCase(value)) {
                return tourType;
            }
        }
        throw new IllegalArgumentException("Invalid tour type: " + value);
    }
}
