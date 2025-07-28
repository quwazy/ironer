package ironer.model;

import javafx.collections.ObservableList;
import lombok.Data;
import ironer.model.irons.Iron;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {
    private String identifier;
    private String orderer;
    private LocalDateTime date;
    private List<Iron> irons;
    private double totalG = 0.0;
    private double totalR = 0.0;
    private double totalV = 0.0;

    public Order(String identifier, String orderer, LocalDateTime date, ObservableList<Iron> irons) {
        this.identifier = identifier;
        this.orderer = orderer;
        this.date = date;
        this.irons = irons.stream().toList();
    }
}
