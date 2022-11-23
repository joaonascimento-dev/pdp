package br.fatec.pdp.filtro;

public class VagaFiltro extends BaseFiltro {
    private final String empresa;
    private final Boolean aprovacao;
    private final boolean ativo;
    private final String busca;
    
    public static class Builder extends BaseFiltro.Builder {
        private String empresa = null;
        private Boolean aprovacao = true;
        private boolean ativo = true;
        private String busca = null;

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

        public Builder busca(String val) {
            busca = val;
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
        this.busca = builder.busca;
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

    public String getBusca() {
        return busca;
    }
}
