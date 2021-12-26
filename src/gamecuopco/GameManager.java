package gamecuopco;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.*;

final class GameManager extends JPanel implements Runnable {

    public static int ThoiGianDich = 0;                     // Thời gian để khởi tạo lại đích
    private final Thread threadT;
    public Car carNC;
    public Board board1 = new Board();
    public Map<String, Car> map = new HashMap<>();          // Map này liên hệ giữa tên và chiếc car
    public Map<String, ThuatToan> map1 = new HashMap<>();   // Mao này liên hẹ giữa tên car và thuật toán. 2 tên là cùng 1 car.
    DiChuyenNgauNhien dc = new DiChuyenNgauNhien();

    public GameManager() {

        CacGiaTriKhoiDau();     // Các giá trị khởi đầu cho game
        addComponent();         // Thêm các xe của máy
        for (String key : map1.keySet()) // Cho các xe máy chạy thuật toán tìm kiếm A Sao
        {
            map1.get(key).ChayThuatToan();
        }

        DiChuyenCarnguoiChoi(); // Người chới dc phép di chuyện

        threadT = new Thread(this);
        threadT.start();        // Khi gọi lệnh này sẽ được chuyển đến phương thức run()ở phía dưới

    }

    public void addComponent() { // Hàm này chứa các xe của phe mình và phe địch
        map.put("Car", new Car(2, Mang.a[0][0], Mang.a[0][1], false, true));
        map1.put("Car", new ThuatToan(Mang.a[0][0], Mang.a[0][1]));

        map.put("Car1", new Car(2, Mang.a[1][0], Mang.a[1][1], false, true));
        map1.put("Car1", new ThuatToan(Mang.a[1][0], Mang.a[1][1]));

        map.put("Car2", new Car(2, Mang.a[2][0], Mang.a[2][1], false, false));
        map1.put("Car2", new ThuatToan(Mang.a[2][0], Mang.a[2][1]));

        map.put("Car3", new Car(2, Mang.a[3][0], Mang.a[3][1], false, false));
        map1.put("Car3", new ThuatToan(Mang.a[3][0], Mang.a[3][1]));

        map.put("Car4", new Car(2, Mang.a[4][0], Mang.a[4][1], false, false));
        map1.put("Car4", new ThuatToan(Mang.a[4][0], Mang.a[4][1]));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        board1.Draw(g);

        carNC.Draw(g);
        for (String key : map.keySet()) {
            map.get(key).Draw(g);
        }

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DoRongDai.countW * DoRongDai.cellW + DoRongDai.Them, DoRongDai.countH * DoRongDai.cellH);
    }

    @Override
    public void run() {

        while (true) {

            if (Mang.ThoiGianGame > 0 && Mang.ThoiGianGame % Mang.ThoiGianLap == 0) { // Sau 1 khoảng thời gian thì mọi thứ thay đổi
                XuLyThayDoi();
            }

            // Cho các agent di chuyển đến đích : Sử dụng thuật toán A*
            if (DiemCuoi.DichConOrKhong) {
                if (Mang.ThoiGianGame % Mang.level == 0) {
                    for (String key : map.keySet()) {
                        map.get(key).posW = map1.get(key).getTrunggian().getFX();
                        map.get(key).posH = map1.get(key).getTrunggian().getFY();
                        map.get(key).TinhDoDaiThuc(); // Cập nhập độ dài thực
                        if (map1.get(key).getTrunggian().getChildren() == null) // nếu con của nó là null, cho nó = target
                        {
                            map1.get(key).setTrunggian(map1.get(key).getTarget());
                        } else // nếu nó có con, thì cho nó bằng con
                        {
                            map1.get(key).setTrunggian(map1.get(key).getTrunggian().getChildren());
                        }

                    }
                } else { // Đẩy là di chuyển từ từ,
                    for (String key : map.keySet()) {

                        if (map1.get(key).getTrunggian().getFX() > map.get(key).posW) {
                            map.get(key).ViTriW += Mang.DoNhay;
                        } else if (map1.get(key).getTrunggian().getFX() < map.get(key).posW) {
                            map.get(key).ViTriW -= Mang.DoNhay;
                        } else if (map1.get(key).getTrunggian().getFY() > map.get(key).posH) {
                            map.get(key).ViTriH += Mang.DoNhay;
                        } else if (map1.get(key).getTrunggian().getFY() < map.get(key).posH) {
                            map.get(key).ViTriH -= Mang.DoNhay;
                        }
                    }
                }

            } else { // Đây là khoảng thời gian không có đích nên chúng di chuyển ngẫu nhiên....
                for (String key : map.keySet()) {
                    map.get(key).DiChuyenNgauNhien();
                }
            }

            // Nếu như chiếc xe chạm đích thì sẽ cộng 1 điểm và đích sẽ bị mất đi ....
            if (DiemCuoi.DichConOrKhong) {
                if (dc.SoSanh(carNC.posW, carNC.posH, DiemCuoi.TargetFx, DiemCuoi.TargetFy)) { /// nếu người chơi chạm
                    /// đích
                    Mang.DiemTa++;
                    DiemCuoi.DichConOrKhong = false;
                    ThoiGianDich = 0;
                } else {
                    for (String key : map.keySet()) {   // Nếu đích đang còn thì mới cộng điểm
                        if (dc.SoSanh(map.get(key).posW, map.get(key).posH, DiemCuoi.TargetFx, DiemCuoi.TargetFy)) {
                            if (map.get(key).LaBenNao) // Nếu là phe ta, thì cộng 1 điểm
                            {
                                Mang.DiemTa++;
                            } else if (!map.get(key).LaBenNao) {
                                Mang.DiemDich++;
                            }
                            DiemCuoi.DichConOrKhong = false; // Điểm cuối sẽ không còn nữa, vì bị bọn nó ăn mất rồi, bọn
                            // xe sẽ lại di chuyển ngẫu nhiên
                            ThoiGianDich = 0; // Khởi tạo lại thời gian đích

                        }
                        map.get(key).KhoangDi = 0;  // Cho khoảng đi bằng 0
                        map.get(key).Huong = 0;     // Cho hướng đi lúc này là = 0 (Đứng Yên)
                    }
                }
            }

            // Sau 1 khoảng thời gian thì địch thay đổi , ...
            if (!DiemCuoi.DichConOrKhong) // Nếu không còn đích
            {
                ThoiGianDich += 1;
            }
            if (ThoiGianDich % 40 == 0 && ThoiGianDich != 0) { // Vì 0 chia hết cho tất cả các số

                ThoiGianDich = 0; // Lại khởi tạo lại thời gian đích
                DiemCuoi.DichConOrKhong = true; // Sau 1 khoảng thời gian thì đích lại có
                while (true) {
                    DiemCuoi.TargetFx = (int) (Math.random() * DoRongDai.countW); // Tạo 1 điểm ngẫu nhiên cho diểm cuối
                    DiemCuoi.TargetFy = (int) (Math.random() * DoRongDai.countH); // Nhưng nó không phải là tường

                    if (dc.KiemTra(DiemCuoi.TargetFy, DiemCuoi.TargetFx) == false) // neu la duong di, thì break..
                    {
                        break;
                    }
                }
                for (String key : map1.keySet()) {
                    int x = map.get(key).posW; // Lưu lại tọa độ hiện tại
                    int y = map.get(key).posH;
                    map1.get(key).clear(); // Clear lại thuật toán, vì điểm cuối đã thay đổi rồi...
                    map1.get(key).initThuatToan(x, y); // Khởi tạo lại thuật toán với toa độ ban đầu là x,y
                    map1.get(key).ChayThuatToan(); // Chạy thuật toán lại
                }
            }

            try {
                Thread.sleep(Mang.Sleep);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            repaint();
            Mang.ThoiGianGame += 1; // Thời gian thực của Game
            dc.XuLyThoiGianGame();

        }

    }

    public void CacGiaTriKhoiDau() {
        Mang.tuong = dc.RanDom(10); // Random các loại tường
        Mang.car = dc.RanDom(5);    // Random cac loại car
        Mang.carNC = dc.RanDom(2);
        dc.NhapMapTuFile("map" + dc.RanDom(10) + ".txt"); // Nhập map và tọa độ các xe : map là random.....từ 1 trong 20 map
        carNC = new CarNguoiChoi(1, Mang.x, Mang.y, true, true); // Sau khi nhập file thì ta mới có tọa độ của carNC, nên nó phải đặt ở dưới
    }

    private void DiChuyenCarnguoiChoi() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_LEFT && carNC.posW > 0 && !dc.KiemTra(carNC.posH, carNC.posW - 1)) {
                    carNC.posW--;
                } else if (key == KeyEvent.VK_RIGHT && carNC.posW < DoRongDai.countW - 1
                        && !dc.KiemTra(carNC.posH, carNC.posW + 1)) {
                    carNC.posW++;
                } else if (key == KeyEvent.VK_UP && carNC.posH > 0 && !dc.KiemTra(carNC.posH - 1, carNC.posW)) {
                    carNC.posH--;
                } else if (key == KeyEvent.VK_DOWN && carNC.posH < DoRongDai.countH - 1
                        && !dc.KiemTra(carNC.posH + 1, carNC.posW)) {
                    carNC.posH++;
                }

                carNC.TinhDoDaiThuc(); // Tính độ dài thực
                repaint();
            }
        });
    }

    public void XuLyThayDoi() { // Sau khoảng bao nhiêu thì thay đổi
        dc.NhapMapTuFile("map" + dc.RanDom(9) + ".txt"); // Nhập map và tọa độ các xe : map là random.....từ 1 trong 10 map
        addComponent(); // Thêm đè vào map đã có lại
        for (String key : map1.keySet()) // Cho các xe máy chạy thuật toán tìm kiếm A Sao
        {
            map1.get(key).ChayThuatToan();
        }
        carNC = new CarNguoiChoi(1, Mang.x, Mang.y, true, true); // Sau khi nhập file thì ta mới có tọa độ của carNC nên nó phải đặt ở dưới
        Mang.tuong = dc.RanDom(11); // Đổi Nhạc, đổi tường, đổi car
        Mang.car = dc.RanDom(5);

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./image/music2.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-15.0f); // Reduce volume by 10 decibels.
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            Logger.getLogger(GameBanCo.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (Mang.level > 1) {
            Mang.level--;
            Mang.DoNhay += 2;
        }
    }

}   
