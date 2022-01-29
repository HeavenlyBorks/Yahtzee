import com.diogonunes.jcolor.Attribute;
import com.diogonunes.jcolor.AnsiFormat;
import static com.diogonunes.jcolor.Ansi.*;
import static com.diogonunes.jcolor.Attribute.*;

import java.util.HashMap;
import java.util.Collections;
import java.util.Scanner;

public class ComboManager {
  public final static AnsiFormat npossible = new AnsiFormat(TEXT_COLOR(207));
  public final static AnsiFormat possible = new AnsiFormat(TEXT_COLOR(207), UNDERLINE());
  public final static AnsiFormat ncombo = new AnsiFormat(TEXT_COLOR(199));
  public final static AnsiFormat combo_dark = new AnsiFormat(TEXT_COLOR(127));
  public final static AnsiFormat combo = new AnsiFormat(TEXT_COLOR(199), UNDERLINE());
  public final static AnsiFormat modifier = new AnsiFormat(TEXT_COLOR(163));

  Scanner input = new Scanner(System.in);

  public ModList<Integer> scores = new ModList<Integer>();

  public String choice = "";

  public int bonus = 0;

  public ComboManager() {
    for (int i = 0; i < 13; i++) {
      scores.add(null);
    }
  }

  public int select_combo(ModList<Integer> rolls) {
    int chose = 0;
    System.out.println(ncombo.format("\nWould you like to mark a combination?") + combo_dark.format("\n(Choose 0 to go back)"));
    int choose = input.nextInt();
    input.nextLine();
    if (choose != 0) {
      if (scores.get(choose - 1) == null) {
        scores.set(choose - 1, to_score(choose, rolls));
        chose = 1;
        if (yahtzee(rolls) != "0" && scores.get(11) == 50) {
          bonus += 100;
        }
      } else {
        System.out.println(ncombo.format("Invalid selection, please try again."));
      }
    }
    while (choose != 0 && chose == 0) {
      System.out.println(ncombo.format("Anything else?"));
      choose = input.nextInt();
      input.nextLine();
      if (choose != 0) {
        if (scores.get(choose - 1) == null) {
          scores.set(choose - 1, to_score(choose, rolls));
          chose = 1;
        } else {
          System.out.println(ncombo.format("Invalid selection, please try again."));
        }
      }
    }
    return chose;
  }

