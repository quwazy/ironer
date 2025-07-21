package ironer.model;

import ironer.model.enums.IronShape;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public Order(String identifier, String orderer, LocalDateTime date) {
        this.identifier = identifier;
        this.orderer = orderer;
        this.date = date;
        this.irons = new ArrayList<>();
    }

    public void addIron(Iron iron) {
        this.irons.add(iron);
        if (iron.getIronShape().equals(IronShape.SIPKE)){
            totalR += totalR + iron.getWeight();
        }
        else if (iron.getIronShape().equals(IronShape.UZENGIJE)){
            totalG += totalG + iron.getWeight();
        }
        else if (iron.getIronShape().equals(IronShape.STUBOVI)){
            totalV += totalV + iron.getWeight();
        }
    }

    public void removeIron(Iron iron) {
        this.irons.remove(iron);
        if (iron.getIronShape().equals(IronShape.SIPKE)){
            totalR -= totalR + iron.getWeight();
        }
        else if (iron.getIronShape().equals(IronShape.UZENGIJE)){
            totalG -= totalG + iron.getWeight();
        }
        else if (iron.getIronShape().equals(IronShape.STUBOVI)){
            totalV -= totalV + iron.getWeight();
        }
    }
}
