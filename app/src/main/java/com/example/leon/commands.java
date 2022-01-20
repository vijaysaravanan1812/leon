package com.example.leon;

public class commands
{
    String txtTime   ;
    String txtCommand;

    public commands(String txtTime, String txtCommand) {
        this.txtTime = txtTime;
        this.txtCommand = txtCommand;
    }

    public String getTxtTime() {
        return txtTime;
    }

    public String getTxtCommand() {
        return txtCommand;
    }

    @Override
    public String toString() {
        return txtTime + "                " + txtCommand;
    }
}
