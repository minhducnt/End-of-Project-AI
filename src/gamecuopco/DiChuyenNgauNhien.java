package gamecuopco;

import java.io.File;
import java.util.Scanner;
import java.util.Vector;

public class DiChuyenNgauNhien {

    public DiChuyenNgauNhien() {
    }

    public int RanDom(int khoang) {     // Hàm này khi vừa mới vào game, thì random các map, để tạo sự hấp dân.
        int map;                        // Hoặc random các tường khi mới vào game
        map = 1 + (int) (Math.random() * khoang);       // C4 map
        return map;
    }

    public boolean KiemTra(int x, int y) { // Kiểm tra tọa độ này có phải là tường hay tọa độ đường đi
        // Là bức tường
        if (x < 0 || x > DoRongDai.countH - 1 || y < 0 || y > DoRongDai.countW - 1) {
            return true;
        } else {
            return DoRongDai.b[x][y] != 0;  // Là đường đi
        }
        // Map này, ngược với posX và posY rồi....... ví dụ b[i][j] ta duyệt posY trước,
        // posX sau...khó xơi
    }

    public int TimHuongPhuHop(int x, int y) { // Hàm này sẽ tìm hướng nào phù hợp cho mỗi vị trí, đứng , ở bất kì vị trí nào
        Vector a = new Vector();
        int w = 0;
        if (!KiemTra(y, x + 1)) { // Nếu bên phải là đường đi
            a.add(1); // đi được bên phải
            w++; // tăng w
        }
        if (!KiemTra(y + 1, x)) { // Nếu đi được bên dưới
            a.add(2);
            w++;
        }
        if (!KiemTra(y, x - 1)) { // Nếu bên trái là đường đi
            a.add(3);
            w++;
        }
        if (!KiemTra(y - 1, x)) { // Nếu bên trên là dường đi
            a.add(4);
            w++;
        }

        // Lấy random cưa mang , để lấy hướng đi phù hợp
        int huong = (int) (Math.random() * w);
        return (int) a.elementAt(huong);    // Lấy 1 hướng phù hợp có thể đi được ngẫu nhiên
    }

    public int KhoangCoTheDiDuoc(int x, int y, int huong) { // 1 khoảng lớn nhất mà có thể, đi được..ko chạm phải tường
        int khoang = 0;
        while (!KiemTra(y, x)) {
            switch (huong) {
                case 1:
                    x++;
                    break;
                case 2:
                    y++;
                    break;
                case 3:
                    x--;
                    break;
                default:
                    y--;
                    break;
            }
            khoang++;
        }
        int ngaunhien = (int) (Math.random() * (khoang));
        return ngaunhien; // Lấy random khoảng có thể đi được

    }

    public boolean SoSanh(int x, int y, int x1, int y1) // Hàm này so sánh 2 tọa độ trong map
    {
        return x == x1 && y == y1;
    }

    public void XuLyThoiGianGame() {
        if (Mang.ThoiGianGame / 10 > 3600) {
            Mang.gio = Mang.ThoiGianGame / (3600 * 10);
        }
        Mang.phut = (Mang.ThoiGianGame / 10 - Mang.gio * 3600) / 60;
        Mang.giay = Mang.ThoiGianGame / 10 % 60;
    }

    public void NhapMapTuFile(String tenFile) {
        try (Scanner sc = new Scanner(new File(tenFile))) {
            Mang.x = sc.nextInt();              // Tọa độ xe người chơi : x,y
            Mang.y = sc.nextInt();
            DiemCuoi.TargetFx = sc.nextInt();   // Tọa độ điểm cuối : target : x,y
            DiemCuoi.TargetFy = sc.nextInt();
            Mang.n = sc.nextInt();
            Mang.a = new int[Mang.n][2];
            for (int i = 0; i < Mang.n; i++) {
                for (int j = 0; j < 2; j++) {
                    Mang.a[i][j] = sc.nextInt();
                }
            }
            DoRongDai.b = new int[DoRongDai.countW][DoRongDai.countH];
            for (int i = 0; i < DoRongDai.countW; i++) {
                for (int j = 0; j < DoRongDai.countH; j++) {
                    DoRongDai.b[i][j] = sc.nextInt();
                }
            }
        } catch (Exception ex) {
            System.out.printf("Lỗi gặp phải: %s\n", ex.toString());
        }
    }
}
