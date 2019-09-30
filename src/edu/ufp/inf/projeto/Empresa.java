package edu.ufp.inf.projeto;

import edu.princeton.cs.algs4.SeparateChainingHashST;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Empresa implements Serializable {

    private static final AtomicInteger count = new AtomicInteger(0);

    private int id;

    private String nome;

    private Coordenada localizacao;

    /**
     * @element-type Encontro
     */
    private RedBlackBST_Aed2<Date, Encontro> encontros;

    /**
     * @element-type Profissional
     */
    private SeparateChainingHashST<Integer, Profissional> profissionais;


    public Empresa(String nome, Coordenada localizacao) {
        this.id = new UniqueID().getId();
        this.nome = nome;
        this.localizacao = localizacao;
        encontros = new RedBlackBST_Aed2<>();
        profissionais = new SeparateChainingHashST<>();
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

    public RedBlackBST_Aed2<Date, Encontro> getEncontros() {
        return encontros;
    }

    public void setEncontros(RedBlackBST_Aed2<Date, Encontro> encontros) {
        this.encontros = encontros;
    }

    public SeparateChainingHashST<Integer, Profissional> getProfissionais() {
        return profissionais;
    }

    public void setProfissionais(SeparateChainingHashST<Integer, Profissional> profissionais) {
        this.profissionais = profissionais;
    }
    /**
     * vamos á ST de profissionais e adicionamos
     * @param p
     */
    public void addProfissional(Profissional p) {
        for (Integer i: profissionais.keys()) {
            if(i==p.getId()){
                return;
            }
        }
        profissionais.put(p.getId(), p);
    }

    /**
     * remover o profissional na ST de profissionais e ir ao profissional e remover a empresa
     * @param p
     * @return
     */
    public Profissional removerProfissional(Profissional p) {
        profissionais.delete(p.getId());
        p.removerEmpresa();
        return p;
    }

    /**
     * vamos á ST de profissionais e listamos todos os profissionais
     * @return
     */
    public ArrayList<Profissional> listarProfissionais() {
        ArrayList<Profissional> p= new ArrayList<>();
        System.out.println("Profissionais da empresa "+this.nome+":\n");
        for (Integer profissional : profissionais.keys()) {
            System.out.println(profissionais.get(profissional).getNome());
            p.add(profissionais.get(profissional));
        }
        return p;
    }

    /**
     * vamos á ST de profissionais e procuramos se algum nome contem o nome que estamos a passar
     * @param nome
     * @return
     */
    public ArrayList<Profissional> procurarProfissional(String nome) {
        ArrayList<Profissional> p= new ArrayList<>();
        for (Integer profissional : profissionais.keys()) {
            if (profissionais.get(profissional).getNome().contains(nome)){
                p.add(profissionais.get(profissional));
            }
        }
        return p;
    }

    /**
     * add encontro á empresa, percorremos a redBlack de encontros e se a data ja tiver ocupada lançamos uma exception, se a data n tiver ocupada addicionamos o encontro
     * @param e
     * @throws EncontroExistenteException
     */
    public void addEncontro(Encontro e) throws EncontroExistenteException {
        for (Date date: encontros.keys()) {
            if(date.compareTo(e.getData())==0){
                throw new EncontroExistenteException("Data ocupada!!");
            }
        }
        encontros.put(e.getData(), e);
    }

    /**
     * percorremos a redBlack de encontro e se o encontro tiver a mesma data que a data passada, removemos e vamos ´s ST de profissionais e removemos o encontro naquela data do profissional
     * @param d
     * @return
     */
    public Encontro removerEncontro(Date d) {
        for (Date date : encontros.keys()) {
            if (date.compareTo(d) == 0) {
                encontros.delete(d);
                for (Integer i: profissionais.keys()){
                    profissionais.get(i).removerEncontro(date);
                }
            }
        }
        return encontros.get(d);
    }

    /**
     * percorremos a redBlack de encontros e se tiver a data igual à data passada, vamos aos encontros e atribuimos a nova localização
     * @param d
     * @param localizacao
     */
    public void editarLocalizacaoEncontro(Date d, Coordenada localizacao) {
        for (Date data: encontros.keys()) {
            if(data.compareTo(d)==0){
                encontros.get(data).setLocalizacao(localizacao);
            }
        }
    }

    /**
     * vamos à ST de encontros e e algum tiver a data igual à data passada(dantiga) vamos aos encontros e alteramos a data para para nova data(dnova) atraves do setData
     * @param dantiga
     * @param dnova
     */
    public void editarDataEncontro(Date dantiga, Date dnova) {
            for (Date date: encontros.keys()) {
                if(date.compareTo(dantiga)==0){
                    encontros.get(date).setData(dnova);
                }
            }
    }

    /**
     * listamos todos os encontro na redBlack de encontros
     * @return
     */
    public RedBlackBST_Aed2<Date, Encontro> listarEncontros() {
        return getEncontros();
    }

    /**
     * vamos à redBlack de encontros e se algum tiver a mesma data que a data (d) passada, return do encontro com aquela data
     * @param d
     * @return
     */
    public Encontro procurarEncontro(Date d) {
        for (Date data : encontros.keys()) {
            if(data.compareTo(d)==0){
                System.out.println(encontros.get(d).getNome());
                return encontros.get(data);
            }
        }
        return null;
    }

    /**
     * vamos à redBlack de encontros e add o profissional no encontro com a data passada
     * @param p
     * @param d
     */
    public void addProfissionaisEncontro(Profissional p, Date d) {
        //chamar função que temos de fazer que no encontro add um profissional
        for (Date data: encontros.keys()) {
            if(data.compareTo(d)==0){
                encontros.get(d).addProfissional(p);
            }
        }
    }

    /**
     * vamos à redBlack de encontros e remove o profissional no encontro com a data passada
     * @param p
     * @param d
     */
    public void removeProfissionalEncontro(Profissional p, Date d) {
        //chamar função que temos de fazer que no encontro add um profissional
        for (Date data: encontros.keys()) {
            if(data.compareTo(d)==0){
                encontros.get(d).removeProfissional(p);
            }
        }
    }

    /**
     * procurar o encontro entre duas datas, vamos aà redBlack de encontros e atraves das datas vemos se esta for depois da data de inicio(dinicio) e antes da data de fim(dfim
     * @param dinicio
     * @param dfim
     * @return imprimimos essa empressa com o encontro entre essas duas data, se não return null
     */
    public RedBlackBST_Aed2<Date, Encontro> procurarEncontroDatas(Date dinicio, Date dfim) {
        RedBlackBST_Aed2<Date, Encontro> e= new RedBlackBST_Aed2<>();
        for (Date data: encontros.keys()) {
            if(data.afterDate(dinicio) && data.beforeDate(dfim)){
                e.put(data, encontros.get(data));
            }
        }
        return e;
    }

    /**
     * todos os encontros com mais de n participantes, chamamos a função contarParticipantes e se esta for maios que o n que passamos, adicionamos esse econtro a um arryList de encontros
     * @param n
     * @return arrayList de encontros (encontros 1)
     */
    public RedBlackBST_Aed2<Date, Encontro> encontroComMaisParticipantes(int n) {
        RedBlackBST_Aed2<Date, Encontro> encontros1= new RedBlackBST_Aed2<>();
        for (Date date: encontros.keys()) {
            if (encontros.get(date).contarParticipantes()>n){
                encontros1.put(date, encontros.get(date));
            }
        }
        return encontros1;
    }

    /**
     * gravamos para um ficheiro txt de empresas com toda a informação (nome, localizaçao, profissionais e encontros)
     * @throws IOException
     */
    public void gravarEmpresa() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("data/empresas.txt", true));
        writer.append(this.getNome() + "; " + this.getLocalizacao());
        if(!this.getProfissionais().isEmpty()){
            writer.append("; Empregados: ");
            for (Integer i: this.getProfissionais().keys()) {
                writer.append(this.getProfissionais().get(i).getNome());
                writer.append(", ");
            }
        }

        writer.newLine();
        writer.close();
    }

    /**
     * carregamos de um ficheiro txt de novas empresas todas e guardamos num arrayList de empresas
     * @return srrayList de empresas
     */
    public static ArrayList<Empresa> carregarEmpresas() {
        ArrayList<Empresa> empresas = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/novasEmpresas.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] info = line.split(",");
                for (int i = 0; i < info.length; i += 3) {
                    Coordenada c = new Coordenada(Double.parseDouble(info[i + 1]), Double.parseDouble(info[i + 2]));
                    empresas.add(new Empresa(info[i], c));
                }
            }
            reader.close();


        } catch (Exception e) {
            System.err.format("Exception occurred trying to read 'novasEmpresas.txt'.");
            e.printStackTrace();
            return null;
        }
        return empresas;
    }

    @Override
    public String toString() {
        String a = "Empresa: " + this.nome + ", " + this.localizacao;

        a += "Empregaddos:";
        for (Integer profissional : profissionais.keys()) {
            a += profissionais.get(profissional).getNome() + "\n";

        }
        return a;
    }
}