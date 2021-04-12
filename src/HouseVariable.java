public class HouseVariable {
    private int houseIndex;
    private int varIndex;

    public HouseVariable(int houseIndex, int varIndex){
        this.houseIndex = houseIndex;
        this.varIndex = varIndex;
    }

    public int getHouseIndex() {
        return houseIndex;
    }

    public void setHouseIndex(int houseIndex) {
        this.houseIndex = houseIndex;
    }

    public int getVarIndex() {
        return varIndex;
    }

    public void setVarIndex(int varIndex) {
        this.varIndex = varIndex;
    }
}
