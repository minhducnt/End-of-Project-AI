
package gamebanco;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JTextField;

public class Board extends GameObject{
    
    
    Random ra = new Random();
    Color color;
    private BufferedImage anh, wall , nen , wing;
    @Override
    public void Draw(Graphics g) { 
        
        color = new Color(ra.nextInt(256), ra.nextInt(256),ra.nextInt(256));
        for (int i = 0; i < DoRongDai.countH; i++) {
            for (int j = 0; j < DoRongDai.countW; j++) {
                int x = j * DoRongDai.cellW;
                int y = i * DoRongDai.cellH;
                
                if( DoRongDai.b[i][j] == 1){              
                    try {
                    wall = ImageIO.read(new File("image\\Wall\\wall"+Mang.tuong+".png"));
                } catch (IOException ex) {
                    Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                }
                    g.drawImage(wall, x, y,32, 32, null);
                }
                else if( DoRongDai.b[i][j] != 1){              
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, DoRongDai.cellW, DoRongDai.cellH);
                }
            }
        }
         
        try {
            anh = ImageIO.read(new File("image\\spkt.png"));
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.drawImage(anh, 480, 0, 200, 200, null);
        
        try {
            wing = ImageIO.read(new File("image\\Angle.png"));
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.drawImage(wing, 480,370, null);
        
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20)); //font, type, size
        if(ToaDoBTL.ngang > 480 && ToaDoBTL.chay == 1)
            ToaDoBTL.ngang --;
        if(ToaDoBTL.ngang < 550 && ToaDoBTL.chay == 2)
            ToaDoBTL.ngang ++;
        if(ToaDoBTL.ngang == 480)
            ToaDoBTL.chay = 2;
        if(ToaDoBTL.ngang == 550)
            ToaDoBTL.chay = 1;
               
        g.drawString("ĐỒ ÁN MÔN HỌC", ToaDoBTL.ngang  , ToaDoBTL.doc);
        g.setColor(color);
        g.drawString("TRÍ TUỆ NHÂN TẠO", 487, 260);
        
        g.setColor(Color.BLACK);
        
        g.drawString("Điểm địch: " + Mang.DiemDich,500, 300);
        g.drawString("Điểm ta: " + Mang.DiemTa,500, 330);
        g.drawString("Thời gian: " + Mang.gio + ":" + Mang.phut +":" + Mang.giay , 500, 360);
        g.drawString("Level " + (7- Mang.level), 580 , 400);
        g.drawString("Nhóm 5 ", 580, 430);
    }
}
class ToaDoBTL
{
    static int ngang = 600;
    static int doc = 230;
    static int chay = 1; // ! la chay trai, 2 la chay phai
}

