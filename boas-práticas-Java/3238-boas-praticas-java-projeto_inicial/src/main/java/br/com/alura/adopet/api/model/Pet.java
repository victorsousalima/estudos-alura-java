package br.com.alura.adopet.api.model;

import br.com.alura.adopet.api.dto.DadosCadastroPetDto;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoPet tipo;

    private String nome;

    private String raca;

    private Integer idade;

    private String cor;

    private Float peso;

    private Boolean adotado;

    @ManyToOne(fetch = FetchType.LAZY)
    private Abrigo abrigo;

    @OneToOne(mappedBy = "pet", fetch = FetchType.LAZY)
    private Adocao adocao;

    public Pet() {}

    public Pet(DadosCadastroPetDto dados) {
        this.tipo = dados.tipoPet();
        this.nome = dados.nome();
        this.raca = dados.raca();
        this.idade = dados.idade();
        this.cor = dados.cor();
        this.peso = dados.peso();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public TipoPet getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getRaca() {
        return raca;
    }

    public Integer getIdade() {
        return idade;
    }

    public String getCor() {
        return cor;
    }

    public Float getPeso() {
        return peso;
    }

    public Boolean getAdotado() {
        return adotado;
    }

    public Abrigo getAbrigo() {
        return abrigo;
    }

    public Adocao getAdocao() {
        return adocao;
    }

    public void adicionarAbrigoNoPetRecemCadastrado(Abrigo abrigo) {
        this.abrigo = abrigo;
        this.adotado = false;
    }
}
