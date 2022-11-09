package br.fatec.pdp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @CreationTimestamp
    private LocalDateTime criacao;
    @UpdateTimestamp
    private LocalDateTime atualizacao;

    @OneToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    private String rg;
    private String cpf;
    private String ra;
    private String nome;
    private String nomeSocial;
    
    @Column(columnDefinition = "varchar(255) default 'Fatec Itapira - \"Dr. Ogari de Castro Pacheco\"' ")
    private String instituição;

    private LocalDateTime dataInicio;
    private LocalDateTime dataTermino;
    private String telefone;
    private String email;
    private LocalDateTime dataNasc;
    private String sexo;
    private String nacionalidade;
    private String logradouro;
    private String cidade;
    private String estado;
    private String pais;
    private String complemento;
    private String bairro;
    private String numero;
    private String cep;

    @OneToMany(mappedBy = "aluno")
    private List<AlunoVaga> listAlunoVaga = new ArrayList<>();

    @OneToMany(mappedBy = "aluno")
    private List<Experiencia> listExperiencia = new ArrayList<>();

    @OneToMany(mappedBy = "aluno")
    private List<Formacao> listFormacao = new ArrayList<>();

    @OneToMany(mappedBy = "aluno")
    private List<Habilidade> listHabilidade = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCriacao() {
        return criacao;
    }

    public void setCriacao(LocalDateTime criacao) {
        this.criacao = criacao;
    }

    public LocalDateTime getAtualizacao() {
        return atualizacao;
    }

    public void setAtualizacao(LocalDateTime atualizacao) {
        this.atualizacao = atualizacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public String getInstituição() {
        return instituição;
    }

    public void setInstituição(String instituição) {
        this.instituição = instituição;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDateTime dataTermino) {
        this.dataTermino = dataTermino;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDateTime dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public List<AlunoVaga> getListAlunoVaga() {
        return listAlunoVaga;
    }

    public void setListAlunoVaga(List<AlunoVaga> listAlunoVaga) {
        this.listAlunoVaga = listAlunoVaga;
    }

    public List<Experiencia> getListExperiencia() {
        return listExperiencia;
    }

    public void setListExperiencia(List<Experiencia> listExperiencia) {
        this.listExperiencia = listExperiencia;
    }

    public List<Formacao> getListFormacao() {
        return listFormacao;
    }

    public void setListFormacao(List<Formacao> listFormacao) {
        this.listFormacao = listFormacao;
    }

    public List<Habilidade> getListHabilidade() {
        return listHabilidade;
    }

    public void setListHabilidade(List<Habilidade> listHabilidade) {
        this.listHabilidade = listHabilidade;
    }

    
    
    
}
