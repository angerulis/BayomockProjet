import java.awt.*;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

class GraphicObject extends JPanel {

    Point objectLoc;
    Color objectColor;
    int objectWidth, objectHeight;
    int amountValue = -1;
    int clickcount = 0;
    int totalAmount = 0;

    public GraphicObject() {

    }

    public GraphicObject(Point curObjectLoc, Color curColor, int curWidth, int curHeight) {

        super();
        objectLoc = curObjectLoc;
        objectColor = curColor;
        objectWidth = curWidth;
        objectHeight = curHeight;
        amountValue = -1;
    }

    public void setLocation(int curX, int curY) {
        objectLoc.setLocation(curX, curY);
    }

    public void setSize(int curWidth, int curHeight) {
        objectWidth = curWidth;
        objectHeight = curHeight;
    }

    public void setPreferredSize(int curWidth, int curHeight) {
        objectWidth = curWidth;
        objectHeight = curHeight;
    }

    public void setBackground(Color curColor) {
        objectColor = curColor;
    }

    public void setLayout(LayoutManager curLayout) {
        super.setLayout(curLayout);
    }

    public void setAmountValue( int curAmountValue){
        this.amountValue = curAmountValue;
    }

    public int getWidth() {
        return objectWidth;
    }

    public void paintComponent(Graphics g) {
        //My test

        if (clickcount != 6){
            g.setColor(Color.black);
            g.drawOval(0, 0, objectWidth, objectHeight);

            g.setColor(objectColor);
            g.fillOval(0, 0, objectWidth, objectHeight);

            if (amountValue != -1){
                g.setColor(Color.black);
                g.setFont(new Font("Tahoma", Font.BOLD, 10));
                g.drawString("" + amountValue, objectWidth/5, objectHeight/2);
                clickcount++;
                totalAmount += amountValue;
            }
        }
        else{
            System.out.println("PAINT EXECUTED");
            g.setColor(Color.red);
            g.setFont(new Font("Tahoma", Font.BOLD, 150));
            g.drawString("You Win " + totalAmount, getWidth() / 2, getHeight() / 2);
        }
    }
}

    public class HiddenMoneyGame extends JFrame {

        Point[] locationPoints = new Point[100];
        GraphicObject[] graphicObjectsList = new GraphicObject[100];
        int ballDiameter;
        int[] amountArr = new int[101];
        int totalAmount = 0;
        int clickcount = 0;
        GridLayout gl = new GridLayout(10, 10, 0, 0);
        Panel pan = new Panel(gl);


        public void iniAmountArr() {
            for (int i = 0, j = 0; i < amountArr.length; i++, j += 100)
                amountArr[i] = j;
        }

        public HiddenMoneyGame(String title, int width, int height, int locX, int locY) {

            super(title);
            setSize(width, height);
            setLocation(locX, locY);

            int curX = 0;
            int curY = 0;
            ballDiameter = (width / 10) - gl.getHgap();
            iniAmountArr();

            for (int i = 0; i != locationPoints.length; i++) {

                Point curPoint = new Point(curX, curY);
                locationPoints[i] = curPoint;

                if (curX <= width - ballDiameter) {
                    curX += ballDiameter + gl.getHgap();
                } else {
                    curX = 0;
                    curY += ballDiameter + gl.getVgap();
                }
            }

            for (int i = 0; i != graphicObjectsList.length; i++) {

                Color randomColor = new Color((int) (Math.random() * 255 + 1),
                        (int) (Math.random() * 255 + 1),
                        (int) (Math.random() * 255 + 1));

                graphicObjectsList[i] = new GraphicObject(locationPoints[i], randomColor, ballDiameter, ballDiameter);

                graphicObjectsList[i].addMouseListener(new MouseDetected());
               pan.add(graphicObjectsList[i]);

            }
            add(pan, BorderLayout.CENTER);
            setVisible(true);

            addWindowListener(new WindowCloser());
            addMouseListener(new MouseDetected());

        }

        class MouseDetected extends MouseAdapter {

            @Override
            public void mouseClicked(MouseEvent e) {


                GraphicObject curGraphicObject = (GraphicObject) e.getSource();

                if (curGraphicObject.amountValue == -1) {

                    Random random = new Random();
                    int amountValue = amountArr[random.nextInt(101)];
                    curGraphicObject.setAmountValue(amountValue);
                    clickcount++;
                }
                pan.repaint();

                if (clickcount == 6) {

                    Panel pan2 = new Panel();
                    add(pan2, BorderLayout.CENTER);
                    remove(pan);
                    repaint();
                    System.out.println("************EXuted************");
                }

            }
        }

        class WindowCloser extends WindowAdapter {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        }

        public static void main(String[] args) {
            HiddenMoneyGame game = new HiddenMoneyGame("GAME", 400, 400, 500, 50);
        }
    }

