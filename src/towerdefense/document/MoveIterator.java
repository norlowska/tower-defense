package towerdefense.document;

/**
 * Created by user on 2019-01-17.
 */
public interface MoveIterator<E> {
    public E left();
    public boolean hasLeft();
    public E right();
    public boolean hasRight();
    public E up();
    public boolean hasUp();
    public E down();
    public boolean hasDown();

}