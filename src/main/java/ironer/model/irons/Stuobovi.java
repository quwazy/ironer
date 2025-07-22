package ironer.model.irons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ironer.model.enums.IronShape;
import ironer.model.enums.IronType;
import javafx.scene.control.TableColumn;

@Data
@EqualsAndHashCode(callSuper = true)
public class Stuobovi extends Iron{
    public Stuobovi(IronShape ironShape, IronType ironType) {
        super(ironShape, ironType);
    }

    @Override
    public TableColumn<Void, Void> getDraw() {
        return null;
    }
}
