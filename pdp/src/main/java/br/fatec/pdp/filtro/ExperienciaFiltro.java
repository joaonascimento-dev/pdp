package br.fatec.pdp.filtro;

public class ExperienciaFiltro extends BaseFiltro {
    
    
    public static class Builder extends BaseFiltro.Builder {
        

        public Builder(/* parâmetros obrigatórios */) {
            // this.algumCampo = algumCampo;
        }

        

        public ExperienciaFiltro build() {
            return new ExperienciaFiltro(this);
        }
    }

    public ExperienciaFiltro(Builder builder) {
        super(builder);
        
    }

    

}
