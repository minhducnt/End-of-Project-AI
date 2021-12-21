
package gamebanco;

import java.awt.*;
import java.awt.event.*;

import java.awt.image.BufferedImage;
import java.io.*;
import static java.lang.Thread.sleep;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

class Mang // class này lưu trữ tọa độ của n những chiếc xe đầu tiên, dc khởi tạo , và một
           // số thứ ....
{
    static int[][] a;
    static int n;
    static int x, y;
    static int DiemTa = 0, DiemDich = 0; // Điểm ban đầu của 2 đội bằng nhau và = 0.
    static int ThoiGianGame = 0;

    static int Sleep = 100; // Thời gian sleep trong vòng lặp While
    static int level = 6; // Level tang, thi DoNhay cung tang thay
    static int DoNhay = 5;
    static int gio = 0, phut = 0, giay = 0;
    static int ThoiGianLap = 640; // Thời gian để game lặp lại, tạo 1 map mới ( 70s nhé - ko phải 700 s) cố tính
                                  // nhân lên 10 rồi..
    static int tuong; // cho biết , các hình ảnh ban đầu là gì; bằng cách dùng random
    static int car; // random những chiếc car bên phe ta, khi mới vào game
    static int carNC;
}

class DoRongDai {
    static int cellW = 32; // độ rộng của 1 ô
    static int cellH = 32; // độ cao của 1 ô.
    static int countW = 15; // số ô hàng ngang tối đa
    static int countH = 15; // số ô hàng dọc tối đa

    static int Them = 200; // thêm chiều ngang + 200
    static int[][] b; // Mảng này lưu tọa độ nhập từ file để vẽ map.
}

class GameObject {
    String name;
    public void Draw(Graphics g) {
    }
}

public class GameBanCo extends JFrame {

    private JPanel gm = new GameManager();;

    public GameBanCo() {

        initComponents();
        initEvents();
        initWindow();
    }

    private void initComponents() {
        Container cp = this.getContentPane();
        // cp.setLayout(null);

        gm.setFocusable(true); // xem một thành phần có thể đạt được sự tập trung

        cp.add(gm);
    }

    private void initEvents() {
        DiChuyenNgauNhien dc = new DiChuyenNgauNhien();
        // javax.swing.Timer timer = new javax.swing.Timer(Mang.ThoiGianLap * 100, new
        // ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent e) {
        //
        // }
        // });
        // timer.start();
    }

    private void initWindow() {
        this.setSize(300, 300);
        this.setLocation(200, 200);
        this.setTitle("ÁP DỤNG THUẬT GIẢI A* VÀO TRÒ CHƠI");
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        InputStream is = new FileInputStream(new File("./image/nhachay2.wav"));
        AudioStream as = new AudioStream(is);
        AudioPlayer.player.start(as);
        new GameBanCo();

    }

}