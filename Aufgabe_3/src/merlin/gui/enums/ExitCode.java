package merlin.gui.enums;

public enum ExitCode {
    DIALOG_ABORTED(0),
    OK_BUTTON_PUSHED(1),
    ABORT_BUTTON_PUSHED(2),
    LOGIN_BUTTON_PUSHED(3),
    REGISTER_BUTTON_PUSHED(4);

    private int code = 0;

    private ExitCode(int code) {
        this.code = code;
    }

    public double code() {
        return code;
    }
}
