package Code;

import java.io.*;
import java.util.ArrayList;

public class Player implements Serializable {
    private ArrayList<Game> savedEndlessGames; // Also Timed Endless
    private int endlessHighScore;
    private int timedHighScore; // TODO IMPLEMENT OR REMOVE
    private String password;
    private String userName;

    public Player(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    public void setEndlessHighScore(int score) {
        this.endlessHighScore = score;
    }
    public int getEndlessHighScore() {
        return endlessHighScore;
    }
    public String getUserName() {
        return userName;
    }
    public void save() {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("Save Files/Players/" +userName+ ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    public static Player load(String name) {
        try {
            FileInputStream fileIn = new FileInputStream("Save Files/Players/" +name+ ".ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Player player = (Player) in.readObject();
            in.close();
            fileIn.close();
            return player;
        } catch (IOException | ClassNotFoundException ignore) {
            return null; // This Should Not Happen
        }
    }
}
