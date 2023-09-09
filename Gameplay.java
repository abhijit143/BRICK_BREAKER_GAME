package org.example;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

    public class Gameplay extends JPanel implements KeyListener, ActionListener {
        private boolean play = false;
        private int score = 0;
        private int total_bricks = 21;
        private Timer timer;
        private int delay = 8;
        private int playerX = 310;
        private int ballposX = 120;
        private int ballposY = 350;
        private int ballxdir = -1;
        private int ballydir = -2;
        private MapGenerator map;

        public Gameplay() {
            map = new MapGenerator(3, 7);
            addKeyListener(this);
            setFocusable(true);
            setFocusTraversalKeysEnabled(false);
            timer = new Timer(delay, this);
            timer.start();
        }

        public void paint(Graphics g) {
            g.setColor(Color.black);
            g.fillRect(1, 1, 692, 592);

            map.draw((Graphics2D) g);

            //Bordering With Yellow
            g.setColor(Color.yellow);
            g.fillRect(0, 0, 3, 592);
            g.fillRect(0, 0, 692, 3);
            g.fillRect(691, 0, 3, 592);

            //Score
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString(" " + score, 590, 30);

            //Slider
            g.setColor(Color.YELLOW);
            g.fillRect(playerX, 550, 100, 8);

            //Ball
            g.setColor(Color.BLUE);
            g.fillOval(ballposX, ballposY, 20, 20);

            //Condition to Stop
            if (ballposY > 570) {
                play = false;
                ballxdir = 0;
                ballydir = 0;
                g.setColor(Color.red);
                g.setFont(new Font("serif", Font.BOLD, 30));
                g.drawString("    Game Over.....Score : " + score, 190, 300);

                g.setFont(new Font("serif", Font.BOLD, 30));
                g.drawString("    Press ENTER to RESTART ", 190, 340);
            }

            //Condition to Stop
            if (total_bricks == 0) {
                play = false;
                ballydir = -2;
                ballxdir = -1;
                g.setColor(Color.red);
                g.setFont(new Font("serif", Font.BOLD, 30));
                g.drawString("    Game Over.....Score : " + score, 190, 300);

                g.setFont(new Font("serif", Font.BOLD, 30));
                g.drawString("    Press ENTER to RESTART ", 190, 340);
            }
            g.dispose();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // It Contains Action Event
            timer.start();

            if (play) {
                if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                    ballydir = -ballydir;
                }

                A:
                // this is a loop breaker
                for (int i = 0; i < map.map.length; i++) //  in map class we create a map array ,
                // here map.map means first map is the map class & the second map means map array
                {
                    for (int j = 0; j < map.map[0].length; j++) {
                        if (map.map[i][j] > 0) {
                            int brickX = j * map.brickwidth + 80;
                            int brickY = i * map.brickheight + 50;
                            int brickwidth = map.brickwidth;
                            int brickheight = map.brickheight;

                            Rectangle rect = new Rectangle(brickX, brickY, brickwidth, brickheight);
                            Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20);
                            Rectangle brickrect = rect;

                            if (ballrect.intersects(brickrect)) {
                                map.setBricksvalue(0, i, j);
                                total_bricks--;
                                score += 5;
                                if (ballposX + 19 <= brickrect.x || ballposX + 1 >= brickrect.x + brickwidth) {
                                    ballxdir = -ballxdir;
                                } else {
                                    ballydir = -ballydir;
                                }
                                break A;
                            }

                        }
                    }
                }
                ballposX += ballxdir;
                ballposY += ballydir;
                if (ballposX < 0) {
                    ballxdir = -ballxdir;
                }
                if (ballposY < 0) {
                    ballydir = -ballydir;
                }
                if (ballxdir > 670) {
                    ballxdir = -ballxdir;
                }
            }
            repaint();
        }


        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (playerX >= 600) {
                    playerX = 600;
                } else {
                    moveRight();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (playerX < 10) {
                    playerX = 10;
                } else {
                    moveLeft();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (!play) {
                    ballposX = 120;
                    ballposY = 350;
                    ballxdir = -1;
                    ballydir = -2;
                    score = 0;
                    playerX = 310;
                    total_bricks = 21;
                    map = new MapGenerator(3, 7);
                    repaint();
                }
            }


        }

        private void moveLeft() {
            play = true;
            playerX -= 20;
        }

        private void moveRight() {
            play = true;
            playerX += 20;
        }

    }
