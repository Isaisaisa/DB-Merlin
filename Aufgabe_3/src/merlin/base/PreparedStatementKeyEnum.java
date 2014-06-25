package merlin.base;

public enum PreparedStatementKeyEnum {
    REGISTER_BIRDWATCHER(0),
    GET_BW_ID(1),
    GET_USER_ROLE(2),
    ADD_OBSERVATION_MOMENT(3),
    ADD_OBSERVATION_PERIOD(4),
    ADD_LOCATION(5),
    ADD_BIRD(6),
    UPDATE_BIRD(7),
    UPDATE_LOCATION(8),
    LOGIN_TO_MERLIN(9);
    
    private int key = 0;

    private PreparedStatementKeyEnum(int key) {
        this.key = key;
    }

    public int key() {
        return key;
    }
}
