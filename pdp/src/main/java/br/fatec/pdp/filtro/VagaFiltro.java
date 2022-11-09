package br.fatec.pdp.filtro;

public class VagaFiltro extends BaseFiltro {
    private final String empresa;
    private final Boolean aprovacao;
    private final boolean ativo;
    
    public static class Builder extends BaseFiltro.Builder {
        private String empresa = null;
        private Boolean aprovacao = true;
        private boolean ativo = true;

        public Builder(/* parâmetros obrigatórios */) {
            // this.algumCampo = algumCampo;
        }

        public Builder empresa(String val) {
            empresa = val;
            return this;
        }

        public Builder aprovacao(Boolean val) {
            aprovacao = val;
            return this;
        }

        public Builder ativo(boolean val) {
            ativo = val;
            return this;
        }

        public VagaFiltro build() {
            return new VagaFiltro(this);
        }
    }

    public VagaFiltro(Builder builder) {
        super(builder);
        this.empresa = builder.empresa;
        this.aprovacao = builder.aprovacao;
        this.ativo = builder.ativo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public Boolean isAprovacao() {
        return aprovacao;
    }

    public boolean isAtivo() {
        return ativo;
    }

}
