package ru.vsu.cs.Person.Step;

public enum StageStep
{
    TAKE_FIGURE("TAKE_FIGURE"),
    MOVE_FIGURE("MOVE_FIGURE"),
    ENDGAME("ENDGAME");

    public String getName() {
        return name;
    }

    public static final String SEPARATOR = ":";

    private final String name;
    StageStep(String name)
    {
        this.name=name;
    }

}
