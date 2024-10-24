package br.com.alura.adopet.api.model;

import br.com.alura.adopet.api.dto.DadosAtualizarTutorDto;
import br.com.alura.adopet.api.dto.DadosCadastroTutorDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tutores")
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String telefone;

    private String email;

    @OneToMany(mappedBy = "tutor")
    private List<Adocao> adocoes;

    public Tutor() {}

    public Tutor(DadosCadastroTutorDto dados) {
        this.nome = dados.nome();
        this.telefone = dados.telefone();
        this.email = dados.email();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tutor tutor = (Tutor) o;
        return Objects.equals(id, tutor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public List<Adocao> getAdocoes() {
        return adocoes;
    }

    public void atualizarDados(DadosAtualizarTutorDto dados) {
        this.nome = dados.nome() != null ? dados.nome() : this.nome;
        this.email = dados.email() != null ? dados.email() : this.email;
        this.telefone = dados.telefone() != null ? dados.telefone() : this.telefone;
    }
}
