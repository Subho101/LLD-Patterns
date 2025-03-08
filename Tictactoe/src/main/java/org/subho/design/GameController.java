package org.subho.design;

public class GameController {
    private static GameController instance;

    private GameController() {}

    public static GameController getInstance() {

        synchronized (GameController.class) {
            if(null == instance) {
                instance = new GameController();
            }
        }
        return instance;
    }
}
