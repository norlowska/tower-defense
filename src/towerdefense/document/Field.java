package towerdefense.document;

public abstract class Field {

    protected boolean start;
    protected boolean finish;
    protected Color color;
    protected Point point;

    public Field(boolean start, boolean finish, Color color, Point point) {
        this.start = start;
        this.finish = finish;
        this.color = color;
        this.point = point;
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

    public boolean isRoad(){return false;}

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Point getPoint(){return point;}
}
