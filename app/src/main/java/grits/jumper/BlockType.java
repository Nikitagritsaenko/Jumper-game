package grits.jumper;

public enum BlockType {
    START,
    END,
    DESTROYABLE_1,
    DESTROYABLE_2,
    SPRING,
    PORTAL,
    PORTAL_REUSABLE,
    LR_REVERSER,
    GRAVITY_REVERSER,
    ENDGAME_BONUS,
    EMPTY;

    boolean isPortal() {
        return this == BlockType.PORTAL || this == BlockType.PORTAL_REUSABLE;
    }
}

