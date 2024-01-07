import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Запускаем игру");
        JFrame window = new JFrame("Крестики-Нолики"); //основное окно
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//кнопка закрытия
        window.setSize(400, 400); //размер окна
        window.setLayout(new BorderLayout()); //менеджер компоновки
        window.setLocationRelativeTo(null); //расположение окна (центр)
        window.setVisible(true); //видимость окна
        Paint graphics = new Paint();
        window.add(graphics);
        System.out.println("Конец игры");
    }
}

