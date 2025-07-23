package ironer.model.irons;

import ironer.model.enums.IronShape;
import ironer.model.enums.IronType;
import javafx.scene.shape.Shape;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Iron {
    private IronShape ironShape;
    private IronType ironType;

    public Iron() {}

    public Iron(IronShape ironShape, IronType ironType) {
        this.ironShape = ironShape;
        this.ironType = ironType;
    }

    public abstract Shape getDraw();
}
