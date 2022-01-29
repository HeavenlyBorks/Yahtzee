import com.diogonunes.jcolor.Attribute;
import com.diogonunes.jcolor.AnsiFormat;
import static com.diogonunes.jcolor.Ansi.*;
import static com.diogonunes.jcolor.Attribute.*;

import java.util.Scanner;

public class GameManager {
  // colors
  public static final AnsiFormat kept = new AnsiFormat(TEXT_COLOR(88));
  public static final AnsiFormat game = new AnsiFormat(TEXT_COLOR(40));
  public static final AnsiFormat game_dark = new AnsiFormat(TEXT_COLOR(28));
  public static final AnsiFormat roll_color = new AnsiFormat(TEXT_COLOR(33));

  public ComboManager combos = new ComboManager();
  private Die die = new Die();
  Scanner input = new Scanner(System.in);
  public boolean over = false;

  public boolean turn() {
    ModList<Integer> rolls = new ModList<Integer>();
    ModList<Integer> keepers = new ModList<Integer>();

    // first roll
    System.out.println(roll_color.format("---------- FIRST ROLL ----------"));
    System.out.print(game.format("Press enter to roll your ") + game_dark.format("dice™: "));
    input.nextLine();
    for (int i = 0; i < 5; i++) {
      rolls.add(die.roll());
    }

    // first roll menu
    boolean m = menu(keepers, rolls);
    if (m == true) {
      return true;
    }

    // second roll
    System.out.println(roll_color.format("\n---------- SECOND ROLL ----------"));
    System.out.print(game.format("Press enter to roll your ") + game_dark.format("dice™: "));
    roll(keepers, rolls);
    input.nextLine();

    // second roll menu
    m = menu(keepers, rolls);
    if (m == true) {
      return true;
    }

    // third roll
    System.out.println(roll_color.format("\n---------- THIRD ROLL ----------"));
    System.out.print(game.format("Press enter to roll your ") + game_dark.format("dice™: "));
    roll(keepers, rolls);
    input.nextLine();

    // third roll menu
    m = menu(keepers, rolls);
    while (m != true) {
      System.out.println(game.format("Can't roll again. Please choose a combination from the score board.\n(You can get to the board by pressing 4 in the menu)"));
      m = menu(keepers, rolls);
    }

    return true;
  }

  // roll sequence (after the first one)
  private void roll(ModList<Integer> keepers, ModList<Integer> rolls) {
    for (int i = 0; i < 5; i++) {
      if (keepers.contains(i) != true) {
        rolls.set(i, die.roll());
      }
    }
  }

  // add to keeps list sequence
  private void keep(ModList<Integer> keepers, ModList<Integer> rolls) {
    int keep = 1;
    // System.out.println(game.format("\nHere's a list of your rolls:"));
    // print_rolls(keepers, rolls);
    System.out.println(game.format("Please input the number of a roll to keep.") + game_dark.format(" (Choose 0 to go back to the menu.)\nDon't worry, you'll be able to choose more afterwards."));
    keep = input.nextInt();
    input.nextLine();
    if (keep != 0) {
        if (keepers.contains(keep - 1) == false) {
          keepers.add(keep - 1);
        } else {
          System.out.println(game.format("Invalid selection, please try again."));
        }
    }
    while (keep != 0) {
      System.out.println(game.format("Anything else?"));
      keep = input.nextInt();
      input.nextLine();
      if (keep != 0) {
        if (keepers.contains(keep - 1) == false) {
          keepers.add(keep - 1);
        } else {
          System.out.println(game.format("Invalid selection, please try again."));
        }
      }
    }
  }

  // remove from keeps list sequence
  private void remove(ModList<Integer> keepers, ModList<Integer> rolls) {
    int rem = 1;
    System.out.println(game.format("\nHere's a list of your keepers: "));
    print_keepers(keepers, rolls);
    System.out.println(game.format("Please input the number of a keeper you want to remove.") + game_dark.format(" (Choose 0 to go back to the menu.)\nDon't worry, you'll be able to choose more afterwards."));
    rem = input.nextInt();
    input.nextLine();
    if (rem != 0) {
      if (rem - 1 < keepers.size() && keepers.get(rem - 1) != null) {
        keepers.set(rem - 1, null);
      } else {
        System.out.println(game.format("Invalid selection, please try again."));
      }
    }
    while (rem != 0) {
      System.out.println(game.format("Anything else?"));
      rem = input.nextInt();
      input.nextLine();
      if (rem != 0) {
        if (rem - 1 < keepers.size() && keepers.get(rem - 1) != null) {
          keepers.set(rem - 1, null);
        } else {
          System.out.println(game.format("Invalid selection, please try again."));
        }
      }
    }
    rolls.removeIf(n -> (n == null));
  }

  // prints your current rolls
  private void print_rolls(ModList<Integer> keepers, ModList<Integer> rolls) {
    System.out.println(game.format("\nHere are your rolls: "));
    for (int i = 0; i < rolls.size(); i++) {
      if (keepers.contains(i) == true) {
        System.out.println(kept.format(Integer.toString(i + 1) + ". " + Integer.toString(rolls.get(i))));
      }
      else {
        System.out.println(game_dark.format(Integer.toString(i + 1) + ". " + Integer.toString(rolls.get(i))));
      }
    }
  }

  // prints the dice you're currently keeping
  private void print_keepers(ModList<Integer> keepers, ModList<Integer> rolls) {
    for (int i = 0; i < keepers.size(); i++) {
      System.out.println(game_dark.format(Integer.toString(i + 1) + ". " + Integer.toString(rolls.get(keepers.get(i)))));
    }
  }

  // interactive menu
  private boolean menu(ModList<Integer> keepers, ModList<Integer> rolls) {
    int menu = 1;
    while (menu != 0) {
      print_rolls(keepers, rolls);
      System.out.println(game.format("\nWhat would you like to do?"));
      System.out.println(game_dark.format("1. Go to the next roll\n2. Add rolls to keeps list\n3. Remove rolls from keeps list\n4. Mark a combination on your score board"));
      menu = input.nextInt() - 1;
      input.nextLine();
      if (menu == 1) {
        keep(keepers, rolls);
      } else if (menu == 2 && keepers.size() != 0) {
        remove(keepers, rolls);
      } else if (menu == 3) {
        combos.print_table(rolls);
        int chose = combos.select_combo(rolls);
        if (chose != 0) {
          if (combos.scores.number_of(null) == 0) {
            over = true;
          }
          return true;
        }
      } else if (menu == 0) {;} else {
        System.out.println(game.format("Invalid selection, please try again."));
      }
    }
    return false;
  }
}