import java.util.ArrayList;

public class EinsteinArc {
    private int var1, val1, var2, val2;

    public EinsteinArc(int var1, int val1, int var2, int val2){
        this.var1= var1;
        this.val1 = val1;
        this.var2 = var2;
        this.val2 = val2;
    }
    public int getVar1() {
        return var1;
    }

    public void setVar1(int var1) {
        this.var1 = var1;
    }

    public int getVal1() {
        return val1;
    }

    public void setVal1(int val1) {
        this.val1 = val1;
    }

    public int getVar2() {
        return var2;
    }

    public void setVar2(int var2) {
        this.var2 = var2;
    }

    public int getVal2() {
        return val2;
    }

    public void setVal2(int val2) {
        this.val2 = val2;
    }
    public boolean existsInList(ArrayList<EinsteinArc> arcs) {
        for (int i = 0; i < arcs.size(); i++) {
            if (this.var1 == arcs.get(i).var1 && this.val1 == arcs.get(i).val1 &&this.var2 == arcs.get(i).var2 && this.val2 == arcs.get(i).val2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Arc: Var1: "+ getVar1()+", Val1; "+getVal1()+", Var2: "+getVar2()+", Val2: "+getVal2();
    }
}
