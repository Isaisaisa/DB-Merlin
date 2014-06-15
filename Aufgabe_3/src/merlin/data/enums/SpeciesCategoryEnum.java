package merlin.data.enums;

public enum SpeciesCategoryEnum {
    ALL(""),
    SPECIES("species"),
    SUBSPECIES("subspecies");
    
    private String value = "";

    private SpeciesCategoryEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
