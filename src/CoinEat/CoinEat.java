package CoinEat;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class CoinEat extends JFrame {
    // 버퍼링을 잡기 위한 변수  bufferImage & screenGraphic
    private Image bufferImage;
    private Graphics screenGraphic;
    // music variable
    private Clip clip;
    private Image backgroundImage = new ImageIcon("res/mainScreen.png").getImage();
    private Image player = new ImageIcon("res/player.png").getImage();
    private Image coin = new ImageIcon("res/coin.png").getImage();
    private int playerX, playerY;
    private int playerWidth = player.getWidth(null);
    private int playerHeight = player.getHeight(null);
    private int coinX, coinY;
    private int coinWidth = coin.getWidth(null);
    private int coinHeight = coin.getHeight(null);
    private int score;

    private boolean up, down, left, right;

    public CoinEat() {
        setTitle("동전먹기");
        setVisible(true);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        up = true;
                        break;
                    case KeyEvent.VK_A:
                        left = true;
                        break;
                    case KeyEvent.VK_S:
                        down = true;
                        break;
                    case KeyEvent.VK_D:
                        right = true;
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        up = false;
                        break;
                    case KeyEvent.VK_A:
                        left = false;
                        break;
                    case KeyEvent.VK_S:
                        down = false;
                        break;
                    case KeyEvent.VK_D:
                        right = false;
                        break;
                }
            }
        });
        Init();
        while (true) {
            // 대기시간없이 계속 반복되면 무리가 갈수 있으므로 대기시간을 줌 .
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            KeyProcess();
            crashCheck();
        }

    }

    // play 처음할때 초기화
    public void Init() {
        score = 0;
        playerX = (500 - playerWidth) / 2;
        playerY = (500 - playerHeight) / 2;
        // randome*(창의크기 +1 - 이미지길이)
        coinX = (int) (Math.random() * (501 - playerWidth));
        // 프레임틀 길이 30
        coinY = (int) (Math.random() * (501 - playerHeight - 30)) + 30;
        //sound
    playSound("audio/backgroundMusic.wav" ,true);
    }

    public void KeyProcess() {
        if (up && playerY - 3 > 30) playerY -= 3;
        if (down && playerY + playerHeight + 3 < 500) playerY += 3;
        if (left && playerX - 3 > 30) playerX -= 3;
        if (right && playerX + playerWidth + 3 < 500) playerX += 3;
    }

    // player get coin score
    public void crashCheck() {
        if (playerX + playerHeight > coinX && coinX + coinWidth > playerX
                && playerY + playerHeight > coinY && coinY + coinHeight > playerY) {
            score += 100;
            playSound("audio/getCoin.wav" ,false);
            coinX = (int) (Math.random() * (501 - playerWidth));
            // 프레임틀 길이 30
            coinY = (int) (Math.random() * (501 - playerHeight - 30)) + 30;

        }
    }

    public  void playSound(String pathName, boolean isLoop){
        try{
            clip = AudioSystem.getClip();
            File audioFile= new File(pathName);
            AudioInputStream audioInputStream =AudioSystem.getAudioInputStream(audioFile);
            clip.open(audioInputStream);
            clip.start();
            if(isLoop) clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch(LineUnavailableException e){
    e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // 버퍼링 해결 ( 화면 깜박임 심함)
    public void paint(Graphics g) {
        bufferImage = createImage(500, 500);
        screenGraphic = bufferImage.getGraphics();
        screenDraw(screenGraphic);
        g.drawImage(bufferImage, 0, 0, null);


    }

    public void screenDraw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, null);
        g.drawImage(coin, coinX, coinY, null);
        g.drawImage(player, playerX, playerY, null);
        //점수 화면
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("SCORE : " + score, 30, 80);
        this.repaint();
    }

}
