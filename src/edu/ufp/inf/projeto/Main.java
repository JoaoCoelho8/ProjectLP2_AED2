package edu.ufp.inf.projeto;

import edu.princeton.cs.algs4.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application implements Serializable {

    public static String path_digraph= "./data/digraph.txt";
    public static String path_digraphST= "./data/digraphST.txt";
    public static String path_digraphBin= "./data/digraphBin.bin";
    public static String path_digraphSTBin= "./data/digraphSTBin.bin";

    public static String path_weightedgraph= "./data/weightedgraph.txt";
    public static String path_weightedgraphST= "./data/weightedgraphST.txt";
    public static String path_weightedgraphBin= "./data/weightedgraphBin.bin";
    public static String path_weightedgraphSTBin= "./data/weightedgraphSTBin.bin";

    public static String path_weighteddigraph= "./data/weighteddigraph.txt";
    public static String path_weighteddigraphST= "./data/weighteddigraphST.txt";
    public static String path_weighteddigraphBin= "./data/weighteddigraphBin.bin";
    public static String path_weighteddigraphSTBin= "./data/weighteddigraphSTBin.bin";

    public static String delimiter= ";";
    public static ArrayList<Empresa> empresaST= Empresa.carregarEmpresas();
    public static ArrayList<PontoPassagem> pontosPassagemST= new ArrayList<>();
    public static ArrayList<Interesse> interesseST= Interesse.carregarInteresses();
    public static ArrayList<Competencia> competenciaST= Competencia.carregarCompetencias();
    public static SeparateChainingHashST<Integer, Profissional> profissionalST= Profissional.carregarProfissionais(empresaST, interesseST, competenciaST);
    public static RedBlackBST_Aed2<Date, Encontro> encontroST= Encontro.carregarEncontros(empresaST);
    public static Digraph digraph= null;
    public static ST<Integer, Integer> prof_empST= new ST<>();

    public static EdgeWeightedGraph edgeWeightedGraph= null;
    public static ST<Integer, Integer> prof_profST= new ST<>();

    public static EdgeWeightedDigraph_Projeto edgeWeightedDigraph= null;
    public static ST<Integer, Integer> caminhosST= new ST<>();
    public static int PESO_INTERESSE= 2;
    public static int PESO_COMPETENCIA= 1;
    public static int PESO_HISTEMPRESA= 10;
    public static int PESO_EMPRESACOMUM= 5;
    public static int PESO_ENCONTRO= 5;
    public static int GRAPHProf_Prof_ID = 0;
    public static int GRAPHProf_Emp_ID = 0;
    public static int GRAPHCaminhos_ID = 0;

    public static ArrayList<Empresa> empresas= new ArrayList<>();
    public static ArrayList<Competencia> competencias=  new ArrayList<>();
    public static SeparateChainingHashST<Integer, Profissional> profissionals= new SeparateChainingHashST<>();
    public static RedBlackBST_Aed2<Date, Encontro> encontros= new RedBlackBST_Aed2<>();
    public static void main(String[] args) {

        /**
         * criar coordenadas
         */
        Coordenada c= new Coordenada(1, 2);
        Coordenada c2= new Coordenada(3, 4);
        Coordenada c3= new Coordenada(14, 37);
        Coordenada c4= new Coordenada(40, 26);
        Coordenada c5= new Coordenada(35, 51);
        Coordenada c6= new Coordenada(77, 4);

        /**
         * criar profissionais
         */
        Profissional p1= new Profissional("João", c);
        addProfissionalToDB(p1);
        Profissional p2= new Profissional("Pedro", c2);
        addProfissionalToDB(p2);

        /**
         * criar empresas
         */
        Empresa e= new Empresa("Google", c3);
        addEmpresaToDB(e);
        Empresa e1= new Empresa("Samsung", c4);
        addEmpresaToDB(e1);

        /**
         * add empressas aos profissionais
         */
        p1.setEmpresa(e);
        p2.setEmpresa(e);

        /**
         * criar datas
         */
        Date date= new Date(24,3,2019);
        Date date2= new Date(25,3,2019);

        /**
         * criar encontros
         */
        Encontro encontro= new Encontro(date, c5, "Festa Google");
        try {
            encontro.setEmpresa(e);
            addEncontroToDB(encontro);
        } catch (EncontroExistenteException e2) {
            e2.printStackTrace();
        }

        /**
         * criar encontros
         */
        Encontro encontro2= new Encontro(date2, c6, "Lançamento de telemóvel");
        try {
            encontro2.setEmpresa(e1);
            addEncontroToDB(encontro2);
        } catch (EncontroExistenteException e2) {
            e2.printStackTrace();
        }

        /**
         * criar interesses
         */
        Interesse interesse= new Interesse("F1");
        addInteresseToDB(interesse);
        Interesse interesse2= new Interesse("Programação");
        addInteresseToDB(interesse2);

        /**
         * add interesses aos profissionais
         */
        p1.addInteresse(interesse);
        p1.addInteresse(interesse2);
        p2.addInteresse(interesse);

        /**
         * criar competencias
         */
        Competencia comp1= new Competencia("Falar Inglês");
        addCompetenciaToDB(comp1);

        /**
         * add competencias aos profissionais
         */
        p1.addCompetencia(comp1);
        p2.addCompetencia(comp1);

//        System.out.println("\nDesempregados:");
//        SeparateChainingHashST<Integer, Profissional> profi= getDesempregados(profissionalST);
//        for (Integer pi:profi.keys()) {
//            System.out.println(profi.get(pi).getNome());
//        }
//
//        System.out.println("\nProfissionais com a competência "+ ler.getNome() +":");
//        SeparateChainingHashST<Integer, Profissional> profi2= getProfissionaisCompetencia(profissionalST, ler);
//        for (Integer pi:profi2.keys()) {
//            System.out.println(profi2.get(pi).getNome());
//        }
//
//        /**
//         * gravar dados
//         */
//        try {
//            p1.gravarProfissional();
//            p2.gravarProfissional();
//            e.gravarEmpresa();
//            e1.gravarEmpresa();
//            encontro.gravarEncontro();
//            encontro2.gravarEncontro();
//            interesse.gravarInteresse();
//            interesse2.gravarInteresse();
//            ler.gravarCompetencia();
//        } catch (IOException e2) {
//            e2.printStackTrace();
//        }

        /**
         * imprimir profissionais e toda a sua imformação
         */
        System.out.println("\nProfissionais"+":");
        for (Integer i: profissionalST.keys()) {
            profissionals.put(i, profissionalST.get(i));
            System.out.println(profissionalST.get(i).getId()+" "+profissionalST.get(i).getNome());
            if(!profissionalST.get(i).getEncontros().isEmpty()){
                for (Date date1: profissionalST.get(i).getEncontros().keys()) {
                    System.out.println("   "+profissionalST.get(i).getEncontros().get(date1).getNome());
                }
            }
        }

        /**
         * imprimir empresas e toda a sua imformação
         */
        System.out.println("\nEmpresas"+":");
        for (Empresa empresa: empresaST) {
            empresas.add(empresa);
            System.out.println(empresa.getId()+" "+empresa.getNome());
            for (Integer premp :empresa.getProfissionais().keys()) {
                System.out.println("   "+empresa.getProfissionais().get(premp).getNome());
            }
        }

        System.out.println("\nEncontros"+":");
        for (Date encontro1: encontroST.keys()) {
            encontros.put(encontro1, encontroST.get(encontro1));
            System.out.println(encontroST.get(encontro1).getId()+" "+encontroST.get(encontro1).getNome());
            for (Integer profissional: encontroST.get(encontro1).getProfissionais().keys()){
                System.out.println("   "+encontroST.get(encontro1).getProfissionais().get(profissional).getNome());
            }
        }

        /**
         * imprimir interesses e toda a sua imformação
         */
        System.out.println("\nInteresses"+":");
        for (Interesse interesse1: interesseST) {
            System.out.println(interesse1.getId()+" "+interesse1.getNome());
        }

        /**
         * imprimir competencias e toda a sua imformação
         */
        System.out.println("\nCompetencias"+":");
        for (Competencia competencia: competenciaST) {
            competencias.add(competencia);
            System.out.println(competencia.getId()+" "+competencia.getNome());
        }

        /* Grafos */

        //em vez de arraylist->usar symbol table (garantir q os ids são todos diferentes)
        //Digraph - Pessoas seguir empresas

//        for (Empresa emp: empresaST) {
//            prof_empST.put(GRAPHProf_Emp_ID, emp.getId());
//            GRAPHProf_Emp_ID++;
//        }
//        for (Integer prof: profissionalST.keys()) {
//            prof_empST.put(GRAPHProf_Emp_ID, profissionalST.get(prof).getId());
//            GRAPHProf_Emp_ID++;
//        }

//        try {
//            readDigraphST();
//        } catch (IOException e2) {
//            e2.printStackTrace();
//        }
        readDigraphSTBin();
        //saveDigraphSTBin();
//        try {
//            saveDigraphST();
//        } catch (IOException e2) {
//            e2.printStackTrace();
//        }

        System.out.println("\nProf_Emp ST (ids para digraph):");
        for (Integer i: prof_empST.keys()) {
            System.out.println("Nó "+i+"-"+prof_empST.get(i));
        }

       // /*Digraph */digraph= new Digraph(prof_empST.size());
//        digraph.addEdge(0,4);
//        digraph.addEdge(2,5);
//        digraph.addEdge(2,4);
//        digraph.addEdge(3,4);
//        digraph.addEdge(4,2);
//        digraph.addEdge(4,0);
//        digraph.addEdge(5,2);
//        digraph.addEdge(5,0);
//        digraph.addEdge(5,3);


//        try {
//            readDigraph();
//        } catch (IOException e2) {
//            e2.printStackTrace();
//        }

        readDigraphBin();
        System.out.println(digraph);
        Bipartite_Projeto bipartite= new Bipartite_Projeto(digraph);
        System.out.println("Bipartido: "+bipartite.isBipartite());
//        saveDigraphBin();
//        try {
//            saveDigraph();
//        } catch (IOException e2) {
//            e2.printStackTrace();
//        }



        //Grafo pesado - relações entre pessoas
//        for (Integer prof: profissionalST.keys()) {
//            prof_profST.put(GRAPHProf_Prof_ID, profissionalST.get(prof).getId());
//            GRAPHProf_Prof_ID++;
//        }

//        }
//        try {
//            readWGraphST();
//        } catch (IOException e2) {
//            e2.printStackTrace();
//        }
        readWGraphSTBin();
//        try {
//            saveWGraphST();
//        } catch (IOException e2) {
//            e2.printStackTrace();
//        }
//
//
//        saveWGraphSTBin();

        System.out.println("\nProf_Prof ST (ids para graph pesado):");
        for (Integer i: prof_profST.keys()) {
            System.out.println("Nó " + i + "-" + prof_profST.get(i));
        }
//        edgeWeightedGraph= new EdgeWeightedGraph(prof_profST.size());
//
//        for (Integer pr1: profissionalST.keys()) {
//            for (Integer pr2: profissionalST.keys()) {
//                if(pr1!=pr2 && pr2>pr1) {
//                    int we = calculaPeso(profissionalST.get(pr1), profissionalST.get(pr2), digraph, prof_empST);
//                    System.out.println(profissionalST.get(pr1).getNome()+"-"+profissionalST.get(pr2).getNome()+" "+we);
//                    if (we > 0) {
//                        int v = 0, w = 0;
//                        for (int i = 0; i < prof_profST.size(); i++) {
//                            if (prof_profST.get(i) == profissionalST.get(pr1).getId()) {
//                                v = i;
//                            }
//                            if (prof_profST.get(i) == profissionalST.get(pr2).getId()) {
//                                w = i;
//                            }
//                        }
//                        if (!(v == 0 && w == 0)) {
//                            Edge edge = new Edge(v, w, we);
//                            edgeWeightedGraph.addEdge(edge);
//                        }
//                    }
//                }
//            }
//        }

//        try {
//            readWGraph();
//        } catch (IOException e2) {
//            e2.printStackTrace();
//        }
        readWGraphBin();
//        try {
//            saveWGraph();
//        } catch (IOException e2) {
//            e2.printStackTrace();
//        }
//        saveWGraphBin();
        for (Edge i: edgeWeightedGraph.edges()) {
            System.out.println(i);
        }

        System.out.println("Conexo: "+isConnected(edgeWeightedGraph, 0));

        /**
         * criar pontos de passagem
         */
        PontoPassagem pp1= new PontoPassagem("PP1", new Coordenada(20,50));
        addPPToDB(pp1);
        PontoPassagem pp2= new PontoPassagem("PP2", new Coordenada(50,66));
        addPPToDB(pp2);
        PontoPassagem pp3= new PontoPassagem("PP3", new Coordenada(10,80));
        addPPToDB(pp3);

        System.out.println("\nPontos de Passagem"+":");
        for (PontoPassagem pontoPassagem: pontosPassagemST) {
            System.out.println(pontoPassagem.getId()+" "+pontoPassagem.getNome());
        }

        //Digrafo pesado - caminhos
//        for (Empresa empresa: empresaST) {
//            caminhosST.put(GRAPHCaminhos_ID, empresa.getId());
//            GRAPHCaminhos_ID++;
//        }
//        for (PontoPassagem pontoPassagem: pontosPassagemST) {
//            caminhosST.put(GRAPHCaminhos_ID, pontoPassagem.getId());
//            GRAPHCaminhos_ID++;
//        }
//        for (Date date1: encontroST.keys()) {
//            caminhosST.put(GRAPHCaminhos_ID, encontroST.get(date1).getId());
//            GRAPHCaminhos_ID++;
//        }
//        System.out.println("\nCaminhos ST (ids para  pesado):");
//        for (Integer i: caminhosST.keys()) {
//            System.out.println("Nó "+i+"-"+caminhosST.get(i));
//        }

//        try {
//            readWDigraphST();
//        } catch (IOException e2) {
//            e2.printStackTrace();
//        }
        readWDigraphSTBin();
//        try {
//            saveWDigraphST();
//        } catch (IOException e2) {
//            e2.printStackTrace();
//        }
//        saveWDigraphSTBin();

        //edgeWeightedDigraph= new EdgeWeightedDigraph_Projeto(caminhosST.size());

//        for (DirectedEdge_Projeto edge_projeto:edgeWeightedDigraph.edges()) {
//            if(edge_projeto.to()==1 && edge_projeto.from()==2){
//                edgeWeightedDigraph.removeEdge(edge_projeto);
//                edgeWeightedDigraph.addEdge(new DirectedEdge_Projeto(2, 1, peso));
//            }
//        }
//        edgeWeightedDigraph.addEdge(new DirectedEdge_Projeto(0,4, calculaDistancia(0, 4, caminhosST), 120));
//        edgeWeightedDigraph.addEdge(new DirectedEdge_Projeto(4,1, calculaDistancia(4, 1, caminhosST), 50));
//        edgeWeightedDigraph.addEdge(new DirectedEdge_Projeto(1,4, calculaDistancia(1, 4, caminhosST), 50));
//        edgeWeightedDigraph.addEdge(new DirectedEdge_Projeto(4,3, calculaDistancia(4, 3, caminhosST), 80));
//        edgeWeightedDigraph.addEdge(new DirectedEdge_Projeto(3,5, calculaDistancia(3, 5, caminhosST), 120));
//        edgeWeightedDigraph.addEdge(new DirectedEdge_Projeto(5,0, calculaDistancia(5, 0, caminhosST), 90));
//        edgeWeightedDigraph.addEdge(new DirectedEdge_Projeto(4,2, calculaDistancia(4, 2, caminhosST), 60));
//        edgeWeightedDigraph.addEdge(new DirectedEdge_Projeto(2,4, calculaDistancia(2, 4, caminhosST), 60));
//        edgeWeightedDigraph.addEdge(new DirectedEdge_Projeto(2,6, calculaDistancia(2, 6, caminhosST), 120));
//        edgeWeightedDigraph.addEdge(new DirectedEdge_Projeto(3,6, calculaDistancia(3, 6, caminhosST), 50));
//        edgeWeightedDigraph.addEdge(new DirectedEdge_Projeto(6,3, calculaDistancia(6, 3, caminhosST), 50));
//        edgeWeightedDigraph.addEdge(new DirectedEdge_Projeto(6,8, calculaDistancia(6, 8, caminhosST), 120));
//        edgeWeightedDigraph.addEdge(new DirectedEdge_Projeto(8,6, calculaDistancia(8, 6, caminhosST), 120));
//        edgeWeightedDigraph.addEdge(new DirectedEdge_Projeto(6,7, calculaDistancia(6, 7, caminhosST), 50));
//        edgeWeightedDigraph.addEdge(new DirectedEdge_Projeto(7,6, calculaDistancia(7, 6, caminhosST), 50));

//        try {
//            readWDigraph();
//        } catch (IOException e2) {
//            e2.printStackTrace();
//        }
        readWDigraphBin();
//        try {
//            saveWDigraph();
//        } catch (IOException e2) {
//            e2.printStackTrace();
//        }
//        saveWDigraphBin();
        System.out.println(edgeWeightedDigraph);

        int origin=4;
        int destiny=3;

        //type 1- Distancia / Type 2- Salto / Type 3- Tempo

        DijkstraSP_Projeto dijkstraSP= new DijkstraSP_Projeto(edgeWeightedDigraph, origin, 1);
        if(dijkstraSP.hasPathTo(destiny)) {
            System.out.println(dijkstraSP.distTo(destiny));
            //System.out.println(dijkstraSP.pathTo(destiny));
            dijkstraSP.pathTo(destiny);
        }

        DijkstraSP_Projeto dijkstraSP2= new DijkstraSP_Projeto(edgeWeightedDigraph, origin, 2);
        if(dijkstraSP2.hasPathTo(destiny)) {
            System.out.println(dijkstraSP2.distTo(destiny));
            //System.out.println(dijkstraSP2.pathTo(destiny));
            dijkstraSP2.pathTo(destiny);
        }

        DijkstraSP_Projeto dijkstraSP3= new DijkstraSP_Projeto(edgeWeightedDigraph, origin, 3);
        if(dijkstraSP3.hasPathTo(destiny)) {
            System.out.println(dijkstraSP3.distTo(destiny));
            //System.out.println(dijkstraSP3.pathTo(destiny));
            dijkstraSP3.pathTo(destiny);
        }

        //R10 d
//        ArrayList<Integer> ficar= new ArrayList<>();
//        ficar.add(4);
//        ficar.add(3);
//        ficar.add(2);
//        ficar.add(6);
//        for (DirectedEdge_Projeto d: edgeWeightedDigraph.edges()) {
//            if(!(ficar.contains(d.from())) || !(ficar.contains(d.to()))){
//                edgeWeightedDigraph.removeEdge(d);
//            }
//        }
//        System.out.println(edgeWeightedDigraph);


        //type 1- Distancia / Type 2- Salto / Type 3- Tempo

        dijkstraSP= new DijkstraSP_Projeto(edgeWeightedDigraph, origin, 1);
        if(dijkstraSP.hasPathTo(destiny)) {
            System.out.println(dijkstraSP.distTo(destiny));
            //System.out.println(dijkstraSP.pathTo(destiny));
            dijkstraSP.pathTo(destiny);
        }

        dijkstraSP2= new DijkstraSP_Projeto(edgeWeightedDigraph, origin, 2);
        if(dijkstraSP2.hasPathTo(destiny)) {
            System.out.println(dijkstraSP2.distTo(destiny));
            //System.out.println(dijkstraSP2.pathTo(destiny));
            dijkstraSP2.pathTo(destiny);
        }

        dijkstraSP3= new DijkstraSP_Projeto(edgeWeightedDigraph, origin, 3);
        if(dijkstraSP3.hasPathTo(destiny)) {
            System.out.println(dijkstraSP3.distTo(destiny));
            //System.out.println(dijkstraSP3.pathTo(destiny));
            dijkstraSP3.pathTo(destiny);
        }


        listProfsFollowEmp(digraph, e1, prof_empST);
        listProfsEnc(edgeWeightedDigraph, encontro, caminhosST);
        listProfsCompWithoutEmp(edgeWeightedGraph, competenciaST, prof_profST);
        sugerirProfsComps(edgeWeightedGraph, competenciaST, prof_profST);
        sugerirProfEmp(edgeWeightedGraph, digraph, e, p2, prof_profST, prof_empST);
        listProfsCompWithoutEmpPlus(edgeWeightedGraph, competenciaST, prof_profST);
        listEncontrosEntreDatas(edgeWeightedDigraph, caminhosST, new Date(1,1,1990), new Date(30,5,2019));


        launch(args);

    }

    /**
     *
     * @param path
     * @return
     */
    public static PrintWriter openPrintWriter(String path) {
        try {
            FileWriter fw= new FileWriter(path);
            PrintWriter pw= new PrintWriter(fw);
            return pw;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * R11 a) Listar as pessoas (profissionais) que seguem uma determinada empresa.
     * @param digraph
     * @param empresa
     * @param st
     * @return profissionais que seguem essa empresa que recebemos
     */
    public static ArrayList<Profissional> listProfsFollowEmp(Digraph digraph, Empresa empresa, ST<Integer, Integer> st){
        ArrayList<Profissional> profissionais= new ArrayList<>();
//        for (Integer emp: st.keys()) {
//            if (st.get(emp)==empresa.getId()){
//                for (Integer prof: digraph.adj(emp)) {
//                    profissionais.add(profissionalST.get(st.get(prof)));
//                }
//            }
//        }
        /*
        for (int v = 0; v < digraph.V(); v++) {
            if (st.get(v)==empresa.getId()){
                for (Integer prof: digraph.adj(v)) {
                    profissionais.add(profissionalST.get(st.get(prof)));
                }
            }
        }*/

        for (int v = 0; v < digraph.V(); v++) {
            for (Integer i: digraph.adj(v)) {
                if (st.get(i)==empresa.getId()) {
                    profissionais.add(profissionalST.get(st.get(v)));
                }
            }
        }

        String path= "./data/ProfsFollowEmp.txt";
        PrintWriter pw= openPrintWriter(path);
        if(pw!=null){
            pw.write("Empresa: "+empresa.getNome()+" - ");
            pw.write("Seguidores: ");
            pw.println();
            for(Profissional d: profissionais){
                pw.write(d.getId()+"-"+d.getNome());
                pw.println();
            }
            pw.close();
        }

        return profissionais;
    }

    /**
     * R11 b) Listar as pessoas (profissionais) que participaram um determinado encontro.
     * @param digraph
     * @param encontro
     * @param st
     * @return profissionais que participam num encontro que recebemos
     */
    public static ArrayList<Profissional> listProfsEnc(EdgeWeightedDigraph_Projeto digraph, Encontro encontro, ST<Integer, Integer> st){
        ArrayList<Profissional> profissionais= new ArrayList<>();
        for (int v = 0; v < digraph.V(); v++) {
            if (st.get(v)==encontro.getId()){
                for (Integer i:encontro.getProfissionais().keys()) {
                    profissionais.add(encontro.getProfissionais().get(i));
                }
            }
        }
        String path= "./data/ProfsEnc.txt";
        PrintWriter pw= openPrintWriter(path);
        if(pw!=null){
            pw.write("Encontro: "+encontro.getNome()+" - ");
            pw.write("Participantes: ");
            pw.println();
            for(Profissional d: profissionais){
                pw.write(d.getId()+"-"+d.getNome());
                pw.println();
            }
            pw.close();
        }
        return profissionais;
    }

    /**
     * R11 d) Procurar e listar profissionais com determinadas competências e que não tenham
     * empresa associada.
     * @param graph
     * @param competencias
     * @param st
     * @return profissionais
     */
    public static ArrayList<Profissional> listProfsCompWithoutEmp(EdgeWeightedGraph graph, ArrayList<Competencia> competencias, ST<Integer, Integer> st){
        ArrayList<Profissional> profissionais= new ArrayList<>();
        int flag=0;
        for (int v = 0; v < graph.V(); v++) {
            Profissional profaux=profissionalST.get(st.get(v));
            if(profaux.getEmpresa()==null){
                for (Competencia competencia: competencias)  {
                    for (Competencia compprof: profaux.getCompetencias()){
                        if(compprof.getId()==competencia.getId()){
                            flag=1;
                        }
                    }
                    if(flag==0){
                        break;
                    }
                }
                profissionais.add(profaux);
            }
        }
        String path= "./data/ProfsCompWithoutEmp.txt";
        PrintWriter pw= openPrintWriter(path);
        if(pw!=null){
            pw.write("Profissionais com as compentecias: ");
            for (Competencia competencia: competencias)  {
                pw.write(competencia.getNome()+", ");
            }
            pw.write(": ");
            pw.println();
            for(Profissional d: profissionais){
                pw.write(d.getId()+"-"+d.getNome());
                pw.println();
            }
            pw.close();
        }
        return profissionais;
    }

    /**
     * R11 e) Sugerir profissionais a empresas com base em competências. O resultado deve
     * ser ordenado por experiência profissional.
     * @param graph
     * @param competencias
     * @param st
     * @return profissionais
     */
    public static RedBlackBST_Aed2<Integer, Profissional> sugerirProfsComps(EdgeWeightedGraph graph, ArrayList<Competencia> competencias, ST<Integer, Integer> st){
        RedBlackBST_Aed2<Integer, Profissional> profissionais= new RedBlackBST_Aed2<>();
        int flag=0;
        for (int v = 0; v < graph.V(); v++) {
            Profissional profaux=profissionalST.get(st.get(v));
            for (Competencia competencia: competencias)  {
                for (Competencia compprof: profaux.getCompetencias()){
                    if(compprof.getId()==competencia.getId()){
                        flag=1;
                    }
                }
                if(flag==0){
                    break;
                }
            }
            profissionais.put(profaux.getHistoricoEmpresa().getEmpresas().size(), profaux);
        }
        String path= "./data/sugerirProfsComps.txt";
        PrintWriter pw= openPrintWriter(path);
        if(pw!=null){
            pw.write("Lista de sugestões a empresas de profs com as seguintes competencias:");
            pw.println();
            for (Competencia competencia: competencias)  {
                pw.write(competencia.getNome()+", ");
            }
            pw.println();
            pw.write("Sugestões: ");
            pw.println();
            for(Integer d: profissionais.keys()){
                pw.write(profissionais.get(d).getId()+"-"+profissionais.get(d).getNome());
                pw.println();
            }
            pw.close();
        }

        return profissionais;
    }

    /**
     * R11 f) Sugerir através do círculo de amizades de um determinado profissional quem
     * pode facilitar a relação com uma determinada empresa
     * @param graph
     * @param digraph
     * @param empresa
     * @param profissional
     * @param stgraph
     * @param stdigraph
     * @return
     */
    public static Profissional sugerirProfEmp(EdgeWeightedGraph graph, Digraph digraph, Empresa empresa, Profissional profissional, ST<Integer, Integer> stgraph, ST<Integer, Integer> stdigraph){
        int profid_graph = 0;
        int profs= 0;
        int empid = 0;
        int distancia=0;
        int v=0;
        Profissional p= null;
        for (Integer i : stgraph) {
            if(stgraph.get(i)==profissional.getId()) {
                profid_graph= i;
            }
        }
        for (Integer i : stdigraph) {
            if(stdigraph.get(i)==empresa.getId()) {
                empid= i;
            }
        }

        for (Edge e: graph.adj(profid_graph)) {
            profs= e.other(profid_graph);
            int id_adj= stgraph.get(profs);
            for (Integer integer: stdigraph) {
                if(stdigraph.get(integer)==id_adj){
                    p = profissionalST.get(id_adj);
                    BreadthFirstDirectedPaths bfd= new BreadthFirstDirectedPaths(digraph, integer);
                    if(bfd.hasPathTo(empid) && distancia>bfd.distTo(empid)){
                        distancia= bfd.distTo(empid);
                        System.out.println(bfd.distTo(empid));
                        System.out.println(bfd.pathTo(empid));
                        String path= "./data/sugerirProfEmp.txt";
                        PrintWriter pw= openPrintWriter(path);
                        if(pw!=null){
                            pw.write("Profissional que pode facilitar a "+profissional.getNome()+" a relação com a empresa "+empresa.getNome()+": ");
                            pw.write(p.getId()+" - "+p.getNome());
                            pw.println();
                            pw.write("Distancia até empresa: "+bfd.distTo(empid));
                            pw.println();
                            pw.write("Caminho até empresa: ");
                            for (Integer i:bfd.pathTo(empid)) {
                                Integer in= stdigraph.get(i);
                                for (Integer inti: profissionalST.keys()) {
                                    if(profissionalST.get(inti).getId()==in){
                                        pw.write(profissionalST.get(inti).getNome()+", ");
                                    }
                                }
                                for (Empresa empi: empresaST) {
                                    if (empi.getId() == in) {
                                        pw.write(empi.getNome() + ", ");
                                    }
                                }
                            }
                            pw.println();
                            pw.close();
                        }
                    }
                }
            }
        }

        return profissionalST.get(stdigraph.get(v));
    }

    /**
     * R11 g) Recebemos um EdgeWeightedGraph uma st e um arraylist de competencias e vamos pesquisar profissionais que tem mais de 2 anos de empresas e com umas determinadas comoetencias
     * @param graph
     * @param competencias
     * @param st
     * @return profissionais que tem mais de 2 anos de empresas e com umas determinadas comoetencias
     */
    public static ArrayList<Profissional> listProfsCompWithoutEmpPlus(EdgeWeightedGraph graph, ArrayList<Competencia> competencias, ST<Integer, Integer> st){
        ArrayList<Profissional> profissionais= new ArrayList<>();
        int flag=0;
        for (int v = 0; v < graph.V(); v++) {
            Profissional profaux=profissionalST.get(st.get(v));
            if(profaux.getEmpresa()==null || profaux.getHistoricoEmpresa().getEmpresas().size()>1) { //experiencia de mais de 2 empresas
                for (Competencia competencia: competencias)  {
                    for (Competencia compprof: profaux.getCompetencias()){
                        if(compprof.getId()==competencia.getId()){
                            flag=1;
                        }
                    }
                    if(flag==0){
                        break;
                    }
                }
                profissionais.add(profaux);
            }
        }

        String path= "./data/ProfsCompWithoutEmpPlus.txt";
        PrintWriter pw= openPrintWriter(path);
        if(pw!=null){
            pw.write("Profissionais com as compentecias ");
            for (Competencia competencia: competencias)  {
                pw.write(competencia.getNome()+", ");
            }
            pw.write("com mais de duas empresas de experiência:");
            pw.println();
            for(Profissional d: profissionais){
                pw.write(d.getId()+"-"+d.getNome());
                pw.println();
            }
            pw.close();
        }

        return profissionais;
    }

    /**
     * R11 h) Procurar num determinado intervalo de tempo os encontros realizados ou agendados entre duas datas
     * @param caminhos
     * @param stcaminhos
     * @param inicio
     * @param fim
     * @return encontros realizados entre as duas datas
     */
    public static RedBlackBST_Aed2<Date, Encontro> listEncontrosEntreDatas(EdgeWeightedDigraph_Projeto caminhos, ST<Integer, Integer> stcaminhos, Date inicio, Date fim){
        RedBlackBST_Aed2<Date, Encontro> encontros= new RedBlackBST_Aed2<>();
        Encontro encontroaux=null;
        for (int v = 0; v < caminhos.V(); v++) {
            for (Date d: encontroST.keys()) {
                if(encontroST.get(d).getId()==stcaminhos.get(v)) {
                    encontroaux=encontroST.get(d);
                    if(d.compareTo(inicio)>=0 && d.compareTo(fim)<=0){
                        encontros.put(d, encontroaux);
                    }
                }
            }

        }

        String path= "./data/EncontrosEntreDatas.txt";
        PrintWriter pw= openPrintWriter(path);
        if(pw!=null){
            pw.write("Encontro entre "+inicio.toString()+" e "+fim.toString()+" :");
            pw.println();
            for (Date date: encontros.keys())  {
                pw.write(encontros.get(date).getNome());
                pw.println();
            }
            pw.close();
        }

        return encontros;
    }

    /**
     * vereficar se um grafo é connected
     * @param edgeWeightedGraph
     * @param no
     * @return true se for ou false se não for
     */
    public static boolean isConnected(EdgeWeightedGraph edgeWeightedGraph, int no){

        BreadthFirstPaths_Projeto bfs= new BreadthFirstPaths_Projeto(edgeWeightedGraph, no);
        for (int v = 0; v < edgeWeightedGraph.V(); v++) {
            if (bfs.hasPathTo(v)) {
//                StdOut.printf("%d to %d (%d):  ", no, v, bfs.distTo(v));
//                for (int x : bfs.pathTo(v)) {
//                    if (x == no) StdOut.print(x);
//                    else        StdOut.print("-" + x);
//                }
//                StdOut.println();
            }

            else {
//                StdOut.printf("%d to %d (-):  not connected\n", no, v);
                return false;
            }

        }
        return true;
    }

    /**
     * calcula distancia entre dois paramentros
     * @param v
     * @param w
     * @param caminhos
     * @return distancia
     */
    public static double calculaDistancia(int v, int w, ST<Integer, Integer> caminhos) {
        double distancia = -1;
        int vid= caminhos.get(v);
        int wid= caminhos.get(w);
        Coordenada cv=null;
        Coordenada cw=null;

        for (Empresa empresa: empresaST) {
            if(empresa.getId()==vid){
                cv=empresa.getLocalizacao();
                //System.out.println("cv empresa");
            }
            if(empresa.getId()==wid){
                cw=empresa.getLocalizacao();
                //System.out.println("cw empresa");
            }
        }
        for (PontoPassagem pontoPassagem: pontosPassagemST) {
            if(pontoPassagem.getId()==vid){
                cv=pontoPassagem.getLocalizacao();
                //System.out.println("cv pp");
            }
            if(pontoPassagem.getId()==wid){
                cw=pontoPassagem.getLocalizacao();
                //System.out.println("cw pp");
            }
        }
        for (Date date1: encontroST.keys()) {
            if(encontroST.get(date1).getId()==vid){
                cv=encontroST.get(date1).getLocalizacao();
                //System.out.println("cv encontro");
            }
            if(encontroST.get(date1).getId()==wid){
                cw=encontroST.get(date1).getLocalizacao();
                //System.out.println("cw encontro");
            }
        }

        if (cv!=null && cw!=null){
            distancia= cv.dist(cw);
            return distancia;
        }
        else {
            return distancia;
        }
    }

    /**
     * calcular o peso entre dois profissionais relativamente a competencias, interesses, encontros, historico empresa, e empresas em comum que podemos saber atraves das adjacencias dos grafos. o peso difere para os diferentes parametros como podemos ver declarado no inicio do main.
     * @param profissional
     * @param profissional1
     * @param digraph
     * @param nodes
     * @return peso das ligações
     */
    public static int calculaPeso(Profissional profissional, Profissional profissional1, Digraph digraph, ST<Integer, Integer> nodes) {
        int peso=0;
        //System.out.println("calculo "+profissional.getNome()+" "+profissional1.getNome());
        for (Competencia ci: profissional.getCompetencias()) {
            for (Competencia ci2: profissional1.getCompetencias()) {
                if(ci.getId()==ci2.getId()){
                    //System.out.println("1 Competencia(1)");
                    peso +=PESO_COMPETENCIA;
                }
            }
        }
        for (Empresa ei: profissional.getHistoricoEmpresa().getEmpresas()) {
            for (Empresa e2: profissional1.getHistoricoEmpresa().getEmpresas()) {
                if(ei.getId()==e2.getId()){
                    //System.out.println("1 Historico(10)");
                    peso+=PESO_HISTEMPRESA;
                }
            }
        }
        for (Interesse i: profissional.getInteresses()) {
            for (Interesse i2: profissional1.getInteresses()) {
                if(i.getId()==i2.getId()){
                    //System.out.println("1 Interesse(2)");
                    peso+=PESO_INTERESSE;
                }
            }
        }
        for (Date di: profissional.getEncontros().keys()) {
            for (Date di2: profissional1.getEncontros().keys()) {
                if(di.compareTo(di2)==0){
                    //System.out.println("1 Encontro(5)");
                    peso+=PESO_ENCONTRO;
                }
            }
        }

        int adji=0;
        for (int i=0; i<nodes.size(); i++) {
            if(nodes.get(i)==profissional.getId()){
                adji=i;
            }
        }
        int adjj=0;
        for (int i=0; i<nodes.size(); i++) {
            if(nodes.get(i)==profissional1.getId()){
                adjj=i;
            }
        }
        for (Integer adj: digraph.adj(adji)) {
            for (Integer adj1: digraph.adj(adjj)) {
                if(adj==adj1){
                    //System.out.println("1 EmpresasComum(5)");
                    peso+=PESO_EMPRESACOMUM;
                }
            }
        }

        return peso;
    }

    /**
     * add empresa to DB
     * @param e
     */
    public static void addEmpresaToDB(Empresa e){
        for (Empresa empresa: empresaST) {
            if(empresa.getNome().compareTo(e.getNome())==0 || (empresa.getLocalizacao().getX()==e.getLocalizacao().getX() && empresa.getLocalizacao().getY()==e.getLocalizacao().getY())){
                System.out.println("Empresa já existe!");
                return;
            }
        }
        empresaST.add(e);
    }

    /**
     * add ponto de passagem to DB
     * @param i
     */
    public static void addPPToDB(PontoPassagem i){
        for (PontoPassagem pontoPassagem: pontosPassagemST) {
            if(pontoPassagem.getLocalizacao().getX()==i.getLocalizacao().getX() &&
                    pontoPassagem.getLocalizacao().getY()==i.getLocalizacao().getY()){
                System.out.println("Ponto de Passagem já existe!");
                return;
            }
        }
        pontosPassagemST.add(i);
    }

    /**
     * add interesse to DB
     * @param i
     */
    public static void addInteresseToDB(Interesse i){
        for (Interesse interesse: interesseST) {
            if(interesse.getNome().compareTo(i.getNome())==0){
                System.out.println("Interesse já existe!");
                return;
            }
        }
        interesseST.add(i);
    }

    /**
     * add competencia to DB
     * @param c
     */
    public static void addCompetenciaToDB(Competencia c){
        for (Competencia competencia: competenciaST) {
            if(competencia.getNome().compareTo(c.getNome())==0){
                System.out.println("Competencia já existe!");
                return;
            }
        }
        competenciaST.add(c);
    }

    /**
     * add profissional to DB
     * @param
     */
    public static void addProfissionalToDB(Profissional p){
        for (Integer i: profissionalST.keys()) {
            if(profissionalST.get(i).getNome().compareTo(p.getNome())==0 && profissionalST.get(i).getLocalizacao().getX()==p.getLocalizacao().getX() && profissionalST.get(i).getLocalizacao().getY()==p.getLocalizacao().getY() ){
                System.out.println("Profissional já existe!");
                return;
            }
        }
        profissionalST.put(p.getId(), p);
    }

    /**
     * add encontro to DB
     * @param e
     */
    public static void addEncontroToDB(Encontro e){
        for (Date date: encontroST.keys()) {
            if(encontroST.get(date).getEmpresa().getId()==e.getEmpresa().getId() && date==e.getData()){
                System.out.println("Encontro já existe!");
                return;
            }
        }
        encontroST.put(e.getData(), e);
    }

    /**
     * função para abrir um ficheiro que queremos tendo em conta o local onde se encontra, path
     * @param path
     * @return
     */
    public static BufferedReader openBufferedReader(String path) {

        try {
            FileReader fr= new FileReader(path);
            BufferedReader br= new BufferedReader(fr);
            return br;
        } catch (FileNotFoundException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }


    /**
     * função para ler grafo dirigido do txt
     * @throws IOException
     */
    public static void readDigraph() throws IOException {
        BufferedReader br= openBufferedReader(path_digraph);
        if(br!=null){
            String v= br.readLine();
            digraph= new Digraph(Integer.parseInt(v));
            String line= br.readLine();
                while (line!=null){
                    String[] dFields= line.split(delimiter);
                    digraph.addEdge(Integer.parseInt(dFields[0]), Integer.parseInt(dFields[1]));
                    line= br.readLine();
                }
                br.close();
        }
    }

    /**
     * função para ler st do grafo dirigido do txt
     * @throws IOException
     */
    public static void readDigraphST() throws IOException {
        BufferedReader br= openBufferedReader(path_digraphST);
        if(br!=null){
            String line= br.readLine();
            while (line!=null){
                String[] dFields= line.split(delimiter);
                prof_empST.put(Integer.parseInt(dFields[0]), Integer.parseInt(dFields[1]));
                line= br.readLine();
            }
            br.close();
        }
    }

    /**
     * função para gravar o grafo dirigifo para txt
     * @throws IOException
     */
    public static void saveDigraph() throws IOException {
        PrintWriter pw= openPrintWriter(path_digraph);
        if(pw!=null){
            pw.write(String.valueOf(digraph.V()));
            pw.println();
            for (int i=0; i<digraph.V(); i++){
                for (Integer integer: digraph.adj(i)) {
                    pw.write(i+delimiter+integer);
                    pw.println();
                }
            }
            pw.close();
        }
    }

    /**
     * função para gravar st do grafo dirigido para txt
     * @throws IOException
     */
    public static void saveDigraphST() throws IOException {
        PrintWriter pw= openPrintWriter(path_digraphST);
        if(pw!=null){
            for (Integer integer:prof_empST.keys()) {
                pw.write(integer+delimiter+prof_empST.get(integer));
                pw.println();
            }
            pw.close();
        }
    }

    /**
     * função para gravar grafo dirigido para bin
     * @throws IOException
     */
    public static void saveDigraphBin() {
        File f= new File(path_digraphBin);
        FileOutputStream fos;
        try {
            fos= new FileOutputStream(f);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(digraph);
        } catch (IOException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * função para gravar st do grafo dirigido para bin
     */
    public static void saveDigraphSTBin() {
        File f= new File(path_digraphSTBin);
        FileOutputStream fos;
        try {
            fos= new FileOutputStream(f);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(prof_empST);
        } catch (IOException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * função para ler grafo dirigido de bin
     */
    public static void readDigraphBin(){
        File f= new File(path_digraphBin);
        try {
            FileInputStream file= new FileInputStream(f);
            ObjectInputStream ois= new ObjectInputStream(file);
            digraph= (Digraph) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * função para ler st do grafo dirigifo de bin
     */
    public static void readDigraphSTBin(){
        File f= new File(path_digraphSTBin);
        try {
            FileInputStream file= new FileInputStream(f);
            ObjectInputStream ois= new ObjectInputStream(file);
            prof_empST= (ST<Integer, Integer>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * função para ler grafo pesado de txt
     * @throws IOException
     */
    public static void readWGraph() throws IOException {
        BufferedReader br= openBufferedReader(path_weightedgraph);
        if(br!=null){
            String v= br.readLine();
            edgeWeightedGraph= new EdgeWeightedGraph(Integer.parseInt(v));
            String line= br.readLine();
            while (line!=null){
                String[] dFields= line.split(delimiter);
                edgeWeightedGraph.addEdge(new Edge(Integer.parseInt(dFields[0]), Integer.parseInt(dFields[1]), Double.parseDouble(dFields[2])));
                line= br.readLine();
            }
            br.close();
        }
    }

    /**
     * função para ler st do grafo pesado de txt
     * @throws IOException
     */
    public static void readWGraphST() throws IOException {
        BufferedReader br= openBufferedReader(path_weightedgraphST);
        if(br!=null){
            String line= br.readLine();
            while (line!=null){
                String[] dFields= line.split(delimiter);
                prof_profST.put(Integer.parseInt(dFields[0]), Integer.parseInt(dFields[1]));
                line= br.readLine();
            }
            br.close();
        }
    }

    /**
     * função para gravar grafo pesado para txt
     * @throws IOException
     */
    public static void saveWGraph() throws IOException {
        PrintWriter pw= openPrintWriter(path_weightedgraph);
        if(pw!=null){
            pw.write(String.valueOf(edgeWeightedGraph.V()));
            pw.println();
            for (Edge edge:edgeWeightedGraph.edges()) {
                pw.write(edge.either()+delimiter+edge.getW()+delimiter+edge.weight());
                pw.println();
            }
            pw.close();
        }
    }

    /**
     * função para gravar st do grafo pesado para txt
     * @throws IOException
     */
    public static void saveWGraphST() throws IOException {
        PrintWriter pw= openPrintWriter(path_weightedgraphST);
        if(pw!=null){
            for (Integer integer:prof_profST.keys()) {
                pw.write(integer+delimiter+prof_profST.get(integer));
                pw.println();
            }
            pw.close();
        }
    }

    /**
     * função para gravar grafo pesado para bin
     */
    public static void saveWGraphBin() {
        File f= new File(path_weightedgraphBin);
        FileOutputStream fos;
        try {
            fos= new FileOutputStream(f);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(edgeWeightedGraph);
        } catch (IOException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * função para gravar st de grafo pesado para bin
     */
    public static void saveWGraphSTBin() {
        File f= new File(path_weightedgraphSTBin);
        FileOutputStream fos;
        try {
            fos= new FileOutputStream(f);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(prof_profST);
        } catch (IOException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * função para ler grafo pesado de bin
     */
    public static void readWGraphBin(){
        File f= new File(path_weightedgraphBin);
        try {
            FileInputStream file= new FileInputStream(f);
            ObjectInputStream ois= new ObjectInputStream(file);
            edgeWeightedGraph= (EdgeWeightedGraph) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * função para ler st do grafo pesado de bin
     */
    public static void readWGraphSTBin(){
        File f= new File(path_weightedgraphSTBin);
        try {
            FileInputStream file= new FileInputStream(f);
            ObjectInputStream ois= new ObjectInputStream(file);
            prof_profST= (ST<Integer, Integer>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * função para ler grafo pesado dirigido  de txt
     * @throws IOException
     */
    public static void readWDigraph() throws IOException {
        BufferedReader br= openBufferedReader(path_weighteddigraph);
        if(br!=null){
            String v= br.readLine();
            edgeWeightedDigraph= new EdgeWeightedDigraph_Projeto(Integer.parseInt(v));
            String line= br.readLine();
            while (line!=null){
                String[] dFields= line.split(delimiter);
                edgeWeightedDigraph.addEdge(new DirectedEdge_Projeto(Integer.parseInt(dFields[0]), Integer.parseInt(dFields[1]), Double.parseDouble(dFields[2]), Double.parseDouble(dFields[3])));
                line= br.readLine();
            }
            br.close();
        }
    }

    /**
     * função para ler st do grafo pesado dirigido de txt
     * @throws IOException
     */
    public static void readWDigraphST() throws IOException {
        BufferedReader br= openBufferedReader(path_weighteddigraphST);
        if(br!=null){
            String line= br.readLine();
            while (line!=null){
                String[] dFields= line.split(delimiter);
                caminhosST.put(Integer.parseInt(dFields[0]), Integer.parseInt(dFields[1]));
                line= br.readLine();
            }
            br.close();
        }
    }

    /**
     * função para gravar grafo pesado dirigido para txt
     * @throws IOException
     */
    public static void saveWDigraph() throws IOException {
        PrintWriter pw= openPrintWriter(path_weighteddigraph);
        if(pw!=null){
            pw.write(String.valueOf(edgeWeightedDigraph.V()));
            pw.println();
            for (DirectedEdge_Projeto edge:edgeWeightedDigraph.edges()) {
                pw.write(edge.from()+delimiter+edge.to()+delimiter+edge.weight()+delimiter+edge.time());
                pw.println();
            }
            pw.close();
        }
    }

    /**
     * função para gravar st do grafo pesado dirigido para txt
     * @throws IOException
     */
    public static void saveWDigraphST() throws IOException {
        PrintWriter pw= openPrintWriter(path_weighteddigraphST);
        if(pw!=null){
            for (Integer integer:caminhosST.keys()) {
                pw.write(integer+delimiter+caminhosST.get(integer));
                pw.println();
            }
            pw.close();
        }
    }

    /**
     * função para gravar grafo pesado dirigido para bin
     */
    public static void saveWDigraphBin() {
        File f= new File(path_weighteddigraphBin);
        FileOutputStream fos;
        try {
            fos= new FileOutputStream(f);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(edgeWeightedDigraph);
        } catch (IOException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * função para gravar st do grafo pesado dirigido para bin
     */
    public static void saveWDigraphSTBin() {
        File f= new File(path_weighteddigraphSTBin);
        FileOutputStream fos;
        try {
            fos= new FileOutputStream(f);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(caminhosST);
        } catch (IOException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * função para ler grafo pesado dirigido de bin
     */
    public static void readWDigraphBin(){
        File f= new File(path_weighteddigraphBin);
        try {
            FileInputStream file= new FileInputStream(f);
            ObjectInputStream ois= new ObjectInputStream(file);
            edgeWeightedDigraph= (EdgeWeightedDigraph_Projeto) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * função para ler st do grafo pesado dirigido de bin
     */
    public static void readWDigraphSTBin(){
        File f= new File(path_weighteddigraphSTBin);
        try {
            FileInputStream file= new FileInputStream(f);
            ObjectInputStream ois= new ObjectInputStream(file);
            caminhosST= (ST<Integer, Integer>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * função que nos dá profissionais com uma determinada competencia
     * @param profissionals
     * @param c
     * @return profissionais
     */
    public static SeparateChainingHashST<Integer, Profissional> getProfissionaisCompetencia(SeparateChainingHashST<Integer, Profissional> profissionals, Competencia c){
        SeparateChainingHashST<Integer, Profissional> profissionals1= new SeparateChainingHashST<>();
        for (Integer p : profissionals.keys()) {
            for (Competencia comp: profissionals.get(p).getCompetencias()) {
                if(comp.getNome().compareTo(c.getNome())==0){
                    profissionals1.put(profissionals.get(p).getId(), profissionals.get(p));
                }
            }
        }
        return profissionals1;
    }

    /**
     * função que nos dá encontros com uma determinada data
     * @param encontros
     * @param inicio
     * @param fim
     * @return encontros
     */
    public static RedBlackBST_Aed2<Date, Encontro> getEncontrobyDate(RedBlackBST_Aed2<Date, Encontro> encontros,Date inicio, Date fim){
        RedBlackBST_Aed2<Date, Encontro> encontros1= new RedBlackBST_Aed2<>();
        for (Date d: encontros.keys()) {
            if(d.afterDate(inicio) && d.beforeDate(fim)){
                encontros1.put(d, encontros.get(d));
            }
        }
        return encontros1;
    }

    /**
     * função que nos dá encontros com uma determinada empresa
     * @param encontros
     * @param empresa
     * @return encontros
     */
    public static RedBlackBST_Aed2<Date, Encontro> getEncontrobyEmpresa(RedBlackBST_Aed2<Date, Encontro> encontros, Empresa empresa){
        RedBlackBST_Aed2<Date, Encontro> encontros1= new RedBlackBST_Aed2<>();
        for (Date d: encontros.keys()) {
            if(encontros.get(d).getEmpresa().getId() == empresa.getId()){
                encontros1.put(d, encontros.get(d));
            }
        }
        return encontros1;
    }

    /**
     * função que nos dá encontros com mais de n participantes
     * @param encontros
     * @param n
     * @return encontros
     */
    public static RedBlackBST_Aed2<Date, Encontro> getEncontrobyNumber(RedBlackBST_Aed2<Date, Encontro> encontros, int n){
        RedBlackBST_Aed2<Date, Encontro> encontros1= new RedBlackBST_Aed2<>();
        for (Date d: encontros.keys()) {
            if(encontros.get(d).getProfissionais().size()>=n){
                encontros1.put(d, encontros.get(d));
            }
        }
        return encontros1;
    }

    /**
     * função que nos dá profissionais desempregados
     * @param profissionals
     * @return profissionais
     */
    public static SeparateChainingHashST<Integer, Profissional> getDesempregados(SeparateChainingHashST<Integer, Profissional> profissionals){
        SeparateChainingHashST<Integer, Profissional> profissionals1= new SeparateChainingHashST<>();
        for (Integer p: profissionals.keys()) {
            if(!profissionals.get(p).isTrabalha()){
                profissionals1.put(profissionals.get(p).getId(), profissionals.get(p));
            }
        }
        return profissionals1;
    }

    /**
     * ao remover profissinal guardamos para um txt de profissionais removidos e metemos todos os campos do profissional removido a null
     * @throws IOException
     */
    public static void removerProfissional(Profissional p) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("data/profissionais_removidos.txt", true));
        writer.append(p.getNome() + "; " + p.getLocalizacao() + "; Empregado: "+ p.isTrabalha());
        if(p.getEmpresa()!=null){
            writer.append("; Empresa: "+p.getEmpresa().getNome());
        }
        if(p.getInteresses()!=null){
            writer.append("; Interesses: ");
            for (Interesse i: p.getInteresses()) {
                writer.append(i.getNome());
                writer.append(", ");
            }
        }

        if(p.getCompetencias()!=null){
            writer.append("; Competencias: ");
            for (Competencia i: p.getCompetencias()) {
                writer.append(i.getNome());
                writer.append(", ");
            }
        }

        if(!p.getHistoricoEmpresa().getEmpresas().isEmpty()){
            writer.append("; Histórico de empresas: ");
            for (Empresa i: p.getHistoricoEmpresa().getEmpresas()) {
                writer.append(i.getNome());
                writer.append(", ");
            }
        }

        if(!p.getEncontros().isEmpty()){
            writer.append("; Encontros: ");
            for (Date i: p.getEncontros().keys()) {
                writer.append(p.getEncontros().get(i).getNome());
                writer.append(", ");
            }
        }

        writer.newLine();
        writer.close();

        p.setNome(null);
        p.setLocalizacao(null);
        p.setTrabalha(false);
        if (p.getEmpresa() != null) {
            p.getEmpresa().removerProfissional(p);
        }

        if (p.getEncontros() != null) {
            for (Date de: p.getEncontros().keys()) {
                p.getEncontros().get(de).removeProfissional(p);
            }
        }
        p.setEncontros(null);

        p.getHistoricoEmpresa().setProfissional(null);
        p.setHistoricoEmpresa(null);
        p.setCompetencias(null);
        p.setInteresses(null);

        for (Integer i: profissionalST.keys()) {
            if(p.getId()==profissionalST.get(i).getId()){
                profissionalST.delete(i);
            }
        }
    }

    /**
     * ao remover guardamos para um ficheiro txt de encontros removidos e metemos toda a informção desse encontro removido a null
     * @throws IOException
     */
    public static void removerEncontro(Encontro e)throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("data/encontros_removidos.txt", true));
        writer.append(e.getEmpresa().getNome()+": "+e.getNome()+"; "+e.getLocalizacao());
        if(!e.getProfissionais().isEmpty()){
            writer.append("; Convidados: ");
            for (Integer i: e.getProfissionais().keys()) {
                writer.append(e.getProfissionais().get(i).getNome());
                writer.append(", ");
            }
        }

        writer.newLine();
        writer.close();

        e.setNome(null);
        e.setLocalizacao(null);

        e.getEmpresa().removerEncontro(e.getData());
        e.setProfissionais(null);

        for (Date d: encontroST.keys()) {
            if(d.compareTo(e.getData())==0){
                encontroST.delete(d);
            }
        }

        e.setData(null);
    }

    /**
     * ao remover guardamos a empresa e toda a sua informação, para um ficheiro txt de empresas removidas e metemos a null toda a informação da empresa removida
     * @throws IOException
     */
    public static void removerEmpresa(Empresa e) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("data/empresas_removidas.txt", true));
        writer.append(e.getNome() + "; " + e.getLocalizacao());
        if(!e.getProfissionais().isEmpty()){
            writer.append("; Empregados: ");
            for (Integer i: e.getProfissionais().keys()) {
                writer.append(e.getProfissionais().get(i).getNome());
                writer.append(", ");
            }
        }

        writer.newLine();
        writer.close();

        e.setNome(null);
        e.setLocalizacao(null);

        if (e.getEncontros() != null) {
            for (Date d : e.getEncontros().keys()) {
                Encontro encontro = e.getEncontros().get(d);
                for (Integer p : encontro.getProfissionais().keys()) {
                    encontro.getProfissionais().get(p).removerEncontro(d);
                }
            }
        }
        e.setEncontros(null);

        if (e.getProfissionais() != null) {
            for (Integer p : e.getProfissionais().keys()) {
                e.getProfissionais().get(p).removerEmpresa();
                e.getProfissionais().delete(p);
            }
        }
        e.setProfissionais(null);

        for (Empresa empresa: empresaST) {
            if(empresa.getId()==e.getId()){
                empresaST.remove(e);
            }
        }
    }

    /**
     *ao remover uma competencia guardamos essa competencia num txt de competencias removidas e metemos essa competencia removida a null
     * @throws IOException
     */
    public static void removerCompetencia(Competencia c)throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("data/competencias_removidas.txt", true));
        writer.append("Competencia: "+c.getNome()+"; ");
        writer.newLine();
        writer.close();

        for (Competencia comp: competenciaST) {
            if(comp.getId()==c.getId()){
                competenciaST.remove(c);
            }
        }

        c.setNome(null);
    }


    /**
     * ao remover um interesse, guardamos num ficheiro txt dos interesses removidos esse interesse, e metemos a null esse interesse removido
     * @throws IOException
     */
    public static void removerInteresse(Interesse i)throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("data/interesses_removidos.txt", true));
        writer.append("Interesse: "+i.getNome()+"; ");
        writer.newLine();
        writer.close();

        for (Interesse interesse: interesseST) {
            if(interesse.getId()==i.getId()){
                interesseST.remove(i);
            }
        }

        i.setNome(null);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("projeto.fxml"));

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Course GUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}

