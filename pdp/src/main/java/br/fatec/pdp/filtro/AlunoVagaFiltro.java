package br.fatec.pdp.filtro;

import br.fatec.pdp.model.Aluno;
import br.fatec.pdp.model.Vaga;

public class AlunoVagaFiltro extends BaseFiltro {
    private final Aluno aluno;
    private final Vaga vaga;
    
    public static class Builder extends BaseFiltro.Builder {
        private Aluno aluno = null;
        private Vaga vaga = null;

        public Builder(/* parâmetros obrigatórios */) {
            // this.algumCampo = algumCampo;
        }

        public Builder aluno(Aluno val) {
            aluno = val;
            return this;
        }

        public Builder vaga(Vaga val) {
            vaga = val;
            return this;
        }

        public AlunoVagaFiltro build() {
            return new AlunoVagaFiltro(this);
        }
    }

    public AlunoVagaFiltro(Builder builder) {
        super(builder);
        this.aluno = builder.aluno;
        this.vaga = builder.vaga;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Vaga getVaga() {
        return vaga;
    }
}
