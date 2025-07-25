package ironer.controller;

import lombok.Getter;

@Getter
public class ActionManager {
    private ClearListAction clearListAction;
    private PrintController printController;

    public ActionManager() {
        clearListAction = new ClearListAction();
        printController = new PrintController();
    }
}
