package dto;

public enum WindDirection {
    NORTH("Север"),
    NORTH_EAST("Северо-восток"),
    EAST("Восток"),
    SOUTH_EAST("Юго-восток"),
    SOUTH("Юг"),
    SOUTH_WEST("Юго-запад"),
    WEST("Запад"),
    NORTH_WEST("Северо-запад");
    private String russianValue;

    WindDirection(String russianValue) {
        this.russianValue = russianValue;
    }

    public static String convertDegreesToDirection(double windDeg) {
        if (windDeg >= 340 || windDeg <= 20) {
            return WindDirection.NORTH.getRussianValue();
        } else if (windDeg >= 70 && windDeg <= 110) {
            return WindDirection.EAST.getRussianValue();
        } else if (windDeg >= 160 && windDeg <= 200) {
            return WindDirection.SOUTH.getRussianValue();
        } else if (windDeg >= 250 && windDeg <= 290) {
            return WindDirection.WEST.getRussianValue();
        } else if (windDeg < 70) {
            return WindDirection.NORTH_EAST.getRussianValue();
        } else if (windDeg < 160) {
            return WindDirection.SOUTH_EAST.getRussianValue();
        } else if (windDeg < 250) {
            return WindDirection.SOUTH_WEST.getRussianValue();
        } else {
            return WindDirection.NORTH_WEST.getRussianValue();
        }
    }

    private String getRussianValue() {
        return russianValue;
    }
}

