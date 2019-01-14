package towerdefense.document;

public abstract class Field {

    protected boolean start;
    protected boolean finish;
    protected Color color;

    public Field(boolean start, boolean finish, Color color) {
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
