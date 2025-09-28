public class Player {
    private int totalCapyNum;
    private String name;

    public Player(String n, int initialCapyNum) {
        this.name = n;
        this.totalCapyNum = initialCapyNum;

    }

    public int getTotalCapyNum() {
        return totalCapyNum;
    }

    public void setTotalCapyNum(int totalCapyNum) {
        this.totalCapyNum = totalCapyNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCapy(int num) {
        this.totalCapyNum += num;
    }

    public void removeCapy(int num) {
        this.totalCapyNum -= num;
    }



    
    
}
