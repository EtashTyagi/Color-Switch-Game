package Code;

import javafx.application.Platform;
import javafx.scene.control.TableView;

import java.io.*;
import java.util.ArrayList;

public class Player implements Serializable, Cloneable {
    private ArrayList<Game> savedEndlessGames = new ArrayList<>();
    private int endlessHighScore;
    private int totalScore;
    private String password;
    private final String userName;

    public Player(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    public void saveGame(Game toSave) {
        savedEndlessGames.add(toSave);
        save();
    }
    public ArrayList<String> getSavedEndlessGames() {
        ArrayList<String> ans = new ArrayList<>();
        int index = 0;
        for (Game game : savedEndlessGames) {
            ans.add("Save("+index+") " + "[score="+game.getCurScore()+"]");
            index++;
        }
        return ans;
    }
    public Game getSaveGameNo(int index) {
        assert index <= savedEndlessGames.size() && index >= 0;
        return savedEndlessGames.get(index);
    }
    public int getBalance() {
        return totalScore;
    }
    public void saveScore(int score) {
        assert score >= 0;
        totalScore+=score;
        this.endlessHighScore = Math.max(score, endlessHighScore);
        save();
    }
    public boolean canDoTransaction(int by) {
        return by <= totalScore;
    }
    public void doTransaction(int by) {
        if (canDoTransaction(by)) {
            totalScore -= by;
        }
    }
    public int getEndlessHighScore() {
        return endlessHighScore;
    }
    public String getUserName() {
        return userName;
    }
    public void save() {
        try {
            PlayerFactory.savePlayerHighScore(userName, endlessHighScore);
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
    @Override
    protected Player clone() throws CloneNotSupportedException {
        return (Player) super.clone();
    }
}
