public class EinsteinPositionConstraint {
    private int var1, value1, offset, var2, value2;

    public EinsteinPositionConstraint(int var1, int value1, int offset, int var2, int value2) {
        this.var1 = var1;
        this.value1 = value1;
        this.offset = offset;
        this.var2 = var2;
        this.value2 = value2;
    }

    public int getVar1() {
        return var1;
    }

    public void setVar1(int var1) {
        this.var1 = var1;
    }

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getVar2() {
        return var2;
    }

    public void setVar2(int var2) {
        this.var2 = var2;
    }

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }
}
