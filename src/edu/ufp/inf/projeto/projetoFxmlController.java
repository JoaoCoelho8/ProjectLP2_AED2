package edu.ufp.inf.projeto;

import edu.princeton.cs.algs4.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static edu.ufp.inf.projeto.Main.*;

public class projetoFxmlController {

    public static String delimiter= ";";
    public static ArrayList<Empresa> empresaST= Empresa.carregarEmpresas();
    public static ArrayList<PontoPassagem> pontosPassagemST= new ArrayList<>();
    public static ArrayList<Interesse> interesseST= Interesse.carregarInteresses();
    public static ArrayList<Competencia> competenciaST= Competencia.carregarCompetencias();
    public static SeparateChainingHashST<Integer, Profissional> profissionalST= Profissional.carregarProfissionais(empresaST, interesseST, competenciaST);
    public static RedBlackBST_Aed2<Date, Encontro> encontroST= Encontro.carregarEncontros(empresaST);
    public Button carregarGrafo;
    public HBox addedge;
    public TextField AddEdgeTimeField;
    public Button addEdge;
    public TextField EditEdgeTimeField;
    public HBox RequisitoE;
    public TextArea InserirCompetenciasProcurarE;
    public TextArea resultArea;

    private Double radius= 20.0;

    public ComboBox GrafoComboBox;
    public Group graphGroup;
    public TextField AddEdgeFromField;
    public TextField AddEdgeToField;
    public TextField AddEdgeWeightField;
    public TextField RemoveEdgeFromField;
    public TextField RemoveEdgeToField;
    public TextField EditEdgeFromField;
    public TextField EditEdgeToField;
    public TextField EditEdgeWeightField;
    public ComboBox<String> PesquisaComboBox;
    public HBox RequisitoA;
    public TextField InserirProfissionaisProcurar;
    public HBox RequisitoB;
    public TextField InserirEncontrosaProcurar;
    public HBox RequisitoD;
    public TextArea InserirCompetenciasProcurar;
    public HBox RequisitoF;
    public TextField InserirProfissionalParaObterAmizades;
    public TextField InserirEmpresaQueOProfissionalQuerSeguir;
    public HBox RequisitoH;
    public TextField InserirDataIinico;
    public TextField InserirDataFim;

