package br.fatec.b3comandas.filtro;

public class UsuarioFiltro extends BaseFiltro {
    private final String login;
    
    public static class Builder extends BaseFiltro.Builder {
        private String login = null;

        public Builder(/* parâmetros obrigatórios */) {
            // this.algumCampo = algumCampo;
        }

        public Builder login(String val) {
            login = val;
            return this;
        }

        public UsuarioFiltro build() {
            return new UsuarioFiltro(this);
        }
    }

    public UsuarioFiltro(Builder builder) {
        super(builder);
        this.login = builder.login;
    }

    public String getLogin() {
        return login;
    }

}
