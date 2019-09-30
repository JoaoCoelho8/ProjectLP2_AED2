package edu.ufp.inf.projeto;

import edu.princeton.cs.algs4.SeparateChainingHashST;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class PontoPassagem implements Serializable {

    private static final AtomicInteger count = new AtomicInteger(0);

    private int id;

    private String nome;

    private Coordenada localizacao;

    public PontoPassagem(String nome, Coordenada localizacao) {
        this.id = new UniqueID().getId();
        this.nome = nome;
        this.localizacao = localizacao;
    }

    public static AtomicInteger getCount() {
        return count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Coordenada getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Coordenada localizacao) {
        this.localizacao = localizacao;
    }
}
