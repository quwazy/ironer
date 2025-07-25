package ironer.controller;

import ironer.model.irons.Iron;
import javafx.collections.ObservableList;

public class ClearListAction {

    public void clearList(ObservableList<Iron> list) {
        list.clear();
    }
}
