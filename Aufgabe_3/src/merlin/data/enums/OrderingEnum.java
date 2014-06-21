package merlin.data.enums;

public enum OrderingEnum {
    NONE(""),
    ASCENDING("ASC"),
    DESCENDING("DESC");
    
    
    private String value = "";

    private OrderingEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
