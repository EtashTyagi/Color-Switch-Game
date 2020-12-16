package Code;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.*;

public class PlayerFactory {
    private PlayerFactory() {}
    public static Player createPlayer(String username, String password) {
        PlayerData data = new PlayerData();
        data.load();
        if (data.usernamePasswordMap.containsKey(username)) {
            return null;
        } else {
            data.usernamePasswordMap.put(username, password);
            data.highScore.put(username, 0);
            data.save();
            Player newPlayer = new Player(username, password);
            newPlayer.save();
            return newPlayer;
        }
    }
    public static Player validate(String userName, String password) {
        PlayerData pd = new PlayerData();
        pd.load();
        return (pd.usernamePasswordMap.containsKey(userName) && pd.usernamePasswordMap.get(userName).equals(password)) ?
                (Player.load(userName)) : (null);
    }
    public static void savePlayerHighScore(String name, int highScore) {
        PlayerData pd = new PlayerData();
        pd.load();
        assert pd.highScore.containsKey(name);
        pd.highScore.put(name, Math.max(pd.highScore.get(name), highScore));
        pd.save();
    }
    public static ArrayList<TableData> getHighScoreData() {
        PlayerData pd = new PlayerData();
        pd.load();
        ArrayList<TableData> ans = new ArrayList<>();
        Set<String> ks = pd.highScore.keySet();
        for (String name : ks) {
            ans.add(new TableData(0, name, pd.highScore.get(name)));
        }
        ans.sort(Comparator.comparing((a) -> -a.getScore()));
        int rank = 1;
        for (TableData data : ans) {
            data.setRank(rank++);
        }
        return ans;
    }
    private static class PlayerData implements Serializable {
        private HashMap<String, String> usernamePasswordMap = new HashMap<>();
        private HashMap<String, Integer> highScore = new HashMap<>();
        private void save() {
            try {
                FileOutputStream fileOut =
                        new FileOutputStream("Save Files/playersData.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(this);
                out.close();
                fileOut.close();
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
        private void load() {
            try {
                FileInputStream fileIn = new FileInputStream("Save Files/playersData.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                PlayerData p = (PlayerData) in.readObject();
                this.usernamePasswordMap = p.usernamePasswordMap;
                this.highScore = p.highScore;
                in.close();
                fileIn.close();
            } catch (IOException | ClassNotFoundException ignore) {

            }
        }
    }

}

