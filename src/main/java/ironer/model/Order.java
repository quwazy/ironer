package ironer.model;

import lombok.Data;
import ironer.model.irons.Sipke;
import ironer.model.irons.Stubovi;
import ironer.model.irons.Uzengije;
import ironer.model.irons.Iron;
import javafx.collections.ObservableList;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {
    private String identifier;      //jedinstvena oznaka
    private String orderer;         //narucilac
    private LocalDateTime date;     //datum
    private List<Iron> irons;       //naruceno gvozdje
    private double totalG = 0.0;
    private double totalR = 0.0;
    private double totalV = 0.0;

    public Order(String identifier, String orderer, LocalDateTime date) {
        this.identifier = identifier;
        this.orderer = orderer;
        this.date = date;
    }

    public void addObservableList(ObservableList<Iron> irons) {
        this.irons = irons.stream().toList();
        calculateTotal();
    }

    public void calculateTotal() {
        this.totalG = 0.0;
        this.totalR = 0.0;
        this.totalV = 0.0;

        for (Iron iron : irons) {
            if (iron instanceof Stubovi){
                totalV += ((Stubovi) iron).getWeight();
                continue;
            }
            if (iron instanceof Uzengije){
                totalG += ((Uzengije) iron).getWeight();
                continue;
            }
            if (iron instanceof Sipke){
                totalR += ((Sipke) iron).getWeight();
            }
        }
    }
}
