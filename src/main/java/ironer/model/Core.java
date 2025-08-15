package ironer.model;

import ironer.model.enums.IronType;

public class Core {
    /// GLATKO GVOZDJE
    public static final double G6 = 0.222;
    public static final double G8 = 0.395;
    /// REBRASTO GVOZDJE
    public static final double R6 = 0.23;
    public static final double R8 = 0.41;
    public static final double R10 = 0.65;
    public static final double R12 = 0.92;
    public static final double R14 = 1.252;
    public static final double R16 = 1.64;
    public static final double R18 = 2.07;
    public static final double R20 = 2.568;
    public static final double R22 = 3.092;
    public static final double R25 = 3.85;
    public static final double R28 = 4.95;
    /// MREZE
    private static final int F4 = 18;
    private static final int F5 = 28;
    private static final int F6 = 40;
    private static final int F65 = 47;
    private static final int F7 = 54;
    private static final int F75 = 62;
    private static final int F8 = 71;
    private static final int F9 = 92;
    private static final int F10 = 111;

    public static double getRoundNumber(double number){
        return Math.round(number * 100.0) / 100.0;
    }

    public static double getWeight(IronType type) {
        return switch (type.toString()) {
            case "G6" -> G6;
            case "G8" -> G8;
            case "R6" -> R6;
            case "R8" -> R8;
            case "R10" -> R10;
            case "R12" -> R12;
            case "R14" -> R14;
            case "R16" -> R16;
            case "R18" -> R18;
            case "R20" -> R20;
            case "R22" -> R22;
            case "R25" -> R25;
            case "R28" -> R28;
            case "F4" -> F4;
            case "F5" -> F5;
            case "F6" -> F6;
            case "F65" -> F65;
            case "F7" -> F7;
            case "F75" -> F75;
            case "F8" -> F8;
            case "F9" -> F9;
            case "F10" -> F10;
            default -> 0;
        };
    }
}
