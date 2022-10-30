package br.fatec.pdp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // private Empresa empresa; FAZER

    private boolean aprovacao;

    // definição de tamanho
    private String titulo;

    // Text
    private String descricao;

    private boolean ativo;

    @OneToMany(mappedBy = "vaga")
    private List<AlunoVaga> listAlunoVaga = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isAprovacao() {
        return aprovacao;
    }

    public void setAprovacao(boolean aprovacao) {
        this.aprovacao = aprovacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<AlunoVaga> getListAlunoVaga() {
        return listAlunoVaga;
    }

    public void setListAlunoVaga(List<AlunoVaga> listAlunoVaga) {
        this.listAlunoVaga = listAlunoVaga;
    }

    
}