package edu.ufp.inf.projeto;

import edu.princeton.cs.algs4.SeparateChainingHashST;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Profissional implements Serializable {

    private static final AtomicInteger count = new AtomicInteger(0);

    private int id;

    private String nome;

    private Coordenada localizacao;

    private boolean trabalha;

    private Empresa empresa;

    private HistoricoEmpresa historicoEmpresa;

    /**
     * @element-type Encontro
     */
    private RedBlackBST_Aed2<Date, Encontro> encontros = new RedBlackBST_Aed2<>();

    /**
     * @element-type Competencia
     */
    private ArrayList<Competencia> competencias = new ArrayList<>();
    /**
     * @element-type Interesse
     */
    private ArrayList<Interesse> interesses = new ArrayList<>();

    //sempre que criamos um profissional, este está desempregado e nunca trabalhou
    public Profissional(String nome, Coordenada localizacao) {
        this.id = new UniqueID().getId();
        this.nome = nome;
        this.localizacao = localizacao;
        this.trabalha = false;
        this.empresa = null;
        this.historicoEmpresa = new HistoricoEmpresa(this);
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

    public boolean isTrabalha() {
        return trabalha;
    }

    public void setTrabalha(boolean trabalha) {
        this.trabalha = trabalha;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * ao fazer o set empressa no profissional, vamos à empressa e add este profissional e colocamos o trabalha a true pois comecçou a trabalhar e add ao seu historico esta empressa
     * @param empresa
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
        empresa.addProfissional(this);
        this.trabalha = true;
        addHistorico(empresa);
    }

    public HistoricoEmpresa getHistoricoEmpresa() {
        return historicoEmpresa;
    }

    public void setHistoricoEmpresa(HistoricoEmpresa historicoEmpresa) {
        this.historicoEmpresa = historicoEmpresa;
    }

    public RedBlackBST_Aed2<Date, Encontro> getEncontros() {
        return encontros;
    }

    public void setEncontros(RedBlackBST_Aed2<Date, Encontro> encontros) {
        this.encontros = encontros;
    }

    public ArrayList<Competencia> getCompetencias() {
        return competencias;
    }

    public void setCompetencias(ArrayList<Competencia> competencias) {
        this.competencias = competencias;
    }

    public ArrayList<Interesse> getInteresses() {
        return interesses;
    }

    public void setInteresses(ArrayList<Interesse> interesses) {
        this.interesses = interesses;
    }

    /**
     * ao remover a empressa do profissional metemos o trabalha a false pois fica sem emprego e a empressa a null pois acabamos de remover esta
     */
    public void removerEmpresa() {
        this.empresa=null;
        this.trabalha=false;
    }

    /**
     * fazemos um for each para percorrer todos os encontros e listar estes pelo seu nome e respetiva data
     */
    public void listarEncontros() {
        for (Date d : encontros.keys()) {
            System.out.println(encontros.get(d).getNome() + " Data: " + d.toString() + "\n");
        }
    }

    /**
     * percorrer todos os encontros e se a data que passamos por igual à data do encontro, returnamos o encontro que tem a respetiva data
     * @param d
     * @return o encontro com a data que queremos
     */
    public Encontro procurarEncontro(Date d) {
        for (Date data: encontros.keys()) {
            if(data.compareTo(d)==0){
                return encontros.get(data);
            }
        }
        return null;
    }

    public void addEncontro(Encontro e) {
        for (Date date: encontros.keys()) {
            if(date.compareTo(e.getData())==0){
                return;
            }
        }
        encontros.put(e.getData(), e);
    }

    /**
     * remover este profissional dos encontros
     * @param d
     */
    //ir aos encontros e remover este profissional
    public void removerEncontro(Date d) {
        Encontro e= null;
        for (Date date: encontros.keys()) {
            if(date.compareTo(d)==0){
                e= encontros.get(date);
                this.encontros.delete(date);
                e.removeProfissional(this);
            }
        }
    }

    /**
     * percorremos o array de competencias, se alguma tiver o mesmo nome que a competencia c, removemos essa competencia
     * @param c
     * @return se a comparação for verdade removemos, se não returnamos null pois essa competencia não existe no array de comptencias
     */
    //ir à competencia c -> profissionais e remover este profissional e remover interesse nos interesses do profissional
    public Competencia removerCompetencia(Competencia c) {
        for (Competencia competencia : competencias) {
            if(competencia.getNome().compareTo(c.getNome())==0){
                this.competencias.remove(competencia);
            }
        }
        return null;
    }

    /**
     * percorremos o array de competencias e listamos todas as competencias deste profissional
     * @return nome das suas competencias
     */
    public ArrayList<Competencia> listarCompetencias() {
        System.out.println("Competencias do "+this.nome+":\n");
        for (Competencia competencia: competencias) {
            System.out.println(competencia.getNome());
        }
        return null;
    }

    /**
     * percorremos o array de competencias e verificamos se o nome da competencia que estamos a passar existe no array de competencias
     * @param nome
     * @return se existir return essa competencia, se não return null
     */
    public Competencia procurarCompetencia(String nome) {
        for (Competencia competencia : competencias) {
            if(competencia.getNome().compareTo(nome)==0){
                return competencia;
            }
        }
        return null;
    }

    /**
     * percorremos o array de interesses, verificamos se o interesse que estamos a passar ja existe, se ja existe fazemos o return pois nao vamos add se ele ja existe, se não vamos ao array de interesses e addicionamos esse interesse
     * @param i
     */
    public void addInteresse(Interesse i) {
        for (Interesse interesse : interesses) {
            if (interesse.getNome().compareTo(i.getNome()) == 0) {
                return;
            }
        }
            this.interesses.add(i);
    }

    /**
     * percorremos o array de competencias, verificamos se a competencia que estamos a passar ja existe, se ja existe fazemos o return pois nao vamos add se ela ja existe, se não vamos ao array de competencias e adicionamos essa competencia
     * @param c
     */
    public void addCompetencia(Competencia c) {
        for (Competencia competencia : competencias) {
            if (competencia.getNome().compareTo(c.getNome()) == 0) {
                return;
            }
        }
        this.competencias.add(c);
    }

    /**
     * percorremos o array de interesses, se algum tiver o mesmo nome que o interesse i, removemos esse interesse
     * @param i
     * @return  se a comparação for verdade removemos, se não returnamos null pois esse interesse não existe no array de interesses
     */
    //ir ao interesse o -> profissionais e remover este profissional e remover interesse nos interesses do profissional
    public Interesse removerInteresse(Interesse i) {
        for (Interesse interesse : interesses) {
            if(interesse.getNome().compareTo(i.getNome())==0){
                this.getInteresses().remove(interesse);
            }
        }
        return null;
    }

    /**
     * percorremos o arrayList de interesses e listamos todos os interesses deste profissional
     * @return arraylist dos interesses do profissional
     */
    public ArrayList<Interesse> listarInteresses() {
        ArrayList<Interesse> i= new ArrayList<>();
        System.out.println("Interesses do "+this.nome+":\n");
        for (Interesse interesse : interesses) {
            System.out.println(interesse.getNome());
            i.add(interesse);
        }
        return i;
    }

    /**
     * percorremos o array de intereses e verificamos se o nome do interesse que estamos a passar existe no array de interesses
     * @param nome
     * @return se existir return esse interesse, se não return null
     */
    public Interesse procurarInteresse(String nome) {
        for (Interesse interesse : interesses) {
            if(interesse.getNome().compareTo(nome)==0){
                return interesse;
            }
        }
        return null;
    }

    /**
     * vamos ao historico e adicionamos esta empresa
     * @param e
     */
    public void addHistorico(Empresa e) {
        historicoEmpresa.addEmpresa(e);
    }

    /**
     * vamos ao historico e removemos este historico
     * @param e
     */
    public void removeHistorico(Empresa e) {
        historicoEmpresa.removeEmpresa(e);
    }

    /**
     * @return todas as empresas onde este profissional ja trabalhou
     */
    public ArrayList<Empresa> listarHistorico() {
        return historicoEmpresa.getEmpresas();
    }

    /**
     * verificar se no historico existe a empresa que estamos a passar
     * @param e
     * @return se existir return essa empresa, se não return null
     */
    public Empresa procurarHistorico(Empresa e) {
        for (Empresa empresas : historicoEmpresa.getEmpresas()) {
            if(empresas.getId()==e.getId()){
                return empresas;
            }
        }
        return null;
    }

    /**
     * gravamos os profissionais para um ficheiro txt de profissionais com toda a innformação deste(nome, localizaçao, empresa, interesses, competencias)
     * @throws IOException
     */
    public void gravarProfissional() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("data/profissionais.txt", true));
        writer.append(this.getNome() + "; " + this.getLocalizacao() + "; Empregado: "+ this.isTrabalha());
        if(this.getEmpresa()!=null){
            writer.append("; Empresa: "+this.getEmpresa().getNome());
        }
        if(this.getInteresses()!=null){
            writer.append("; Interesses: ");
            for (Interesse i: this.getInteresses()) {
                writer.append(i.getNome());
                writer.append(", ");
            }
        }

        if(this.getCompetencias()!=null){
            writer.append("; Competencias: ");
            for (Competencia i: this.getCompetencias()) {
                writer.append(i.getNome());
                writer.append(", ");
            }
        }

        if(!this.getHistoricoEmpresa().getEmpresas().isEmpty()){
            writer.append("; Histórico de empresas: ");
            for (Empresa i: this.getHistoricoEmpresa().getEmpresas()) {
                writer.append(i.getNome());
                writer.append(", ");
            }
        }

        if(!this.getEncontros().isEmpty()){
            writer.append("; Encontros: ");
            for (Date i: this.encontros.keys()) {
                writer.append(this.getEncontros().get(i).getNome());
                writer.append(", ");
            }
        }

        writer.newLine();
        writer.close();
    }

    /**
     * carregamos todos os profissionais de um ficheiro  txt de novos profissionais
     * @param empresas
     * @param interesses
     * @param competencias
     * @return return um ST com esses profissionais carregados
     */
    public static SeparateChainingHashST<Integer, Profissional> carregarProfissionais(ArrayList<Empresa> empresas, ArrayList<Interesse> interesses, ArrayList<Competencia> competencias) {
        SeparateChainingHashST<Integer, Profissional> profissionals = new SeparateChainingHashST<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/novosProfissionais.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] info = line.split(",");
                Coordenada c = new Coordenada(Double.parseDouble(info[1]), Double.parseDouble(info[2]));
                Profissional p = new Profissional(info[0], c);
                if (empresas != null && info.length >= 4) {
                    for (Empresa ei : empresas) {
                        if (ei.getNome().compareTo(info[3]) == 0) {
                            p.setEmpresa(ei);
                        }
                    }
                }

                if (info.length >= 5) {
                    info[4] = info[4].replace("[", "");
                    info[4] = info[4].replace("]", "");
                    String[] interessesprof = info[4].split(";");
                    for (String inp : interessesprof) {
                        int flag = 0;
                        if (interesses != null) {
                            for (Interesse in : interesses) {
                                if (inp.compareTo(in.getNome()) == 0) {
                                    p.addInteresse(in);
                                    flag = 1;
                                }
                            }
                        }
                        if (flag == 0) {
                            Interesse interesse = new Interesse(inp);
                            p.addInteresse(interesse);
                        }

                    }
                }

                if (info.length >= 6) {
                    info[5] = info[5].replace("[", "");
                    info[5] = info[5].replace("]", "");
                    String[] competenciasprof = info[5].split(";");
                    for (String comp : competenciasprof) {
                        int flag = 0;
                        if (competencias != null) {
                            for (Competencia ci : competencias) {
                                if (comp.compareTo(ci.getNome()) == 0) {
                                    p.addCompetencia(ci);
                                    flag = 1;
                                }
                            }
                        }
                        if (flag == 0) {
                            Competencia competencia = new Competencia(comp);
                            p.addCompetencia(competencia);
                        }

                    }
                }

                profissionals.put(p.getId(), p);
            }

            reader.close();


        } catch (Exception e) {
            System.err.format("Exception occurred trying to read 'novosProfissionais.txt'.");
            e.printStackTrace();
            return null;
        }
        return profissionals;
    }


    @Override
    public String toString() {
        String a = "Profissional: " + this.nome + ", " + "Empresa: ";
        if (this.empresa != null) {
            a += empresa.getNome();
        } else {
            a += "Sem empresa";
        }
        a += "\nInteresses: ";
        for (Interesse i : interesses) {
            a += i.getNome() + " ";

        }
        a += "\nCompetencias: ";
        for (Competencia c : competencias) {
            a += c.getNome() + " ";

        }
        return a;
    }
}
