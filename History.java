public class History {
    private int id;
    private String actionDone;
    private String dataAndTime;

    public History() {
    }

    public History(int id, String actionDone, String dataAndTime) {
        this.id = id;
        this.actionDone = actionDone;
        this.dataAndTime = dataAndTime;
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", actionDone='" + actionDone + '\'' +
                ", dataAndTime='" + dataAndTime + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActionDone() {
        return actionDone;
    }

    public void setActionDone(String actionDone) {
        this.actionDone = actionDone;
    }

    public String getDataAndTime() {
        return dataAndTime;
    }

    public void setDataAndTime(String dataAndTime) {
        this.dataAndTime = dataAndTime;
    }
}
