package models;

public class TitleModel {
    public enum TitleAction {
        START,
        HOW_TO_PLAY,
        NONE
    }

    public TitleAction getAction(String actionCommand) {
        if ("START".equals(actionCommand)) {
            return TitleAction.START;
        }
        if ("HOW_TO_PLAY".equals(actionCommand)) {
            return TitleAction.HOW_TO_PLAY;
        }
        return TitleAction.NONE;
    }
}
