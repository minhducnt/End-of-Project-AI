package gamecuopco;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

class Mang // Class này lưu trữ tọa độ của n những chiếc xe đầu tiên, dc khởi tạo , và một số thứ ....
{

    static int[][] a;
    static int n;
    static int x, y;
    static int DiemTa = 0, DiemDich = 0; // Điểm ban đầu của 2 đội bằng nhau và = 0.
    static int ThoiGianGame = 0;

    static int Sleep = 100; // Thời gian sleep trong vòng lặp While
    static int level = 6;   // Level tang, thi DoNhay cung tang thay
    static int DoNhay = 5;
    static int gio = 0, phut = 0, giay = 0;
    static int ThoiGianLap = 640; // Thời gian để game lặp lại, tạo 1 map mới ( 70s nhé - ko phải 700 s) cố tính nhân lên 10 rồi..
    static int tuong;       // Cho biết các hình ảnh ban đầu là gì ? bằng cách dùng random
    static int car;         // Random những chiếc car bên phe ta, khi mới vào game
    static int carNC;
}

class DoRongDai {

    static int cellW = 32;  // Độ rộng của 1 ô
    static int cellH = 32;  // Độ cao của 1 ô.
    static int countW = 15; // Số ô hàng ngang tối đa
    static int countH = 15; // Số ô hàng dọc tối đa

    static int Them = 200;  // Thêm chiều ngang + 200
    static int[][] b;       // Mảng này lưu tọa độ nhập từ file để vẽ map.
}

class GameObject {

    String name;

    public void Draw(Graphics g) {
    }
}

public class GameBanCo extends JFrame {

    private final JPanel gm = new GameManager();

    public GameBanCo() {
        initComponents();
        initEvents();
        initWindow();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./image/music1.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl volume= (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-15.0f); // Reduce volume by 10 decibels.
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            System.out.println("Lỗi khi mở nhạc !.");
        }
        GameBanCo gameBanCo = new GameBanCo();
    }

    private void initComponents() {
        Container cp = this.getContentPane();
        gm.setFocusable(true); // Xem một thành phần có thể đạt được sự tập trung
        cp.add(gm);
    }

    private void initEvents() {
        DiChuyenNgauNhien dc = new DiChuyenNgauNhien();
    }

    private void initWindow() {
        this.setLocation(200, 200);
        this.setTitle("ÁP DỤNG THUẬT GIẢI A* VÀO TRÒ CHƠI");
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

}
