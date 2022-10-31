package br.fatec.pdp.filtro;

public class EmpresaFiltro extends BaseFiltro {
    private final String nomefantasia;
    
    public static class Builder extends BaseFiltro.Builder {
        private String nomefantasia = null;

        public Builder(/* parâmetros obrigatórios */) {
            // this.algumCampo = algumCampo;
        }

        public Builder nomefantasia(String val) {
            nomefantasia = val;
            return this;
        }

        public EmpresaFiltro build() {
            return new EmpresaFiltro(this);
        }
    }

    public EmpresaFiltro(Builder builder) {
        super(builder);
        this.nomefantasia = builder.nomefantasia;
    }

    public String getNomeFantasia() {
        return nomefantasia;
    }

}
