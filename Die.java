import java.util.Random;
/*
* Create a Die class (as in a playing die)
*/
public class Die
{
    //instance variable(s)
    private int value;

    //constructor(s)
    public Die()
    {
      Random gen = new Random();
      value = gen.nextInt(6) + 1;
    }

    //accessor(s)
    public int getValue()
    {
      return value;
    }

    //mutator(s)
    public int roll()
    {
      Random gen = new Random();
      value = gen.nextInt(6) + 1;
      return value;
    }
}