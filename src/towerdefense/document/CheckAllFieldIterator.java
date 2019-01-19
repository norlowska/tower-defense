package towerdefense.document;

import java.util.Iterator;

/**
 * Created by user on 2019-01-17.
 */
public interface CheckAllFieldIterator<E> {
    E next();
    boolean hasNext();
    Point fieldPoint();
    E nextMove();

}