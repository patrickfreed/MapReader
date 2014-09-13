import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class MapReader {
    
    static final int brick = -213840;
    static ArrayList<ArrayList<Integer>> materials;
    
    public static void main(String[] args) throws IOException {       
        materials = new ArrayList<ArrayList<Integer>>();
        BufferedImage mm = ImageIO.read(new File("materials.png"));
        System.out.println(mm.getHeight());
        for(int x = 0; x < mm.getWidth() / 32; x++) {
            materials.add(readIcon(mm.getSubimage(x * 32, 0, 32, 32)));
        }
        
        //System.out.println(new Color(252, 188, 176).getRGB());
        //System.out.println(new File("img.png").getCanonicalPath());
        BufferedImage i = ImageIO.read(new File("1b.png"));
        
        
        byte[][] data = new byte[i.getHeight() / 32][i.getWidth() / 32];   
        
        System.out.println("Scanning...");
        
        for(int y = 0; y < data.length; y++) {
            for(int x = 0; x < data[0].length; x++) {
                BufferedImage img = i.getSubimage(x * 32, y * 32, 32, 32);
                
                data[y][x] = getData(img);
            }
        }
        
        System.out.println("...done!");
        
        write(data);
    }
    public static ArrayList<Integer> readIcon(BufferedImage mm){
        ArrayList<Integer> in = new ArrayList<Integer>();
        
        for(int x = 0; x < mm.getWidth() / 32; x++) {
            BufferedImage sub = mm.getSubimage(x * 32, 0, 32, 32);          
            
            for(int c = 0; c < 32; c++) {
               in.add(sub.getRGB(c, 16));
            }
        }
        
        return in;
    }
    public static void write(byte[][] data) throws IOException {
        FileWriter out = new FileWriter(new File("data.txt"));
        PrintWriter print = new PrintWriter(out);
        
        String dstring = "";
        
        for(int y = 0; y < data.length; y++) {
            for(int x = 0; x < data[0].length; x++) {
               dstring += data[y][x];
            }
            dstring += "\n";
        }
        
        print.printf("%s", dstring);
        print.close();
        System.out.println(dstring);
    }    
    
    public static byte getData(BufferedImage i) {
        ArrayList<Integer> ai = readIcon(i);
        
        for(int x = 0; x < materials.size(); x++) {
            if(ai.equals(materials.get(x))) {
                return 1;
            }
        }
        
        return 0;
    }
}