  public void print_table(ModList<Integer> rolls) {
    // pre table
    System.out.println(ncombo.format("\nColor key:\nHot pink - set score") + npossible.format("\nLight pink - possible score (based on your current rolls)"));

    // start of table
    System.out.println("\n" + combo.format("Combination    | Score"));

    // ones                        Combination    |
    System.out.print(combo.format("1. Ones        |"));
    if (scores.get(0) != null) { System.out.print(combo.format(" " + String.format("%-4s ", scores.get(0)))); } else { System.out.print(possible.format(" " + String.format("%-4s ", ones(rolls)))); }

    // twos
    System.out.print(combo.format("\n2. Twos        |"));
    if (scores.get(1) != null) { System.out.print(combo.format(" " + String.format("%-4s ", scores.get(1)))); } else { System.out.print(possible.format(" " + String.format("%-4s ", twos(rolls)))); }

    // threes
    System.out.print(combo.format("\n3. Threes      |"));
    if (scores.get(2) != null) { System.out.print(combo.format(" " + String.format("%-4s ", scores.get(2)))); } else { System.out.print(possible.format(" " + String.format("%-4s ", threes(rolls)))); }

    // fours
    System.out.print(combo.format("\n4. Fours       |"));
    if (scores.get(3) != null) { System.out.print(combo.format(" " + String.format("%-4s ", scores.get(3)))); } else { System.out.print(possible.format(" " + String.format("%-4s ", fours(rolls)))); }

    // fives
    System.out.print(combo.format("\n5. Fives       |"));
    if (scores.get(4) != null) { System.out.print(combo.format(" " + String.format("%-4s ", scores.get(4)))); } else { System.out.print(possible.format(" " + String.format("%-4s ", fives(rolls)))); }

    // sixes
    System.out.print(combo.format("\n6. Sixes       |"));
    if (scores.get(5) != null) { System.out.print(combo.format(" " + String.format("%-4s ", scores.get(5)))); } else { System.out.print(possible.format(" " + String.format("%-4s ", sixes(rolls)))); }

    // 3 of a kind
    System.out.print(combo.format("\n7. 3 of a Kind |"));
    if (scores.get(6) != null) { System.out.print(combo.format(" " + String.format("%-4s ", scores.get(6)))); } else { System.out.print(possible.format(" " + String.format("%-4s ", three_kind(rolls)))); }

    // 4 of a kind
    System.out.print(combo.format("\n8. 4 of a Kind |"));
    if (scores.get(7) != null) { System.out.print(combo.format(" " + String.format("%-4s ", scores.get(7)))); } else { System.out.print(possible.format(" " + String.format("%-4s ", four_kind(rolls)))); }

    // full house
    System.out.print(combo.format("\n9. Full House  |"));
    if (scores.get(8) != null) { System.out.print(combo.format(" " + String.format("%-4s ", scores.get(8)))); } else { System.out.print(possible.format(" " + String.format("%-4s ", full_house(rolls)))); }

    // small straight
    System.out.print(ncombo.format("\n10. Small      |"));
    if (scores.get(9) != null) { System.out.print(ncombo.format(" " + String.format("%-4s ", scores.get(9))) + combo.format("\n    Straight   |      ")); } else { System.out.print(npossible.format(" " + String.format("%-4s ", small_straight(rolls))) + combo.format("\n    Straight   |      ")); }

    // large straight
    System.out.print(ncombo.format("\n11. Large      |"));
    if (scores.get(10) != null) { System.out.print(ncombo.format(" " + String.format("%-4s ", scores.get(10))) + combo.format("\n    Straight   |      ")); } else { System.out.print(npossible.format(" " + String.format("%-4s ", large_straight(rolls))) + combo.format("\n    Straight   |      ")); }

    // yahtzee
    System.out.print(combo.format("\n12. YAHTZEE    |"));
    if (scores.get(11) != null) { System.out.print(combo.format(" " + String.format("%-4s ", scores.get(11)))); } else { System.out.print(possible.format(" " + String.format("%-4s ", yahtzee(rolls)))); }

    // chance
    System.out.print(combo.format("\n13. Chance     |"));
    if (scores.get(12) != null) { System.out.print(combo.format(" " + String.format("%-4s ", scores.get(12)))); } else { System.out.print(possible.format(" " + String.format("%-4s ", chance(rolls)))); }
    System.out.println();
  }

  // TOP ROW
  public String ones(ModList<Integer> rolls) {
    return Integer.toString(rolls.number_of(1));
  }

  public String twos(ModList<Integer> rolls) {
    return Integer.toString(rolls.number_of(2) * 2);
  }

  public String threes(ModList<Integer> rolls) {
    return Integer.toString(rolls.number_of(3) * 3);
  }

  public String fours(ModList<Integer> rolls) {
    return Integer.toString(rolls.number_of(4) * 4);
  }

  public String fives(ModList<Integer> rolls) {
    return Integer.toString(rolls.number_of(5) * 5);
  }

  public String sixes(ModList<Integer> rolls) {
    return Integer.toString(rolls.number_of(6) * 6);
  }

  // BOTTOW ROW

  public String three_kind(ModList<Integer> rolls) {
    if (yahtzee(rolls) != "0" && scores.get(11) != null) {
      if (scores.get(rolls.get(0) - 1) == null) { return "0"; }
      else { int temp = 0; for (int i : rolls) { temp += i; } return Integer.toString(temp); }
    }
    for (int i : rolls) {
      if (rolls.number_of(i) >= 3) {
        int temp = 0; for (int j : rolls) { temp += i; } return Integer.toString(temp);
      }
    }
    return "0";
  }

