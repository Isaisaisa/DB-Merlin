package merlin.base;

public enum PreparedStatementKeyEnum {
    COREDATA_FILTERED_ORDERED(0),
    OBSERVATION_ALL(1), //? nötig?
    OBSERVATION_FILTERED_ORDERED(2),
    CHECKLIST_FILTERED(3);
    
    private int key = 0;

    private PreparedStatementKeyEnum(int key) {
        this.key = key;
    }

    public int key() {
        return key;
    }
}
