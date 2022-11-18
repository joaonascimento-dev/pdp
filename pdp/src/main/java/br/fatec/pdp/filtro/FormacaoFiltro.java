package br.fatec.pdp.filtro;

public class FormacaoFiltro extends BaseFiltro {
    
    
    public static class Builder extends BaseFiltro.Builder {
        

        public Builder(/* parâmetros obrigatórios */) {
            // this.algumCampo = algumCampo;
        }

        

        public FormacaoFiltro build() {
            return new FormacaoFiltro(this);
        }
    }

    public FormacaoFiltro(Builder builder) {
        super(builder);
        
    }

    

}
