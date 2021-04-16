package display;

import deck.Card;
import play.game.Game;
import statics.Hand;
import statics.Players;

import java.awt.Color;
import java.awt.*;
import java.awt.EventQueue;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import static statics.Players.humanPlayer;

public class Board {

    public static void main(String[] args) {
        new Board();
    }

    public Board() {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }


            JFrame frame = new JFrame("Testing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new GamePane());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
        ExecutorService ex = new ForkJoinPool();
        ex.submit(() -> {
            Game game = new Game(100);
            game.playGame();
        });

    }

    public class GamePane extends JPanel {

        private Rectangle[] roundCards;
        private Rectangle[] handCards;
        private Rectangle[] handCardsFrom;
        public GamePane() {

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    Card clickedCard = null;

                    for (Card card : humanPlayer.getHand()) {

                        Rectangle bounds = roundCards[humanPlayer.cardOrder.get(card)];
                        if (bounds.contains(e.getPoint())) {
                            clickedCard = card;
                        }
                    }
                    if (clickedCard != null) {
                        humanPlayer.cardClicked(clickedCard);
                        repaint();
                    }
                }
            });
            final Timer timer = new Timer(10, e ->checkDraw());
            timer.start();

        }

        private void checkDraw() {
            repaint();

        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(400, 400);
        }

        @Override
        public void invalidate() {
            super.invalidate();
            roundCards = new Rectangle[13];
            int cardHeight = (getHeight() - 20) / 3;
            int cardWidth = (int) (cardHeight * 0.6);
            int xDelta = cardWidth / 2;
            int xPos = (int) ((getWidth() / 2) - (cardWidth * (humanPlayer.getHand().size() / 4.0)));
            int yPos = (getHeight() - 20) - cardHeight;
            for (int i =0; i<13; i++) {
                Rectangle bounds = new Rectangle(xPos, yPos, cardWidth, cardHeight);
                roundCards[i] = bounds;
                xPos += xDelta;
            }

            yPos /= 2;
            xPos /= 2;
            handCards = new Rectangle[4];
            handCards[0] = new Rectangle(xPos, yPos, cardWidth, cardHeight);
            handCards[1] = new Rectangle(xPos - xDelta, yPos - xDelta, cardWidth, cardHeight);
            handCards[2] = new Rectangle(xPos, yPos - xDelta - xDelta, cardWidth, cardHeight);
            handCards[3] = new Rectangle(xPos + xDelta, yPos - xDelta, cardWidth, cardHeight);
            handCardsFrom = new Rectangle[4];
            handCardsFrom[0] = new Rectangle(xPos, yPos, cardWidth, cardHeight);
            handCardsFrom[1] = new Rectangle(-xDelta, yPos - xDelta, cardWidth, cardHeight);
            handCardsFrom[2] = new Rectangle(xPos, -cardHeight, cardWidth, cardHeight);
            handCardsFrom[3] = new Rectangle(xPos + xDelta + xPos, yPos - xDelta, cardWidth, cardHeight);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            paintHumanPlayersCards(g2d);
            paintHandCards(g2d);
            paintAnimatedCard(g2d);
            paintDebug(g2d);
            g2d.dispose();
        }

        private void paintDebug(Graphics2D g2dain) {
            Graphics2D g2d = (Graphics2D) g2dain.create();
            g2d.translate(  5,  getHeight()/2);
            String text = Players.getPlayer(1).toString();
            drawString(g2d,text, 0,0);


            g2d.translate(  getWidth()-250,  0);
            text = Players.getPlayer(3).toString();
            drawString(g2d,text, 0,0);

            g2d.translate( - getWidth()/2,  -getHeight()/2+10);
            text = Players.getPlayer(2).toString();
            drawString(g2d,text, 0,0);


            g2d.translate(  0,  getHeight()-230);
            text =  Players.getPlayer(0).toString();
            drawString(g2d,text, 0,0);

            g2d.dispose();
        }

        private void drawString(Graphics g, String text, int x, int y) {
            for (String line : text.split("\n"))
                g.drawString(line, x, y += g.getFontMetrics().getHeight());
        }

        private void paintAnimatedCard(Graphics2D g2d) {
            if (Animation.animate) {
                Rectangle fromRectangle = Animation.animatePlayer == 0 ? roundCards[humanPlayer.cardOrder.get(Animation.animateCard)]
                        : handCardsFrom[Animation.animatePlayer];
                Rectangle cardRectangle = Animation.getRectangle(fromRectangle, handCards[Animation.animatePlayer]);
                g2d.setColor(Color.WHITE);
                g2d.fill(cardRectangle);
                g2d.setColor(Color.BLACK);
                g2d.draw(cardRectangle);
                Graphics2D copy = (Graphics2D) g2d.create();
                paintCard(copy, Animation.animateCard, cardRectangle);
                copy.dispose();
                Animation.setAnimateNext();
            }
        }

        private void paintHandCards(Graphics2D g2d) {
            int i = 0;
            for (Card card : Hand.cards) {
                int drawCell = (i++ + Hand.starter) % 4;

                    g2d.setColor(Color.WHITE);
                    g2d.fill(handCards[drawCell]);
                    g2d.setColor(Color.BLACK);
                    g2d.draw(handCards[drawCell]);
                    Graphics2D copy = (Graphics2D) g2d.create();
                    paintCard(copy, card, handCards[drawCell]);
                    copy.dispose();

            }
        }

        private void paintHumanPlayersCards(Graphics2D g2d) {
            for (Card card :  humanPlayer.getHand()) {
                Rectangle bounds = roundCards[humanPlayer.cardOrder.get(card)];
                System.out.println(bounds);
                if (bounds != null) {
                    if(WrongCard.exists && card.equals(WrongCard.card)){
                    g2d.setColor(Color.GRAY);
                    WrongCard.next();
                }else
                    g2d.setColor(Color.WHITE);
            }
                    g2d.fill(bounds);
                    g2d.setColor(Color.BLACK);
                    g2d.draw(bounds);
                    Graphics2D copy = (Graphics2D) g2d.create();
                    paintCard(copy, card, bounds);
                    copy.dispose();
                }
            }



        protected void paintCard(Graphics2D g2d, Card card, Rectangle bounds) {
            g2d.translate(bounds.x + 5, bounds.y + 5);
            g2d.setClip(0, 0, bounds.width - 5, bounds.height - 5);

            String text = card.getFace().getSign() + card.getSuit().getSign();
            FontMetrics fm = g2d.getFontMetrics();
            g2d.setColor(card.getSuit().getColor());
            g2d.drawString(text, 0, fm.getAscent());
        }
    }
}


