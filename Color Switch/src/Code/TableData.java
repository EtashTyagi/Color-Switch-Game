package Code;

public class TableData {
    private int rank;
    private String name;
    private int score;
    TableData(int rank, String name, int score) {
        this.rank = rank;
        this.name = name;
        this.score = score;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
    public int getRank() {
        return rank;
    }
    public String getName() {
        return name;
    }
    public int getScore() {
        return score;
    }
}
