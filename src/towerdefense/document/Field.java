package towerdefense.document;

public abstract class Field {

    protected boolean start;
    protected boolean finish;
    protected String color;

    public Field(boolean start, boolean finish, String color) {
        this.start = start;
        this.finish = finish;
        this.color = color;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
