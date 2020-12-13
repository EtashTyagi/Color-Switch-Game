package Code;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerFactory {
    private PlayerFactory() {}
    public static Player createPlayer(String username, String password) {
        PlayerData data = new PlayerData();
        data.load();
        if (data.usernamePasswordMap.containsKey(username)) {
            return null;
        } else {
            data.usernamePasswordMap.put(username, password);
            data.save();
            Player newPlayer = new Player(username, password);
            newPlayer.save();
            return newPlayer;
        }
    }
    public static ArrayList<String> getAllPlayerNames() {
        PlayerData pd = new PlayerData();
        pd.load();
        return new ArrayList<>(pd.usernamePasswordMap.keySet());
    }
    public static Player validate(String userName, String password) {
        PlayerData pd = new PlayerData();
        pd.load();
        return (pd.usernamePasswordMap.containsKey(userName) && pd.usernamePasswordMap.get(userName).equals(password)) ?
                (Player.load(userName)) : (null);
    }
    private static class PlayerData implements Serializable {
        private HashMap<String, String> usernamePasswordMap = new HashMap<>();
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
                in.close();
                fileIn.close();
            } catch (IOException | ClassNotFoundException ignore) {

            }
        }
    }
}

