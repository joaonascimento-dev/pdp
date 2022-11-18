package br.fatec.pdp.filtro;

public class HabilidadeFiltro extends BaseFiltro {
    
    
    public static class Builder extends BaseFiltro.Builder {
        

        public Builder(/* parâmetros obrigatórios */) {
            // this.algumCampo = algumCampo;
        }

        

        public HabilidadeFiltro build() {
            return new HabilidadeFiltro(this);
        }
    }

    public HabilidadeFiltro(Builder builder) {
        super(builder);
        
    }

    

}
