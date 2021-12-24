package gamecuopco;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Car extends GameObject {

    public int posW;    // Vị trí ban đầu theo x của board1
    public int posH;    // Vị trí ban đầu theo y của board1
    public int ViTriW;  // Tọa độ thực trên bản đồ
    public int ViTriH;
    public boolean LaNguoiChoi;
    public boolean LaBenNao; // true là phe ta, phe địch là false
    public int Huong;   // Hướng ngẫu nhiên khi không có địch
    public int KhoangDi = 0;

    DiChuyenNgauNhien dc = new DiChuyenNgauNhien();
    private BufferedImage carDich, carTa, carNC, diemKetThuc;

    public Car(int huong, int posW, int posH, boolean KoOrCo, boolean LaBenNao) {
        this.Huong = huong;
        this.posW = posW;
        this.posH = posH;
        this.LaNguoiChoi = KoOrCo; // Có phải là ô tô của người chơi hay không ?
        this.LaBenNao = LaBenNao;

        TinhDoDaiThuc();
    }

    public final void TinhDoDaiThuc() {
        this.ViTriW = this.posW * DoRongDai.cellW;
        this.ViTriH = this.posH * DoRongDai.cellH;
    }

    public void DiChuyenNgauNhien() {
        if (this.KhoangDi <= 0) {
            this.Huong = dc.TimHuongPhuHop(this.posW, this.posH);
            this.KhoangDi = dc.KhoangCoTheDiDuoc(this.posW, this.posH, this.Huong);
        }
        if (this.KhoangDi > 0) {
            this.DiChuyenDoanNgan();
        }
    }

    public void DiChuyenDoanNgan() { // Di chuyển bước nhảy với độ ngắn, nhất định
        if (Mang.ThoiGianGame % Mang.level == 0) {
            switch (this.Huong) {
                case 1:
                    this.posW++; // Đi sang phải
                    break;
                case 2:
                    this.posH++; // Đi xuống
                    break;
                case 3:
                    this.posW--; // Đi sang trái
                    break;
                case 4:
                    this.posH--; // Đi lên trên
                    break;
                default:
                    break;
            }

            this.KhoangDi--;
            this.TinhDoDaiThuc(); // Cập nhập độ dài thực
        } else {
            switch (this.Huong) {
                case 1:
                    this.ViTriW += Mang.DoNhay;
                    break;
                case 2:
                    this.ViTriH += Mang.DoNhay;
                    break;
                case 3:
                    this.ViTriW -= Mang.DoNhay;
                    break;
                case 4:
                    this.ViTriH -= Mang.DoNhay;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void Draw(Graphics g) {

        if (LaNguoiChoi) {
            try {
                carNC = ImageIO.read(new File("image\\Player\\player" + Mang.carNC + ".png"));
            } catch (IOException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
            g.drawImage(carNC, ViTriW, ViTriH, null);
        } else if (LaBenNao == false) { // Nếu là đội địch
            try {
                carDich = ImageIO.read(new File("image\\Enemy\\enemy"+ Mang.car + ".png"));
            } catch (IOException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
            g.drawImage(carDich, ViTriW, ViTriH, null);
        } else if (LaBenNao == true) { // Nếu là Phe Ta
            try {
                carTa = ImageIO.read(new File("image\\Ally\\marvel" + Mang.car + ".png"));
            } catch (IOException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
            g.drawImage(carTa, ViTriW, ViTriH, null);
        }

        if (DiemCuoi.DichConOrKhong) { // Nếu đích cuối còn thì mới vẽ ra
            try {
                diemKetThuc = ImageIO.read(new File("image\\pizza.png"));
            } catch (IOException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
            g.drawImage(diemKetThuc, DiemCuoi.TargetFx * DoRongDai.cellW, DiemCuoi.TargetFy * DoRongDai.cellH, null);
        }

    }

}
