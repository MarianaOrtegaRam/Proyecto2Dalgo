
public class elemento {

    private Atomo atomo1;
    private Atomo atomo2;

    public elemento(int a1, int a2) {
        boolean carga1;
        boolean carga2;
        int abs1 = Math.abs(a1);
        int abs2 = Math.abs(a2);

        if (a1 > 0) {
            carga1 = true;
        } else {
            carga1 = false;
        }

        if (a2 > 0) {
            carga2 = true;
        } else {
            carga2 = false;
        }

        Atomo atom1 = new Atomo(abs1, carga1, a1);
        Atomo atom2 = new Atomo(abs2, carga2, a2);

        this.atomo1 = atom1;
        this.atomo2 = atom2;
    }

    public Atomo getAtomo1() {
        return this.atomo1;
    }

    public Atomo getAtomo2() {
        return this.atomo2;
    }
}