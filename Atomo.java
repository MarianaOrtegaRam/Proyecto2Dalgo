public class Atomo {

    private int masa;
    private boolean carga;
    private int cargaMasa;

    public Atomo(int masa, boolean carga, int cargaMasa) {
        this.carga = carga;
        this.masa = masa;
        this.cargaMasa = cargaMasa;
    }

    public int getCargaMasa() {
        return this.cargaMasa;
    }

    public boolean getCarga() {
        return this.carga;
    }

    public int getMasa() {
        return this.masa;
    }
}
