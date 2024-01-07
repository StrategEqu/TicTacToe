import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Paint extends JComponent {
    public static final int FIELD_EMPTY = 0; //пустое поле
    public static final int FIELD_X = 10; //поле с крестиком
    public static final int FIELD_0 = 200; //с ноликом
    int[][] field; //массив поля
    boolean isXturn;

    public Paint() {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        field = new int[3][3];
        initGame();
    }

    public void initGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = FIELD_EMPTY; //заполняем массив 0
            }
        }
        isXturn = true;
    }

    int checkState() {
        int diag = 0;
        int diag2 = 0;
        for (int i = 0; i < 3; i++) {
            //сумма по диагонали от левого угла
            diag += field[i][i];
            //сумма по диагонали от правого угла
            diag2 += field[i][2 - i];
        }
        if (diag == FIELD_0 * 3 || diag == FIELD_X * 3) {
            return diag;
        }
        if (diag2 == FIELD_0 * 3 || diag2 == FIELD_X * 3) {
            return diag2;
        }
        int check_i, check_j;
        boolean hasEmpty = false;

        for (int i = 0; i < 3; i++) { //проходим по всем рядам
            check_i = 0;
            check_j = 0;
            for (int j = 0; j < 3; j++) {
                //суммируем знаки в ряду
                if (field[i][j] == 0) {
                    hasEmpty = true;
                }
                check_i += field[i][j];
                check_j += field[j][i];
            }
            if (check_i == FIELD_0 * 3 || check_i == FIELD_X * 3) {
                return check_i;
            }
            if (check_j == FIELD_0 * 3 || check_j == FIELD_X * 3) {
                return check_j;
            }
        }
        if (hasEmpty) return 0;
        else return -1;
    }

    @Override
    protected void processMouseEvent(MouseEvent mouseEvent) {
        super.processMouseEvent(mouseEvent);
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) { //проверка нажатая лкм
            int x = mouseEvent.getX(); //координата х клика
            int y = mouseEvent.getY(); //координата у клика
            //переводим координаты в индексы ячейки в массиве field
            int i = (int) ((float) x / getWidth() * 3);
            int j = (int) ((float) y / getHeight() * 3);

            if (field[i][j] == FIELD_EMPTY) { //проверяем что ячейка пуста
                field[i][j] = isXturn ? FIELD_X : FIELD_0; //проверка чей ход
                isXturn = !isXturn; //меняем флаг хода
                repaint(); //вызов метода paintComponents (перерисовка компонента)
                int res = checkState();
                if (res != 0) {
                    if (res == FIELD_0 * 3) {
                        JOptionPane.showMessageDialog(this, "нолики выиграли", "Победа", JOptionPane.INFORMATION_MESSAGE);
                    } else if (res == FIELD_X * 3) {
                        JOptionPane.showMessageDialog(this, "крестики выиграли", "Победа", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "ничья!", "Ничья!", JOptionPane.INFORMATION_MESSAGE);
                    }
                    initGame(); //перезапуск игры
                    repaint();
                }
            }
        }
    }

    void drawX(int i, int j, Graphics graphics) { //рисуем крестик
        graphics.setColor(Color.RED);
        int dw = getWidth() / 3;
        int dh = getHeight() / 3;
        int x = i * dw;
        int y = j * dh;
        graphics.drawLine(x, y, x + dw, y + dh);
        //линия от верхнего угла до правого нижнего
        graphics.drawLine(x, y + dh, x + dw, y);
        //линия от левого нижнего угла до правого верхнего
    }

    void draw0(int i, int j, Graphics graphics) { //рисуем нолик
        graphics.setColor(Color.BLUE);
        int dw = getWidth() / 3;
        int dh = getHeight() / 3;
        int x = i * dw;
        int y = j * dh;
        graphics.drawOval(x + 5 * dw / 100, y, dw * 9 / 10, dh);
    }

    void drawXO(Graphics graphics) { //метод рисования
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //если в ячейке крестик, рисуем его
                if (field[i][j] == FIELD_X) {
                    drawX(i, j, graphics);
                    //то же с ноликом
                } else if (field[i][j] == FIELD_0) {
                    draw0(i, j, graphics);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.clearRect(0, 0, getWidth(), getHeight()); //очищаем холст
        drawGrid(graphics); //рисуем сетку
        drawXO(graphics); //рисуем текущие крестики и нолики
    }

    void drawGrid(Graphics graphics) {
        int w = getWidth(); //ширина поля
        int h = getHeight(); //высота поля
        int dw = w / 3; //делим ширину на 3 и получаем ширину одной ячейки
        int dh = h / 3; //делим высоту на 3 и получаем высоту одной ячейки
        graphics.setColor(Color.BLACK); //цвет линий
        for (int i = 1; i < 3; i++) {
            graphics.drawLine(0, dh * i, w, dh * i);
            //горизонтальная линия
            graphics.drawLine(dw * i, 0, dw * i, h);
            //вертикальная линия
        }
    }
}