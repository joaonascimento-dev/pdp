package br.fatec.pdp.filtro;

public class EmpresaFiltro extends BaseFiltro {
    private final String nomefantasia;
    private final Boolean aprovacao;
    
    public static class Builder extends BaseFiltro.Builder {
        private String nomefantasia = null;
        private Boolean aprovacao = true;
        
        public Builder(/* parâmetros obrigatórios */) {
            // this.algumCampo = algumCampo;
        }

        public Builder nomefantasia(String val) {
            nomefantasia = val;
            return this;
        }

        public Builder aprovacao(Boolean val) {
            aprovacao = val;
            return this;
        }

        public EmpresaFiltro build() {
            return new EmpresaFiltro(this);
        }
    }

    public EmpresaFiltro(Builder builder) {
        super(builder);
        this.nomefantasia = builder.nomefantasia;
        this.aprovacao = builder.aprovacao;
    }

    public String getNomeFantasia() {
        return nomefantasia;
    }
    
    public Boolean isAprovacao() {
        return aprovacao;
    }

}
