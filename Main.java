import com.diogonunes.jcolor.Attribute;
import com.diogonunes.jcolor.AnsiFormat;
import static com.diogonunes.jcolor.Ansi.*;
import static com.diogonunes.jcolor.Attribute.*;

import java.util.Scanner;

class Main {
  final static AnsiFormat computer = new AnsiFormat(TEXT_COLOR(220));
  final static AnsiFormat computer_dark = new AnsiFormat(TEXT_COLOR(214));

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    GameManager p1 = new GameManager();
    GameManager p2 = new GameManager();
    System.out.println(computer.format("Welcome to ") + computer_dark.format("Yahtzee™.") + computer.format(" This game is about ") + computer_dark.format("dice™.")); 
    System.out.println(computer.format("We'll assume you already know ") + computer_dark.format("how to play™.") + computer.format("\nIf you don't, you can look up the dice") + computer_dark.format(" combinations™ ") + computer.format("on the ") + computer_dark.format("Internet™.") + computer.format("\nLet's get into it!"));

    System.out.println(computer.format("\nHow many people are playing today?") + computer_dark.format("\n1. One Player™\n2. Two Player™"));
    int players = input.nextInt();
    input.nextLine();
    while (players < 1 && players > 2) {
      players = input.nextInt();
      input.nextLine();
    }

    while (p1.over == false) {
      System.out.println();
      if (players == 2) { System.out.println(computer.format("-------- PLAYER ONE TURN --------")); }
      p1.turn();
      if (players == 2) {
        System.out.println(computer.format("\n-------- PLAYER TWO TURN --------"));
        p2.turn();
      }
    }

    System.out.println(colorize("\nGAME OVER", TEXT_COLOR(220), BACK_COLOR(208)));
    if (players == 2) {
      System.out.println(computer.format("Player 1 Final Score:"));
      score(p1);

      System.out.println(computer.format("Player 2 Final Score:"));
      score(p2);
    }
    else {
      System.out.println(computer.format("Final Score:"));
      score(p1);
    }
  }

  public static void score(GameManager game) {
    int total = 0;
    for (int i : game.combos.scores) {
      total += i;
    }

    total += game.combos.bonus;

    int bonus = 0;
    if ((game.combos.scores.get(0) + game.combos.scores.get(1) + game.combos.scores.get(2) + game.combos.scores.get(3) + game.combos.scores.get(4) + game.combos.scores.get(5)) > 64) {
      total += 35;
      bonus = 35;
    }

    System.out.println(computer_dark.format(Integer.toString(total)));

    System.out.println(computer.format("\nUpper 6 bonus:"));
    System.out.println(computer_dark.format(Integer.toString(bonus)));

    System.out.println(computer.format("Yahtzee bonuses:"));
    System.out.println(computer_dark.format(Integer.toString(game.combos.bonus / 100)));
  }
}