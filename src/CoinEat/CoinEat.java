package CoinEat;

import javax.swing.*;
import java.awt.*;

public class CoinEat  extends JFrame {
    private Image backgroundImage = new ImageIcon("res/mainScreen.png").getImage();
    private Image player = new ImageIcon("res/player.png").getImage();
    private Image coin = new ImageIcon("res/coin.png").getImage();
    private int playerX,playerY;
    private int playerWidth= player.getWidth(null);
    private int playerHeight= player.getHeight(null);
    private int coinX,coinY;
    private int coinWidth= coin.getWidth(null);
    private int coinHeight= coin.getHeight(null);
    private int score;

    public CoinEat(){
    setTitle("동전먹기");
    setVisible(true);
    setSize(500,500);
    setLocationRelativeTo(null);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Init();
    }
    // play 처음할때 초기화
    public void Init(){
        score =0;
        playerX=(500-playerWidth)/2;
        playerY=(500-playerHeight)/2;
        // randome*(창의크기 +1 - 이미지길이)
        coinX= (int)(Math.random()*(501-playerWidth));
        // 프레임틀 길이 30
        coinY= (int)(Math.random()*(501-playerHeight +30))-30;
    }
 public void paint(Graphics g){
           g.drawImage(backgroundImage,0,0,null);
           g.drawImage(coin,coinX,coinY,null);
     g.drawImage(player,playerX,playerY ,null);

    }

}
