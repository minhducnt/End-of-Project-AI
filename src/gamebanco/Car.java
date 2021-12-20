package gamebanco;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class Car extends GameObject {
    public int posW;              //vị trí ban đầu theo x của board1 
    public int posH;               //vị trí ban đầu theo y của board1
    public int ViTriW;             // tọa độ thực trên bản đồ
    public int ViTriH;
    public boolean LaNguoiChoi;
    public boolean LaBenNao;         //true : là phe ta, địch là false
    public int Huong;       //huong ngau nhien khi khong co dich
    public int KhoangDi = 0;
    
    DiChuyenNgauNhien dc = new DiChuyenNgauNhien();
    private BufferedImage carDich, carTa ,carNC, diemKetThuc ;
      
    public Car(int huong,int posW,int posH , boolean KoOrCo, boolean LaBenNao) { 
        this.Huong= huong;
        this.posW = posW;
        this.posH = posH;
        this.LaNguoiChoi = KoOrCo;                             // Có phải là ô tô của người chơi hay không
        this.LaBenNao = LaBenNao;
        
        TinhDoDaiThuc();
    }
    
    public void TinhDoDaiThuc(){
        this.ViTriW = this.posW * DoRongDai.cellW;
        this.ViTriH = this.posH * DoRongDai.cellH;
    }
    
    public void DiChuyenNgauNhien(){
        if(this.KhoangDi <= 0){
            this.Huong = dc.TimHuongPhuHop(this.posW,this.posH);
            this.KhoangDi = dc.KhoangCoTheDiDuoc(this.posW, this.posH, this.Huong);
        }
        if(this.KhoangDi > 0){
            this.DiChuyenDoanNgan();
        }
    }
    
    public void DiChuyenDoanNgan(){                         // Di chuyển nhảy với độ ngắn, nhất định
      if(Mang.ThoiGianGame % Mang.level == 0)   
      {   
         if(this.Huong == 1 )      
            this.posW ++;       //đi sang phải                     
         else if(this.Huong == 2  )   
            this.posH ++;       // đi xuông
         else if(this.Huong == 3  )
            this.posW --;        //di sang trai
         else if(this.Huong == 4)   
             this.posH --;        // di len tren      
         
         this.KhoangDi --;
         this.TinhDoDaiThuc();                                // Cập nhập độ dài thực
       }
      else {
         if(this.Huong == 1 )
             this.ViTriW += Mang.DoNhay;
         else if(this.Huong == 2  )   
             this.ViTriH += Mang.DoNhay;
         else if(this.Huong == 3  )
            this.ViTriW -= Mang.DoNhay;
         else if(this.Huong == 4 )   
            this.ViTriH -= Mang.DoNhay;   
      }
   }
   
   
    
    
    
    
    
    
    
    
    
    @Override
    public void Draw(Graphics g){
        
        if(LaNguoiChoi){
            try {
                carNC = ImageIO.read(new File("image\\truck"+Mang.carNC+".png"));
            } catch (IOException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
            g.drawImage(carNC, ViTriW , ViTriH , null);
       }    
        else if(LaBenNao == false){                           // Nếu là đội địch
           try {
                carDich = ImageIO.read(new File("image\\Dich.png"));
            } catch (IOException ex) {
               Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
            g.drawImage(carDich, ViTriW , ViTriH , null);
        } 
        else if(LaBenNao == true ){                          // Nếu là Phe Ta
            try {
                carTa = ImageIO.read(new File("image\\car"+Mang.car+".png"));
            } catch (IOException ex) {
               Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
            g.drawImage(carTa, ViTriW , ViTriH , null);
        }
     
//        g.fillOval(posW *DoRongDai.cellW, posH * DoRongDai.cellH,DoRongDai.cellW,DoRongDai.cellH);    //vẽ chiếc xe
       if(DiemCuoi.DichConOrKhong){                   // Nếu đích còn thì mới vẽ ra chứ
           try {
               diemKetThuc = ImageIO.read(new File("image\\thanh.png"));
            } catch (IOException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.drawImage(diemKetThuc, DiemCuoi.TargetFx *DoRongDai.cellW, DiemCuoi.TargetFy * DoRongDai.cellH, null);
       }
        //g.fillOval(DiemCuoi.TargetFx *DoRongDai.cellW, DiemCuoi.TargetFy * DoRongDai.cellH,DoRongDai.cellW,DoRongDai.cellH);   //vẽ điềm kết thúc
    }  
  
}