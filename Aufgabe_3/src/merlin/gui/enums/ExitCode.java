package merlin.gui.enums;

public enum ExitCode {
    DIALOG_ABORTED(0),
	QUIT_DIALOG(0),
    OK_BUTTON_PUSHED(1),
    CANCEL_BUTTON_PUSHED(0),
    LOGIN_BUTTON_PUSHED(1),
    REGISTER_BUTTON_PUSHED(1),
    LOGOUT_BUTTON_PUSHED(1),
    YES_BUTTON_PUSHED(1),
    NO_BUTTON_PUSHED(0),
    COMMIT_AND_QUIT_BUTTON_PUSHED(2),
    QUIT_BUTTON_PUSHED(1);
    
    private int code = 0;

    private ExitCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
