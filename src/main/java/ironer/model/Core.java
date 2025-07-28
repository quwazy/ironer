package ironer.model;

import ironer.model.enums.IronType;

public class Core {
    /// GLATKO GVOZDJE
    public static final double G6 = 0.222;
    public static final double G8 = 0.395;
    public static final double G10 = 0.63;
    /// REBRASTO GVOZDJE
    public static final double R6 = 0.23;
    public static final double R8 = 0.41;
    public static final double R10 = 0.65;
    public static final double R12 = 0.92;
    public static final double R14 = 1.252;
    public static final double R16 = 1.64;

    public static double getRoundNumber(double number){
        return Math.round(number * 100.0) / 100.0;
    }

    public static double getWeight(IronType type) {
        return switch (type.toString()) {
            case "G6" -> G6;
            case "G8" -> G8;
            case "G10" -> G10;
            case "R6" -> R6;
            case "R8" -> R8;
            case "R10" -> R10;
            case "R12" -> R12;
            case "R14" -> R14;
            case "R16" -> R16;
            default -> 0;
        };
    }
}
