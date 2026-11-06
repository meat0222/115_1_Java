// 契約與教師版相同；佔位值只是讓骨架能啟動，不代表完成需求。
public class MissionStep {
    private int command;
    private int energy;
    private String result;

    public MissionStep(int command, int energy, String result) {
        // TODO 1：以 this 將三個參數保存到目前物件的三個欄位。
        this.command = command;
        this.energy = energy;
        this.result = result;
    }

    // TODO 2：三個 getter 讀取本物件對應欄位。
    public int getCommand() {
        return command;
    }

    public int getEnergy() {
        return energy;
    }

    public String getResult() {
        return result;
    }

    public boolean isSuccessful() {
        // TODO 3：以內容比較（equals），不是 == 比參考。
        return "OK".equals(result);
    }
}
