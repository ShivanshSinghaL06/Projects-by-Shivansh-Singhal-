package src;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Score {
    private int score;

    
    public Score(){
        this.score=0;
    }

    
    public void increaseScore(){
        this.score++;
    }

    
    public void resetScore(){
        this.score=0;
    }

    
    public int getScore(){
        return this.score;
    }

    
    public String getHighScore() {
        FileReader readFile = null;
        BufferedReader reader = null;
        try {
            
            readFile = new FileReader("highscore.dat");
            reader = new BufferedReader(readFile);

            String line = reader.readLine();
            String allLines = line;

            while (line != null) {
                
                line = reader.readLine();
                
                if (line == null)
                    break;
                
                allLines = allLines.concat("\n" + line);
            }

            
            return allLines;
        }
        
        catch (Exception e) {
            return "0\n0\n0\n0\n0\n0\n0\n0\n0\n0";
        } finally {
            try {
                // Tutup readernya
                if (reader != null)
                    reader.close();
            } // Kalau terjadi exception
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    
    public void sortHighScore() {
        FileReader readFile = null;
        BufferedReader reader = null;
        FileWriter writeFile = null;
        BufferedWriter writer = null;
        List<Integer> list = new ArrayList<Integer>();
        try {
            readFile = new FileReader("highscore.dat");
            reader = new BufferedReader(readFile);

            String line = reader.readLine();

            
            while (line != null) {
                list.add(Integer.parseInt(line));
                line = reader.readLine();
            }

           
            Collections.sort(list);

            
            Collections.reverse(list);

            
            writeFile = new FileWriter("highscore.dat");
            writer = new BufferedWriter(writeFile);

            int size = list.size();

            
            for (int i = 0; i < 10; i++) {
                
                if (i > size - 1) {
                    String def = "0";
                    writer.write(def);
                } else { // Ambil satu satu dari list
                    String str = String.valueOf(list.get(i));
                    writer.write(str);
                }
                if (i < 9) {
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            return;
        } finally {
            try {
                
                if (reader != null)
                    reader.close();
                
                if (writer != null)
                    writer.close();
            } 
            catch (IOException e) {
                return;
            }
        }

    }

  
    public void saveNewScore() {
        String newScore = String.valueOf(this.getScore());

       
        File scoreFile = new File("highscore.dat");

        
        if (!scoreFile.exists()) {
            try {
                
                scoreFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileWriter writeFile = null;
        BufferedWriter writer = null;

        try {
            
            writeFile = new FileWriter(scoreFile, true);
            writer = new BufferedWriter(writeFile);
            writer.write(newScore);
        } catch (Exception e) {
            return;
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (Exception e) {
                return;
            }
        }

    }
}
