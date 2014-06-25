package merlin.base;

public enum PreparedStatementKeyEnum {
    COREDATA_FILTERED_ORDERED(0),
    OBSERVATION_ALL(1), //? nötig?
    OBSERVATION_FILTERED_ORDERED(2),
    CHECKLIST_FILTERED(3),
    REGISTER_BIRDWATCHER(4),
    GET_BW_ID(5),
    GET_USER_ROLE(6),
    ADD_OBSERVATION_MOMENT(7),
    ADD_OBSERVATION_PERIOD(8),
    ADD_LOCATION(9),
    GET_LOCATIONS(10),
    ADD_BIRD(11);
    
    
    private int key = 0;

    private PreparedStatementKeyEnum(int key) {
        this.key = key;
    }

    public int key() {
        return key;
    }
}
