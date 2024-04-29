public class Atomo {

    private Integer masa;
    private boolean carga;
    private Integer cargaMasa;

    public Atomo(Integer masa, boolean carga, Integer cargaMasa) {
        this.carga = carga;
        this.masa = masa;
        this.cargaMasa = cargaMasa;
    }

    public Integer getCargaMasa() {
        return this.cargaMasa;
    }

    public boolean getCarga() {
        return this.carga;
    }

    public Integer getMasa() {
        return this.masa;
    }
}