    public void handleReadtxtGrafoRelacoesAction(ActionEvent actionEvent) {
        try {
            Main.readWGraphST();
            Main.readWGraph();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printEdgeWGraph();
    }

    public void handleReadtxtGrafoSeguidoresAction(ActionEvent actionEvent) {
        try {
            Main.readDigraphST();
            Main.readDigraph();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printDigraph();
    }

    public void handleReadtxtGrafoCaminhosAction(ActionEvent actionEvent) {
        try {
            Main.readWDigraphST();
            Main.readWDigraph();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printEdgeWDigraph();
    }

    public void handleReadbinGrafoRelacoesAction(ActionEvent actionEvent) {
        Main.readWGraphSTBin();
        Main.readWGraphBin();
        System.out.println("read");

        printEdgeWGraph();
    }

    public void printEdgeWGraph(){
        graphGroup.getChildren().clear();
        createGraphGroup(edgeWeightedGraph.V());
        //String[] lines= edgesField.getText().split("\n");
        for (Edge h: edgeWeightedGraph.edges()) {
            int v= h.either();
            int w= h.getW();
            StackPane spv= (StackPane) graphGroup.getChildren().get(v);
            Circle cv= (Circle) spv.getChildren().get(0);
            StackPane spw= (StackPane) graphGroup.getChildren().get(w);
            Circle cw= (Circle) spw.getChildren().get(0);
            Line line= new Line(cv.getCenterX(), cv.getCenterY(), cw.getCenterX(), cw.getCenterY());
            graphGroup.getChildren().add(line);
            Point pv = new Point(cv.getCenterX(), cv.getCenterY());
            Point pw = new Point(cw.getCenterX(), cw.getCenterY());
            Double xint = pv.getX() + ((pw.getX() - pv.getX()) / 2);
            Double yint = pv.getY() + ((pw.getY() - pv.getY()) / 2);
            //StackPane sp= (StackPane) graphGroup.getChildren().get(w);
            Text text = new Text();
            text.wrappingWidthProperty().set(100);
            text.setX(xint);
            text.setY(yint);
            double roundedOneDigitX = Math.round(h.weight() * 10) / 10.0;
            text.setText(String.valueOf(roundedOneDigitX));
            graphGroup.getChildren().add(text);
        }
    }

    public void handleReadbinGrafoSeguidoresAction(ActionEvent actionEvent) {
        Main.readDigraphSTBin();
        Main.readDigraphBin();

        printDigraph();
    }

    public void printDigraph(){
        graphGroup.getChildren().clear();
        createGraphGroup(digraph.V());
        //String[] lines= edgesField.getText().split("\n");
        for(int i=0; i<digraph.V(); i++) {
            for(Integer edge :digraph.adj(i)){
                int v= i;
                int w= edge;
                StackPane spv= (StackPane) graphGroup.getChildren().get(v);
                Circle cv= (Circle) spv.getChildren().get(0);
                StackPane spw= (StackPane) graphGroup.getChildren().get(w);
                Circle cw= (Circle) spw.getChildren().get(0);
                //Line line= new Line(cv.getCenterX(), cv.getCenterY(), cw.getCenterX(), cw.getCenterY());
                Arrow arrow = new Arrow(cv.getCenterX(), cv.getCenterY(), cw.getCenterX(), cw.getCenterY(), 5);
                graphGroup.getChildren().add(arrow);
            }
        }
    }

    public void handleReadbinGrafoCaminhosAction(ActionEvent actionEvent) {
        Main.readWDigraphSTBin();
        Main.readWDigraphBin();

        printEdgeWDigraph();
    }

    private void printEdgeWDigraph(){
        graphGroup.getChildren().clear();
        createGraphGroup(edgeWeightedDigraph.V());
        //String[] lines= edgesField.getText().split("\n");
        for (DirectedEdge_Projeto h: edgeWeightedDigraph.edges()) {
            int v = h.from();
            int w = h.to();
            StackPane spv = (StackPane) graphGroup.getChildren().get(v);
            Circle cv = (Circle) spv.getChildren().get(0);
            StackPane spw = (StackPane) graphGroup.getChildren().get(w);
            Circle cw = (Circle) spw.getChildren().get(0);
            //Line line= new Line(cv.getCenterX(), cv.getCenterY(), cw.getCenterX(), cw.getCenterY());
            Arrow arrow = new Arrow(cv.getCenterX(), cv.getCenterY(), cw.getCenterX(), cw.getCenterY(), 5);
            graphGroup.getChildren().add(arrow);
            Point pv = new Point(cv.getCenterX(), cv.getCenterY());
            Point pw = new Point(cw.getCenterX(), cw.getCenterY());
            Double xint = pv.getX() + ((pw.getX() - pv.getX()) / 2);
            Double yint = pv.getY() + ((pw.getY() - pv.getY()) / 2);
            //StackPane sp= (StackPane) graphGroup.getChildren().get(w);
            Text text = new Text();
            text.wrappingWidthProperty().set(100);
            text.setX(xint);
            text.setY(yint);
            double roundedOneDigitX = Math.round(h.weight() * 10) / 10.0;
            text.setText(String.valueOf(roundedOneDigitX));
            graphGroup.getChildren().add(text);
        }
    }
    private void createGraphGroup(int vnumber) {
        for (int i=0; i<vnumber; i++) {
            Random r= new Random();
            double posX= 20+r.nextDouble()*700;
            double posY= 20+ r.nextDouble()*500;
            Circle c= new Circle(posX, posY, radius);
            c.setFill(Color.BLUE.brighter().brighter());
            c.setId(""+i);
            Text text= new Text(""+i);
            StackPane stack= new StackPane();
            stack.setLayoutX(posX-radius);
            stack.setLayoutY(posY-radius);
            stack.getChildren().addAll(c, text);
            graphGroup.getChildren().add(stack);
        }
    }

    public void handleSavetxtGrafoRelacoesAction(ActionEvent actionEvent) {
        try {
            Main.saveWGraph();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSavetxtGrafoSeguidoresAction(ActionEvent actionEvent) {
        try {
            Main.saveDigraph();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSavetxtGrafoCaminhosAction(ActionEvent actionEvent) {
        try {
            Main.saveWGraph();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSavebinGrafoRelacoesAction(ActionEvent actionEvent) {
        Main.saveWGraphBin();
    }

    public void handleSavebinGrafoSeguidoresAction(ActionEvent actionEvent) {
        Main.saveDigraphBin();
    }

    public void handleSavebinGrafoCaminhosAction(ActionEvent actionEvent) {
        Main.saveWDigraphBin();
    }

    public void handleExitAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void handleAboutAction(ActionEvent actionEvent) {
        final Stage dialog = new Stage();
        dialog.setTitle("About");
        dialog.initModality(Modality.APPLICATION_MODAL);
        HBox dialogVbox = new HBox(0);
        Text text= new Text("João Coelho\nPedro Mota\nLinguagens Programação II\nAlgoritmia Estruturas Dados II\nProjeto 2019");
        text.setTextAlignment(TextAlignment.CENTER);
        dialogVbox.getChildren().add(text);
        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, 250, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    @FXML
    private void addDisciplinesToComboBox() {
        GrafoComboBox.getItems().clear();
        GrafoComboBox.getItems().add("Grafo de seguidores");
        GrafoComboBox.getItems().add("Grafo de relações");
        GrafoComboBox.getItems().add("Grafo de caminhos");
    }

    public void handleButtonAddEdgeAction(ActionEvent actionEvent) {
        String grafoName= (String) GrafoComboBox.getValue();
        switch (grafoName){
            case "Grafo de seguidores":
                digraph.addEdge(Integer.parseInt(AddEdgeFromField.getText()), Integer.parseInt(AddEdgeToField.getText()));
                printDigraph();
                break;

            case "Grafo de relações":
                edgeWeightedGraph.addEdge(new Edge(Integer.parseInt(AddEdgeFromField.getText()), Integer.parseInt(AddEdgeToField.getText()), Integer.parseInt(AddEdgeWeightField.getText())));
                printEdgeWGraph();
                break;

            case "Grafo de caminhos":
                edgeWeightedDigraph.addEdge(new DirectedEdge_Projeto(Integer.parseInt(AddEdgeFromField.getText()), Integer.parseInt(AddEdgeToField.getText()), Integer.parseInt(AddEdgeWeightField.getText()), Integer.parseInt(AddEdgeTimeField.getText())));
                printEdgeWDigraph();
                break;
        }
    }

    public void handleButtonRemoveEdgeAction(ActionEvent actionEvent) {
        String grafoName= (String) GrafoComboBox.getValue();
        switch (grafoName){
            case "Grafo de seguidores":
                for (int i=0; i<digraph.V(); i++) {
                    for (Integer integer:digraph.adj(i)) {
                        if(Integer.parseInt(RemoveEdgeFromField.getText())== i && Integer.parseInt(RemoveEdgeToField.getText())==integer) {
                            digraph.removeOneEdge(i, integer);
                        }
                    }
                }
                printDigraph();
                break;

            case "Grafo de relações":
                System.out.println("inicio");
                for (Edge e: edgeWeightedGraph.edges()) {
                    System.out.println("v: "+e.either()+"w: "+e.getW());
                    System.out.println(Integer.parseInt(RemoveEdgeFromField.getText()) + " "+ Integer.parseInt(RemoveEdgeToField.getText()));
                    if((e.either()==Integer.parseInt(RemoveEdgeFromField.getText()) && e.getW()==Integer.parseInt(RemoveEdgeToField.getText())) || (e.getW()==Integer.parseInt(RemoveEdgeFromField.getText()) && e.either()==Integer.parseInt(RemoveEdgeToField.getText()))) {
                        System.out.println("remover");
                        edgeWeightedGraph.removeOneEdge(e);
                    }
                }
                printEdgeWGraph();
                break;

            case "Grafo de caminhos":
                for (DirectedEdge_Projeto edge:edgeWeightedDigraph.edges()) {
                    if(edge.from()==Integer.parseInt(RemoveEdgeFromField.getText()) && edge.to()==Integer.parseInt(RemoveEdgeToField.getText())) {
                        edgeWeightedDigraph.removeEdge(edge);
                    }
                }
                printEdgeWDigraph();
                break;
        }
    }

    public void handleButtonEditEdgeAction(ActionEvent actionEvent) {
        String grafoName= (String) GrafoComboBox.getValue();
        switch (grafoName){
            case "Grafo de seguidores":
                for (int i=0; i<digraph.V(); i++) {
                    for (Integer integer:digraph.adj(i)) {
                        if(Integer.parseInt(EditEdgeFromField.getText())== i && Integer.parseInt(EditEdgeToField.getText())==integer) {
                            digraph.removeOneEdge(i, integer);
                            digraph.addEdge(Integer.parseInt(EditEdgeFromField.getText()), Integer.parseInt(EditEdgeToField.getText()));
                        }
                    }
                }
                printDigraph();
                break;

            case "Grafo de relações":
                System.out.println("inicio");
                for (Edge e: edgeWeightedGraph.edges()) {
                    if((e.either()==Integer.parseInt(EditEdgeFromField.getText()) && e.getW()==Integer.parseInt(EditEdgeToField.getText())) || (e.getW()==Integer.parseInt(EditEdgeFromField.getText()) && e.either()==Integer.parseInt(EditEdgeToField.getText()))) {
                        edgeWeightedGraph.removeOneEdge(e);
                        edgeWeightedGraph.addEdge(new Edge(Integer.parseInt(EditEdgeFromField.getText()), Integer.parseInt(EditEdgeToField.getText()), Double.parseDouble(EditEdgeWeightField.getText())));
                    }
                }
                printEdgeWGraph();
                break;

            case "Grafo de caminhos":
                for (DirectedEdge_Projeto edge:edgeWeightedDigraph.edges()) {
                    if(edge.from()==Integer.parseInt(EditEdgeFromField.getText()) && edge.to()==Integer.parseInt(EditEdgeToField.getText())) {
                        edgeWeightedDigraph.removeEdge(edge);
                        edgeWeightedDigraph.addEdge(new DirectedEdge_Projeto(Integer.parseInt(EditEdgeFromField.getText()), Integer.parseInt(EditEdgeToField.getText()), Integer.parseInt(EditEdgeWeightField.getText()), Integer.parseInt(EditEdgeTimeField.getText())));
                    }
                    if(edge.to()==Integer.parseInt(EditEdgeFromField.getText()) && edge.from()==Integer.parseInt(EditEdgeToField.getText())) {
                        edgeWeightedDigraph.removeEdge(edge);
                        edgeWeightedDigraph.addEdge(new DirectedEdge_Projeto(Integer.parseInt(EditEdgeFromField.getText()), Integer.parseInt(EditEdgeToField.getText()), Integer.parseInt(EditEdgeWeightField.getText()), Integer.parseInt(EditEdgeTimeField.getText())));
                    }
                }
                printEdgeWDigraph();
                break;
        }

    }

    public void handleButtonSeachProfissionals(ActionEvent actionEvent) {
        resultArea.clear();
        String empresa = InserirProfissionaisProcurar.getText();
        for (Empresa e: empresas) {
            if(e.getNome().compareTo(empresa)==0){
                for (Profissional i :Main.listProfsFollowEmp(digraph, e, prof_empST)) {
                    resultArea.appendText(i.getNome()+"\n");
                    //resultArea.setText(i.getNome());
                }
            }
        }
    }

    public void handleButtonSearchMeetings(ActionEvent actionEvent) {
        resultArea.clear();
        String encontro = InserirEncontrosaProcurar.getText();
        for (Date e: encontros.keys()) {
            if(encontros.get(e).getNome().compareTo(encontro)==0){
                for (Profissional i :Main.listProfsEnc(edgeWeightedDigraph, encontros.get(e), caminhosST)) {
                    //System.out.println("2: "+i.getNome());
                    //resultArea.setText(i.getNome());
                    resultArea.appendText(i.getNome()+"\n");
                }
            }
        }

    }

    public void handleButtonSearchCompetencias(ActionEvent actionEvent) {
        resultArea.clear();
        ArrayList<Competencia> comps= new ArrayList<>();
        String[] lines= InserirCompetenciasProcurar.getText().split("\n");
        for (String h: lines) {
            System.out.println(h);
            for (Competencia e : competencias) {
                if (e.getNome().compareTo(h) == 0) {
                    comps.add(e);
                }
            }
        }

        for (Profissional i :Main.listProfsCompWithoutEmp(edgeWeightedGraph, comps, prof_profST)) {
            //System.out.println("3: "+i.getNome());
            //resultArea.setText(i.getNome());
            resultArea.appendText(i.getNome()+"\n");
        }
    }

    public void handleButtonSearchCompetenciasE(ActionEvent actionEvent) {
        resultArea.clear();
        ArrayList<Competencia> comps= new ArrayList<>();
        String[] lines= InserirCompetenciasProcurar.getText().split("\n");
        for (String h: lines) {
            System.out.println(h);
            for (Competencia e : competencias) {
                if (e.getNome().compareTo(h) == 0) {
                    comps.add(e);
                }
            }
        }

        RedBlackBST_Aed2<Integer, Profissional> profs=Main.sugerirProfsComps(edgeWeightedGraph, comps, prof_profST);
        for (Integer i : profs.keys()) {
            //System.out.println("4: "+profs.get(i).getNome());
            //resultArea.setText(profs.get(i).getNome());
            resultArea.appendText(profs.get(i).getNome()+"\n");
        }
    }

    public void handleButtonSearchCirculoDeAmizades(ActionEvent actionEvent) throws IOException {
        //?
        resultArea.clear();
        Profissional p= null;
        Empresa e= null;
        for (Integer prf: profissionals.keys()) {
            if(InserirProfissionalParaObterAmizades.getText().compareTo(profissionals.get(prf).getNome())==0){
                p= profissionals.get(prf);
            }
        }
        for (Empresa emp: empresas) {
            if(InserirEmpresaQueOProfissionalQuerSeguir.getText().compareTo(emp.getNome())==0){
                e=emp;
            }
        }
        //Main.sugerirProfEmp(edgeWeightedGraph, digraph, e, p, prof_profST, prof_empST);
        String path= "./data/sugerirProfEmp.txt";
        BufferedReader br= openBufferedReader(path);
        if(br!=null){
            String line= br.readLine();
            while (line!=null){
                resultArea.appendText(line+"\n");
                line= br.readLine();
            }
            br.close();
        }
    }

    public void handleButtonSearchMettingsByDates(ActionEvent actionEvent) {
        resultArea.clear();

        String inicio= InserirDataIinico.getText();
        String fim= InserirDataFim.getText();

        String[] iFields= inicio.split("/");
        Date i= new Date(Integer.parseInt(iFields[0]), Integer.parseInt(iFields[1]), Integer.parseInt(iFields[2]));

        String[] fFields= fim.split("/");
        Date f= new Date(Integer.parseInt(fFields[0]), Integer.parseInt(fFields[1]), Integer.parseInt(fFields[2]));

        for (Date date:encontros.keys()) {
            if(date.afterDate(i) && date.beforeDate(f)){
                resultArea.appendText(encontros.get(date).getNome()+"\n");
            }
        }
    }

    public void handleSelectPesquisa(ActionEvent actionEvent) {
        String pesquisa= (String) PesquisaComboBox.getValue();
        switch (pesquisa){
            case "Listar as profissionais que seguem uma determinada empresa":
                RequisitoA.setVisible(true);
                RequisitoB.setVisible(false);
                RequisitoD.setVisible(false);
                RequisitoE.setVisible(false);
                RequisitoF.setVisible(false);
                RequisitoH.setVisible(false);
                break;

            case "Listar as profissionais que participaram um determinado encontro":
                RequisitoA.setVisible(false);
                RequisitoB.setVisible(true);
                RequisitoD.setVisible(false);
                RequisitoE.setVisible(false);
                RequisitoF.setVisible(false);
                RequisitoH.setVisible(false);
                break;

            case "Listar profissionais com determinadas competências e que não tenham empresa associada":
                RequisitoA.setVisible(false);
                RequisitoB.setVisible(false);
                RequisitoD.setVisible(true);
                RequisitoE.setVisible(false);
                RequisitoF.setVisible(false);
                RequisitoH.setVisible(false);
                break;

            case "Sugerir profissionais a empresas com base em competências":
                RequisitoA.setVisible(false);
                RequisitoB.setVisible(false);
                RequisitoD.setVisible(false);
                RequisitoE.setVisible(true);
                RequisitoF.setVisible(false);
                RequisitoH.setVisible(false);
                break;

            case "Sugerir profissional que pode facilitar a relação com uma determinada empresa":
                RequisitoA.setVisible(false);
                RequisitoB.setVisible(false);
                RequisitoD.setVisible(false);
                RequisitoE.setVisible(false);
                RequisitoF.setVisible(true);
                RequisitoH.setVisible(false);
                break;

            case "Procurar num determinado intervalo de tempo os encontros realizados ou agendados":
                RequisitoA.setVisible(false);
                RequisitoB.setVisible(false);
                RequisitoD.setVisible(false);
                RequisitoE.setVisible(false);
                RequisitoF.setVisible(false);
                RequisitoH.setVisible(true);
                break;
        }
    }

    public void handleCarregarPesquisasParaComboBox(Event mouseEvent) {
        PesquisaComboBox.getItems().clear();
        PesquisaComboBox.getItems().add("Listar as profissionais que seguem uma determinada empresa");
        PesquisaComboBox.getItems().add("Listar as profissionais que participaram um determinado encontro");
        PesquisaComboBox.getItems().add("Listar profissionais com determinadas competências e que não tenham empresa associada");
        PesquisaComboBox.getItems().add("Sugerir profissionais a empresas com base em competências");
        PesquisaComboBox.getItems().add("Sugerir profissional que pode facilitar a relação com uma determinada empresa");
        PesquisaComboBox.getItems().add("Procurar num determinado intervalo de tempo os encontros realizados ou agendados");
    }

    public void handleSelectGraph(ActionEvent actionEvent) {

        String grafoName= (String) GrafoComboBox.getValue();
        switch (grafoName){
            case "Grafo de seguidores":
                System.out.println("Seguidores");
                AddEdgeFromField.setVisible(true);
                AddEdgeToField.setVisible(true);
                AddEdgeWeightField.setVisible(false);
                AddEdgeTimeField.setVisible(false);
                EditEdgeFromField.setVisible(true);
                EditEdgeToField.setVisible(true);
                EditEdgeWeightField.setVisible(false);
                EditEdgeTimeField.setVisible(false);
                break;

            case "Grafo de relações":
                System.out.println("Relaçoes");
                AddEdgeFromField.setVisible(true);
                AddEdgeToField.setVisible(true);
                AddEdgeWeightField.setVisible(true);
                AddEdgeTimeField.setVisible(false);
                EditEdgeFromField.setVisible(true);
                EditEdgeToField.setVisible(true);
                EditEdgeWeightField.setVisible(true);
                EditEdgeTimeField.setVisible(false);
                break;

            case "Grafo de caminhos":
                System.out.println("Caminhos");
                AddEdgeFromField.setVisible(true);
                AddEdgeToField.setVisible(true);
                AddEdgeWeightField.setVisible(true);
                AddEdgeTimeField.setVisible(true);
                EditEdgeFromField.setVisible(true);
                EditEdgeToField.setVisible(true);
                EditEdgeWeightField.setVisible(true);
                EditEdgeTimeField.setVisible(true);
                break;
        }
    }

}
