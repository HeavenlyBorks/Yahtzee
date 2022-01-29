import java.util.ArrayList;

public class ModList<E> extends ArrayList<E> {
  /**
  * Checks and returns the amount of times an object {@code e}
  * is repeated inside the ArrayList.
  *
  * @param e - the object to check for inside the ArrayList
  *
  * @return {@code int} containing the number of times the object
  * {@code e} is repeated inside the ArrayList
  */
  public int number_of(E e) {
    int num = 0;
    for (E i : this) {
      if (i == e) {
        num++;
      }
    }
    return num;
  }
}