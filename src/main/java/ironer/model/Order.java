package ironer.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {
    private String identifier;
    private String orderer;
    private LocalDateTime date;
    private Double totalG = 0.0;
    private Double totalR = 0.0;
    private Double totalV = 0.0;
    private static int counter = 1;
    private List<Iron> irons;

    public Order(String identifier, String orderer, LocalDateTime date, List<Iron> irons) {
        this.identifier = identifier;
        this.orderer = orderer;
        this.date = date;
        this.irons = irons;
    }
}
