public class EinsteinConstraint {
    private int[] constraintSet;

    public EinsteinConstraint(int[] constraintSet) {
        this.constraintSet = new int[6];
        for (int i = 0; i < 6; i++) {
            this.constraintSet[i] = constraintSet[i];
        }
    }

    public int[] getConstraintSet() {
        return constraintSet;
    }

    public void setConstraintSet(int[] constraintSet) {
        for (int i = 0; i < this.constraintSet.length; i++) {
            this.constraintSet[i] = constraintSet[i];
        }
    }

    @Override
    public String toString() {
        String display = "[|";
        for (int i = 0; i < 6; i++) {
            display += this.constraintSet[i]+"|";
        }
        display+="]";
        return display;
    }
}
