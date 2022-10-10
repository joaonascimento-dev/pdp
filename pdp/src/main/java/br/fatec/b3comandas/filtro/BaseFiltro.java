package br.fatec.b3comandas.filtro;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseFiltro {
    protected final Integer id;
    protected final Integer limite;
    protected final Boolean exibirExcluidos;
    protected final List<Map.Entry<OrdemEnum, String>> listOrdem;

    public enum OrdemEnum {
        ASC, DESC;
    }

    public static class Builder {
        private Integer id = null;
        private Integer limite = null;
        private Boolean exibirExcluidos = false;
        private List<Map.Entry<OrdemEnum, String>> listOrdem = new ArrayList<>();

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder limite(Integer val) {
            limite = val;
            return this;
        }

        public Builder exibirExcluidos(Boolean val) {
            exibirExcluidos = val;
            return this;
        }

        public Builder listOrdem(List<Map.Entry<OrdemEnum, String>> val) {
            listOrdem = val;
            return this;
        }

        public BaseFiltro build() {
            return new BaseFiltro(this);
        }
    }

    public BaseFiltro(Builder builder) {
        this.id = builder.id;
        this.limite = builder.limite;
        this.exibirExcluidos = builder.exibirExcluidos;
        this.listOrdem = builder.listOrdem;
    }

    public Integer getId() {
        return id;
    }
    
    public Integer getLimite() {
        return limite;
    }

    public Boolean isExibirExcluidos() {
        return exibirExcluidos;
    }

    public List<Map.Entry<OrdemEnum, String>> getListOrdem() {
        return listOrdem;
    }
}
