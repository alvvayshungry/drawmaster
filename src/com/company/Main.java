package com.company;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.filechooser.FileFilter;

public class Main {
    // Режим рисования
    int mode = 0;
    int xPad;
    int xF;
    int yF;
    int yPad;
    int thickness;
    boolean pressed = false;
    // текущий цвет
    Color maincolor;
    MyFrame f;
    MyPanel japan;
    JButton colorbutton;
    JColorChooser tcc;
    BufferedImage imag;
    boolean loading = false;
    String fileName;

    public Main() {
        f = new MyFrame("Draw Master");
        f.setSize(350, 350);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        maincolor = Color.black;

        JMenuBar menuBar = new JMenuBar();
        f.setJMenuBar(menuBar);
        menuBar.setBounds(0, 0, 350, 30);
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);

        Action loadAction = new AbstractAction("Загрузить") {
            public void actionPerformed(ActionEvent event) {
                JFileChooser jf = new JFileChooser();
                int result = jf.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        // при выборе изображения подстраиваем размеры формы
                        // и панели под размеры данного изображения
                        fileName = jf.getSelectedFile().getAbsolutePath();
                        File iF = new File(fileName);
                        jf.addChoosableFileFilter(new TextFileFilter(".png"));
                        jf.addChoosableFileFilter(new TextFileFilter(".jpg"));
                        jf.addChoosableFileFilter(new TextFileFilter(".bmp"));
                        imag = ImageIO.read(iF);
                        loading = true;
                        f.setSize(imag.getWidth() + 40, imag.getWidth() + 80);
                        japan.setSize(imag.getWidth(), imag.getWidth());
                        japan.repaint();
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(f, "Файл не существует");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(f, "Исключение ввода-вывода");
                    } catch (Exception ex) {
                    }
                }
            }
        };
        JMenuItem loadMenu = new JMenuItem(loadAction);
        fileMenu.add(loadMenu);

        Action saveAction = new AbstractAction("Сохранить") {
            public void actionPerformed(ActionEvent event) {
                try {
                    JFileChooser jf = new JFileChooser();
                    // Создаем фильтры  файлов
                    TextFileFilter pngFilter = new TextFileFilter(".png");
                    TextFileFilter jpgFilter = new TextFileFilter(".jpg");
                    TextFileFilter bmpFilter = new TextFileFilter(".bmp");
                    if (fileName == null) {
                        // Добавляем фильтры
                        jf.addChoosableFileFilter(pngFilter);
                        jf.addChoosableFileFilter(jpgFilter);
                        jf.addChoosableFileFilter(bmpFilter);
                        int result = jf.showSaveDialog(null);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            fileName = jf.getSelectedFile().getAbsolutePath();
                        }
                    }
                    // Смотрим какой фильтр выбран
                    if (jf.getFileFilter() == pngFilter) {
                        ImageIO.write(imag, "png", new File(fileName + ".png"));
                    } else if (jf.getFileFilter() == jpgFilter) {
                        ImageIO.write(imag, "jpeg", new File(fileName + ".jpg"));
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(f, "Ошибка ввода-вывода");
                }
            }
        };
        JMenuItem saveMenu = new JMenuItem(saveAction);
        fileMenu.add(saveMenu);

        Action saveasAction = new AbstractAction("Сохранить как...") {
            public void actionPerformed(ActionEvent event) {
                try {
                    JFileChooser jf = new JFileChooser();

                    TextFileFilter pngFilter = new TextFileFilter(".png");
                    TextFileFilter jpgFilter = new TextFileFilter(".jpg");
                    TextFileFilter bmpFilter = new TextFileFilter(".bmp");

                    jf.addChoosableFileFilter(pngFilter);
                    jf.addChoosableFileFilter(jpgFilter);
                    jf.addChoosableFileFilter(bmpFilter);
                    int result = jf.showSaveDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        fileName = jf.getSelectedFile().getAbsolutePath();
                    }
                    // Смотрим какой фильтр выбран
                    if (jf.getFileFilter() == pngFilter) {
                        ImageIO.write(imag, "png", new File(fileName + ".png"));
                    } else if (jf.getFileFilter() == jpgFilter) {
                        ImageIO.write(imag, "jpeg", new File(fileName + ".jpg"));
                    } else {
                        ImageIO.write(imag, "bmp", new File(fileName + ".bmp"));
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(f, "Ошибка ввода-вывода");
                }
            }
        };
        JMenuItem saveasMenu = new JMenuItem(saveasAction);
        fileMenu.add(saveasMenu);

        japan = new MyPanel();
        japan.setBounds(50, 50, 50, 200);
        japan.setBackground(Color.white);
        japan.setOpaque(true);
        f.add(japan);


        JToolBar toolbar = new JToolBar("Toolbar", JToolBar.VERTICAL);

        JButton penbutton = new JButton(new ImageIcon("pen.png"));
        penbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mode = 0;
            }
        });
        toolbar.add(penbutton);

        JButton brushbutton = new JButton(new ImageIcon("brush.png"));
        brushbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mode = 1;
            }
        });
        toolbar.add(brushbutton);

        JButton eraserbutton = new JButton(new ImageIcon("eraser.png"));
        eraserbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mode = 2;
            }
        });
        toolbar.add(eraserbutton);


        JButton linebutton = new JButton(new ImageIcon("line.png"));
        linebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mode = 3;
            }
        });
        toolbar.add(linebutton);

        JButton ellipsebutton = new JButton(new ImageIcon("ellipse.png"));
        ellipsebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mode = 4;
            }
        });
        toolbar.add(ellipsebutton);

        JButton rectanglebutton = new JButton(new ImageIcon("rectangle.png"));
        rectanglebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mode = 5;
            }
        });
        toolbar.add(rectanglebutton);

        toolbar.setBounds(0, 50, 200, 300);
        f.add(toolbar);

        // Тулбар для кнопок
        JToolBar colorbar = new JToolBar("Colorbar", JToolBar.HORIZONTAL);
        colorbar.setBounds(0, 0, 500, 50);
        colorbutton = new JButton();
        colorbutton.setBackground(maincolor);
        colorbutton.setBounds(50, 5, 43, 43);
        colorbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ColorDialog coldi = new ColorDialog(f, "Выбор цвета");
                coldi.setVisible(true);
            }
        });
        colorbar.add(colorbutton);

        JButton redbutton = new JButton();
        redbutton.setBackground(Color.red);
        redbutton.setBounds(100, 5, 35, 35);
        redbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                maincolor = Color.red;
                colorbutton.setBackground(maincolor);
            }
        });
        colorbar.add(redbutton);

        JButton orangebutton = new JButton();
        orangebutton.setBackground(Color.orange);
        orangebutton.setBounds(140, 5, 35, 35);
        orangebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                maincolor = Color.orange;
                colorbutton.setBackground(maincolor);
            }
        });
        colorbar.add(orangebutton);

        JButton yellowbutton = new JButton();
        yellowbutton.setBackground(Color.yellow);
        yellowbutton.setBounds(180, 5, 35, 35);
        yellowbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                maincolor = Color.yellow;
                colorbutton.setBackground(maincolor);
            }
        });
        colorbar.add(yellowbutton);

        JButton greenbutton = new JButton();
        greenbutton.setBackground(Color.green);
        greenbutton.setBounds(220, 5, 35, 35);
        greenbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                maincolor = Color.green;
                colorbutton.setBackground(maincolor);
            }
        });
        colorbar.add(greenbutton);

        JButton bluebutton = new JButton();
        bluebutton.setBackground(Color.blue);
        bluebutton.setBounds(260, 5, 35, 35);
        bluebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                maincolor = Color.blue;
                colorbutton.setBackground(maincolor);
            }
        });
        colorbar.add(bluebutton);

        JButton cyanbutton = new JButton();
        cyanbutton.setBackground(Color.cyan);
        cyanbutton.setBounds(300, 5, 35, 35);
        cyanbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                maincolor = Color.cyan;
                colorbutton.setBackground(maincolor);
            }
        });
        colorbar.add(cyanbutton);

        JButton magentabutton = new JButton();
        magentabutton.setBackground(Color.magenta);
        magentabutton.setBounds(340, 5, 35, 35);
        magentabutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                maincolor = Color.magenta;
                colorbutton.setBackground(maincolor);
            }
        });
        colorbar.add(magentabutton);

        JButton whitebutton = new JButton();
        whitebutton.setBackground(Color.white);
        whitebutton.setBounds(380, 5, 35, 35);
        whitebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                maincolor = Color.white;
                colorbutton.setBackground(maincolor);
            }
        });
        colorbar.add(whitebutton);

        JButton blackbutton = new JButton();
        blackbutton.setBackground(Color.black);
        blackbutton.setBounds(420, 5, 35, 35);
        blackbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                maincolor = Color.black;
                colorbutton.setBackground(maincolor);
            }
        });
        colorbar.add(blackbutton);
        colorbar.setLayout(null);
        f.add(colorbar);

        tcc = new JColorChooser(maincolor);
        tcc.getSelectionModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                maincolor = tcc.getColor();
                colorbutton.setBackground(maincolor);
            }
        });
        japan.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (pressed == true) {
                    Graphics g = imag.getGraphics();
                    Graphics2D g2 = (Graphics2D) g;
                    // установка цвета
                    g2.setColor(maincolor);
                    switch (mode) {
                        // карандаш
                        case 0:
                            g2.drawLine(xPad, yPad, e.getX(), e.getY());
                            break;
                        // кисть
                        case 1:
                            g2.setStroke(new BasicStroke(3.0f));
                            g2.drawLine(xPad, yPad, e.getX(), e.getY());
                            break;
                        // ластик
                        case 2:
                            g2.setStroke(new BasicStroke(3.0f));
                            g2.setColor(Color.WHITE);
                            g2.drawLine(xPad, yPad, e.getX(), e.getY());
                            break;
                    }
                    xPad = e.getX();
                    yPad = e.getY();
                }
                japan.repaint();
            }
        });
        japan.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                Graphics g = imag.getGraphics();
                Graphics2D g2 = (Graphics2D) g;
                // установка цвета
                g2.setColor(maincolor);
                switch (mode) {
                    // карандаш
                    case 0:
                        g2.drawLine(xPad, yPad, xPad + 1, yPad + 1);
                        break;
                    // кисть
                    case 1:
                        g2.setStroke(new BasicStroke(3.0f));
                        g2.drawLine(xPad, yPad, xPad + 1, yPad + 1);
                        break;
                    // ластик
                    case 2:
                        g2.setStroke(new BasicStroke(3.0f));
                        g2.setColor(Color.WHITE);
                        g2.drawLine(xPad, yPad, xPad + 1, yPad + 1);
                        break;


                }
                xPad = e.getX();
                yPad = e.getY();

                pressed = true;
                japan.repaint();
            }

            public void mousePressed(MouseEvent e) {
                xPad = e.getX();
                yPad = e.getY();
                xF = e.getX();
                yF = e.getY();
                pressed = true;
            }

            public void mouseReleased(MouseEvent e) {

                Graphics g = imag.getGraphics();
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(maincolor);
                int x1 = xF, x2 = xPad, y1 = yF, y2 = yPad;
                if (xF > xPad) {
                    x2 = xF;
                    x1 = xPad;
                }
                if (yF > yPad) {
                    y2 = yF;
                    y1 = yPad;
                }
                switch (mode) {
                    // линия
                    case 3:
                        g.drawLine(xF, yF, e.getX(), e.getY());
                        break;
                    // круг
                    case 4:
                        g.drawOval(x1, y1, (x2 - x1), (y2 - y1));
                        break;
                    // прямоугольник
                    case 5:
                        g.drawRect(x1, y1, (x2 - x1), (y2 - y1));
                        break;
                }
                xF = 0;
                yF = 0;
                pressed = false;
                japan.repaint();
            }
        });

        f.addComponentListener(new  ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                if(loading==false)
                {
                    japan.setSize(f.getWidth()-40, f.getHeight()-80);
                    BufferedImage tempImage = new  BufferedImage(japan.getWidth(), japan.getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D d2 = (Graphics2D) tempImage.createGraphics();
                    d2.setColor(Color.white);
                    d2.fillRect(0, 0, japan.getWidth(), japan.getHeight());
                    tempImage.setData(imag.getRaster());
                    imag=tempImage;
                    japan.repaint();
                }
                loading=false;
            }
        });
        f.setLayout(null);
        f.setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new  Runnable() {
            public void run() {
                new  Main();
            }
        });
    }

    class ColorDialog extends JDialog
    {
        public ColorDialog(JFrame owner, String title)
        {
            super(owner, title, true);
            add(tcc);
            setSize(500, 600);
        }
    }

    class MyFrame extends JFrame
    {
        public void paint(Graphics g)
        {
            super.paint(g);
        }
        public MyFrame(String title)
        {
            super(title);
        }
    }

    class MyPanel extends JPanel
    {
        public MyPanel()
        { }
        public void paintComponent (Graphics g)
        {
            if(imag==null)
            {
                imag = new  BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D d2 = (Graphics2D) imag.createGraphics();
                d2.setColor(Color.white);
                d2.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
            super.paintComponent(g);
            g.drawImage(imag, 0, 0,this);
        }
    }
    // Фильтр картинок
    class TextFileFilter extends FileFilter
    {
        private String ext;
        public TextFileFilter(String ext)
        {
            this.ext=ext;
        }
        public boolean accept(java.io.File file)
        {
            if (file.isDirectory()) return true;
            return (file.getName().endsWith(ext));
        }
        public String getDescription()
        {
            return "*"+ext;
        }
    }
}