  public String four_kind(ModList<Integer> rolls) {
    if (yahtzee(rolls) != "0" && scores.get(11) != null) {
      if (scores.get(rolls.get(0) - 1) == null) { return "0"; }
      else { int temp = 0; for (int i : rolls) { temp += i; } return Integer.toString(temp); }
    }
    for (int i : rolls) {
      if (rolls.number_of(i) >= 4) {
        int temp = 0; for (int j : rolls) { temp += i; } return Integer.toString(temp);
      }
    }
    return "0";
  }

  public String full_house(ModList<Integer> rolls) {
    if (yahtzee(rolls) != "0" && scores.get(11) != null) {
      if (scores.get(rolls.get(0) - 1) == null) { return "0"; }
      else { return "25"; }
    }
    ModList<Integer> temp = new ModList<Integer>();
    for (int i : rolls) {
      temp.add(i);
    }
    Collections.sort(temp);
    Collections.reverse(temp);
    if (rolls.number_of(temp.get(0)) == 2 || rolls.number_of(temp.get(0)) == 3) {
      int num = temp.get(0);
      int inum = rolls.number_of(num);
      for (int i = 0; i < inum; i++) {
        temp.remove(0);
      }
      if ((rolls.number_of(temp.get(0)) == 2 || rolls.number_of(temp.get(0)) == 3) && rolls.number_of(temp.get(0)) != inum && temp.get(0) != num) {
        return "25";
      }
    }
    return "0";
  }

  public String small_straight(ModList<Integer> rolls) {
    if (yahtzee(rolls) != "0" && scores.get(11) != null) {
      if (scores.get(rolls.get(0) - 1) == null) { return "0"; }
      else { return "30"; }
    }
    for (int i : rolls) {
      if (rolls.contains(i+1) && rolls.contains(i+2) && rolls.contains(i+3)) {
        return "30";
      }
    }
    return "0";
  }

  public String large_straight(ModList<Integer> rolls) {
    if (yahtzee(rolls) != "0" && scores.get(11) != null) {
      if (scores.get(rolls.get(0) - 1) == null) { return "0"; }
      else { return "40"; }
    }
    for (int i : rolls) {
      if (rolls.contains(i+1) && rolls.contains(i+2) && rolls.contains(i+3) && rolls.contains(i+4)) {
        return "40";
      }
    }
    return "0";
  }

  public String yahtzee(ModList<Integer> rolls) {
    if (rolls.number_of(rolls.get(0)) == 5) {
      return "50";
    }
    return "0";
  }

  public String chance(ModList<Integer> rolls) {
    if (yahtzee(rolls) != "0" && scores.get(11) != null) {
      if (scores.get(rolls.get(0) - 1) == null) { return "0"; }
      else { int temp = 0; for (int i : rolls) { temp += i; } return Integer.toString(temp); }
    }
    int temp = 0;
    for (int i : rolls) {
      temp += i;
    }
    return Integer.toString(temp);
  }

  private int to_score(int index, ModList<Integer> rolls) {
    switch (index) {
      case 1: return Integer.parseInt(ones(rolls));
      case 2: return Integer.parseInt(twos(rolls));
      case 3: return Integer.parseInt(threes(rolls));
      case 4: return Integer.parseInt(fours(rolls));
      case 5: return Integer.parseInt(fives(rolls));
      case 6: return Integer.parseInt(sixes(rolls));
      case 7: return Integer.parseInt(three_kind(rolls));
      case 8: return Integer.parseInt(four_kind(rolls));
      case 9: return Integer.parseInt(full_house(rolls));
      case 10: return Integer.parseInt(small_straight(rolls));
      case 11: return Integer.parseInt(large_straight(rolls));
      case 12: return Integer.parseInt(yahtzee(rolls));
      case 13: return Integer.parseInt(chance(rolls));
      default: return 0;
    }
  }
}