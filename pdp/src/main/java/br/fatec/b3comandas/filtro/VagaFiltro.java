package br.fatec.b3comandas.filtro;

public class VagaFiltro extends BaseFiltro {
    private final String empresa;
    
    public static class Builder extends BaseFiltro.Builder {
        private String empresa = null;

        public Builder(/* parâmetros obrigatórios */) {
            // this.algumCampo = algumCampo;
        }

        public Builder empresa(String val) {
            empresa = val;
            return this;
        }

        public VagaFiltro build() {
            return new VagaFiltro(this);
        }
    }

    public VagaFiltro(Builder builder) {
        super(builder);
        this.empresa = builder.empresa;
    }

    public String getEmpresa() {
        return empresa;
    }

}
