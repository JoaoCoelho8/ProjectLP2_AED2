package edu.ufp.inf.projeto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class HistoricoEmpresa implements Serializable {

    private static final AtomicInteger count = new AtomicInteger(0);

    private int id;

    private ArrayList<Empresa> empresas= new ArrayList<>();

    private Profissional profissional;

    public HistoricoEmpresa(Profissional profissional) {
        this.id = new UniqueID().getId();
        this.profissional = profissional;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(ArrayList<Empresa> empresas) {
        this.empresas = empresas;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    /**
     * percorrer arrayList empresas e se a empresa ja existir return; se não add empresa ao arrayList empresas
     * @param e
     */
    public void addEmpresa(Empresa e){
        for (Empresa ei: empresas) {
            if(ei.getId()==e.getId())
            {
                return;
            }
        }
        this.empresas.add(e);
    }

    /**
     * percorrer arrayList empresas e se a empresa existir remover empresa ao arrayList empresas
     * @param e
     */
    public void removeEmpresa(Empresa e){
        for (Empresa ei: empresas) {
            if(ei.getId()==e.getId())
            {
                empresas.remove(ei);
            }
        }
    }

    @Override
    public String toString() {
        String a = "Profissional: " + this.profissional.getNome();

        a += "Histórico de empresas:";
        for (Empresa empresa : empresas) {
            a += empresa.getNome() + "\n";

        }
        return a;
    }
}