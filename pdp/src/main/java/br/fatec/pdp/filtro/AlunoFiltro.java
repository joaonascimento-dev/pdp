package br.fatec.pdp.filtro;

public class AlunoFiltro extends BaseFiltro {
    private final String nome;
    
    public static class Builder extends BaseFiltro.Builder {
        private String nome = null;

        public Builder(/* parâmetros obrigatórios */) {
            // this.algumCampo = algumCampo;
        }

        public Builder nome(String val) {
            nome = val;
            return this;
        }

        public AlunoFiltro build() {
            return new AlunoFiltro(this);
        }
    }

    public AlunoFiltro(Builder builder) {
        super(builder);
        this.nome = builder.nome;
    }

    public String getNome() {
        return nome;
    }

}
