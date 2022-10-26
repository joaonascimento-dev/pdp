package br.fatec.pdp.filtro;

public class VagaFiltro extends BaseFiltro {
    private final String empresa;
    private final boolean aprovacao;
    private final boolean ativo;
    
    public static class Builder extends BaseFiltro.Builder {
        private String empresa = null;
        private boolean aprovacao = true;
        private boolean ativo = true;

        public Builder(/* parâmetros obrigatórios */) {
            // this.algumCampo = algumCampo;
        }

        public Builder empresa(String val) {
            empresa = val;
            return this;
        }

        public Builder aprovacao(boolean val) {
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

    public boolean isAprovacao() {
        return aprovacao;
    }

    public boolean isAtivo() {
        return ativo;
    }

}
