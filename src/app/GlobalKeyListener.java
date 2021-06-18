package app;

import javafx.application.Platform;
import model.WOLF;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import view.GameView;

public class GlobalKeyListener implements NativeKeyListener {
    private boolean q = false, shift = false, ctrl = false;
    private GameView game;

    public GlobalKeyListener(GameView game) {
        this.game = game;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        moveWolf(e);
        showTime(e);
        exitShortcut(e);
    }

    private void moveWolf(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_A) {
            game.getWolf().setImage(WOLF.UP_LEFT.getImgWolf());
            game.getBasket().setUpLeft();
            game.getControlBtns().get(0).fire();
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_Z) {
            game.getWolf().setImage(WOLF.DOWN_LEFT.getImgWolf());
            game.getBasket().setDownLeft();
            game.getControlBtns().get(1).fire();
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_QUOTE) {
            game.getWolf().setImage(WOLF.UP_RIGHT.getImgWolf());
            game.getBasket().setUpRight();
            game.getControlBtns().get(2).fire();
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_SLASH) {
            game.getWolf().setImage(WOLF.DOWN_RIGHT.getImgWolf());
            game.getBasket().setDownRight();
            game.getControlBtns().get(3).fire();
        }
    }

    private void showTime(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_P) {
            game.getTimeBtn().fire();
        }
    }

    private void exitShortcut(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_Q) {
            q = true;
            if (shift) {
                if (ctrl) {
                    Platform.runLater(() -> {
                        game.endGame();
                    });                }
            }
        } else if (e.getKeyCode() == NativeKeyEvent.VC_SHIFT) {
            shift = true;
            if (q) {
                if (ctrl) {
                    Platform.runLater(() -> {
                        game.endGame();
                    });                }
            }
        } else if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            ctrl = true;
            if (q) {
                if (shift) {
                    Platform.runLater(() -> {
                        game.endGame();
                    });

                }
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_Q) {
            q = false;
        } else if (e.getKeyCode() == NativeKeyEvent.VC_SHIFT) {
            shift = false;
        } else if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            ctrl = false;
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        //System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }
}