package edu.ufp.inf.projeto;

import edu.princeton.cs.algs4.SeparateChainingHashST;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Encontro implements Serializable {

    private static final AtomicInteger count = new AtomicInteger(0);

    private int id;

    private Date data;

    private Coordenada localizacao;

    private String nome;

    private Empresa empresa;

    /**
     * @element-type Profissional
     */
    private SeparateChainingHashST<Integer, Profissional> profissionais = new SeparateChainingHashST<>();

    public Encontro(Date data, Coordenada localizacao, String nome) {
        this.id = new UniqueID().getId();
        this.data = data;
        this.localizacao = localizacao;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Coordenada getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Coordenada localizacao) {
        this.localizacao = localizacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) throws EncontroExistenteException {
        empresa.addEncontro(this);
        this.empresa= empresa;
        this.profissionais= empresa.getProfissionais();
        for (Integer integer:empresa.getProfissionais().keys()) {
            empresa.getProfissionais().get(integer).addEncontro(this);
        }
    }

    public SeparateChainingHashST<Integer, Profissional> getProfissionais() {
        return profissionais;
    }

    public void setProfissionais(SeparateChainingHashST<Integer, Profissional> profissionais) {
        this.profissionais = profissionais;
    }

    /**
     * percorrer ST de profissionais e verificar se o profissional ja existe, se não existir adicionamos à ST este profissional e vamos ao profissional e adicionamos tambem este encontro
     * @param p
     */
    public void addProfissional(Profissional p){
        for (Integer pi: profissionais.keys()) {
            if(profissionais.get(pi).getId()==p.getId())
            {
                System.out.println("Profissional já existe!");
                return;
            }
        }
        this.profissionais.put(p.getId(), p);
        p.addEncontro(this);
    }

    /**
     * vamos á ST de profissionais e se ele existir removemos da ST e vamos ao profissional e removemos este encontro dele
     * @param p
     */
    public void removeProfissional(Profissional p){
        for (Integer pi: profissionais.keys()) {
            if(profissionais.get(pi).getId()==p.getId())
            {
                profissionais.delete(pi);
                p.removerEncontro(this.getData());
            }
        }
    }

    /**
     * vamos á ST de profissinais e listamos todos os que existirem
     */
    public void listarConvidados() {
        for (Integer p1: profissionais.keys()) {
            System.out.println(profissionais.get(p1).getNome()+"-Convidado\n");
        }
    }

    /**
     * dá imformação do numero de participantes/profissionais no encontro
     * @return
     */
    public int contarParticipantes() {
        return profissionais.size();
    }

    /**
     * chamamos a função validarProfissional
     * @return
     */
    public boolean validar(){
        return validarProfissional();
    }

    /**
     * validamos um profissional
     * @return false se nao trabalha nem tem nenhuma empresa, true se trabalhar ou tiver uma empresa
     */
    public boolean validarProfissional(){
        for (Integer p: profissionais.keys()) {
            if(!profissionais.get(p).isTrabalha() || profissionais.get(p).getEmpresa().getId()!=this.empresa.getId()){
                return false;
            }
        }
        return true;
    }

    /**
     * gravamos para um ficheiro txt de encontros
     * @throws IOException
     */
    public void gravarEncontro() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("data/encontros.txt", true));
        writer.append(this.getEmpresa().getNome()+": "+this.getNome()+"; "+this.getLocalizacao());
        if(!this.getProfissionais().isEmpty()){
            writer.append("; Convidados: ");
            for (Integer i: this.getProfissionais().keys()) {
                writer.append(this.getProfissionais().get(i).getNome());
                writer.append(", ");
            }
        }

        writer.newLine();
        writer.close();
    }

    /**
     * carregamos de um ficheiro txt de novos encontros e guardamos numa redBlack
     * @param empresas
     * @return redBlack de encontros
     */
    public static RedBlackBST_Aed2<Date, Encontro> carregarEncontros(ArrayList<Empresa>empresas){
        RedBlackBST_Aed2<Date, Encontro> encontros= new RedBlackBST_Aed2<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/novosEncontros.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] info= line.split(",");
                    String[] data=info[0].split("/");
                    Date d= new Date(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
                    Coordenada c= new Coordenada(Double.parseDouble(info[1]), Double.parseDouble(info[2]));

                    for (Empresa e: empresas) {
                        if(e.getNome().compareTo(info[4])==0){
                            Encontro encontro= new Encontro(d, c, info[3]);
                            encontro.setEmpresa(e);
                            encontro.setProfissionais(e.getProfissionais());
                            encontros.put(d, encontro);
                        }
                    }
                }
            reader.close();

        } catch (Exception e) {
            System.err.format("Exception occurred trying to read 'novosEncontros.txt'.");
            e.printStackTrace();
            return null;
        }
        return encontros;
    }


    @Override
    public String toString() {
        String a= "Encontro: "+this.nome+", "+this.data+", "+this.localizacao+", Empresa: "+this.empresa.getNome()+" Profissionais: ";
        for (Integer p: profissionais.keys()) {
            a += profissionais.get(p).getNome()+"\n";

        }
        return a;
    }
}