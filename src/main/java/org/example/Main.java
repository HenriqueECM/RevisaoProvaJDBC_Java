package org.example;

import com.sun.security.jgss.GSSUtil;
import org.example.dao.*;
import org.example.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        boolean sair = false;

        System.out.println("""
                    
                Sistema de Manutenção Industrial
                    
                1 - Cadastrar Máquina
                2 - Cadastrar Técnico
                3 - Cadastrar Peça
                4 - Criar Ordem de Manutenção
                5 - Associar Peças à Ordem
                6 - Executar Manutenção
                    
                0 - Sair
                Escolha uma operação:""");
        int escolha = SC.nextInt();

        SC.nextLine();

        switch (escolha){
            case 1: {
                cadastrarMaquina();
                break;
            }

            case 2: {
                cadastrarTecnico();
                break;
            }

            case 3: {
                cadastrarPeca();
                break;
            }

            case 4: {
                ordemManutencao();
                break;
            }
            case 5: {
                associarPecaOrdem();
                break;
            }
            case 0: {
                sair = true;
                System.out.println("Sistema Finalizando...");
                break;
            }
        }
        if (!sair){
            menu();
        }
    }

    private static void associarPecaOrdem() {
        List<Integer> opcoesOrdem = new ArrayList<>();
        OrdemManutencaoDAO ordemManutencaoDAO = new OrdemManutencaoDAO();
        List<OrdemManutencao> ordemManutencaoList = ordemManutencaoDAO.listarOrdemManutencaoPendentes();
        for (OrdemManutencao ordemManutencao : ordemManutencaoList){
            System.out.println("--------- Ordem de Manutenção ---------\n" +
                    " | ID: " + ordemManutencao.getId() +
                    " | ID MÁQUINA: " + ordemManutencao.getIdMaquina() +
                    " | ID TÉCNICO: " + ordemManutencao.getIdTecnico() +
                    " | DATA SOLICITAÇÃO: " + ordemManutencao.getDataSolicitacao() +
                    " | STATUS: " + ordemManutencao.getStatus() +
                    "\n--------------------------------------");
            opcoesOrdem.add(ordemManutencao.getId());
        }
        System.out.println("Digite o ID da ordem para selecionar: ");
        int idOrdem = SC.nextInt();
        SC.nextLine();

        if (opcoesOrdem.contains(idOrdem)){

            boolean sair = false;

            while (!sair){
                List<Integer> opcoesPeca = new ArrayList<>();
                PecaDAO pecaDao = new PecaDAO();
                List<Peca> pecaList = pecaDao.listarTodasPecas();
                OrdemPecaDAO ordemPecaDAO = new OrdemPecaDAO();


                for (Peca peca : pecaList){
                    System.out.println("-------- PEÇAS --------\n" +
                            " | ID: " + peca.getId() +
                            " | NOME: " + peca.getNome() +
                            " | ESTOQUE: " + peca.getEstoque() +
                            "\n-----------------------");
                    opcoesPeca.add(peca.getId());
                }

                System.out.println("Digite o ID da peça para selecionar: ");
                int idPeca = SC.nextInt();
                SC.nextLine();

                boolean pecaOrdemExiste = ordemPecaDAO.buscarExistencia(idOrdem, idPeca);

                // verificar opção valida e verificar se os dois já estão cadastrados
                if(opcoesPeca.contains(idPeca) && !pecaOrdemExiste){
                    System.out.println("Digite a quantidade da peça que será utilizada");
                    double quantidade = SC.nextDouble();
                    SC.nextLine();

                    double quantidadeEstoque = 0;
                    for (Peca peca : pecaList){
                        if (idPeca == peca.getId()){
                            quantidadeEstoque = peca.getEstoque();
                        }
                    }

                    if (quantidadeEstoque >= quantidade && quantidade > 0){
                        OrdemPeca ordemPeca = new OrdemPeca(idOrdem, idPeca, quantidade);
                        ordemPecaDAO.inserirOrdemPeca(ordemPeca);

                        System.out.println("Deseja adicionar mais uma peça à ordem? \n1 - Sim\n2 - Não");
                        int opcaoSair = SC.nextInt();
                        SC.nextLine();

                        if (opcaoSair == 2){
                            sair = true;
                        } else {
                            associarPecaOrdem();
                        }
                    } else {
                        System.out.println("Quantidade inválida. Insira um número possível!");
                    }
                } else {
                    System.out.println("Opção inválida!");
                }
            }
        }
    }

    private static void ordemManutencao() {
        List<Integer> opcoesMaquina = new ArrayList<>();
        MaquinaDAO maquinaDao = new MaquinaDAO();
        List<Maquina> maquina = maquinaDao.listarMaquinaOperacional();
        for (Maquina m : maquina){
            System.out.println("----------- Máquina -----------\n" +
                    " | ID: " + m.getId() +
                    " | NOME: " + m.getNome() +
                    " | SETOR: " + m.getSetor() +
                    " | STATUS: " + m.getStatus() +
                    "\n-------------------------------");
            opcoesMaquina.add(m.getId());
        }
        System.out.println("Digite o ID da máquina para selecionar: ");
        int idMaquina = SC.nextInt();

        if (opcoesMaquina.contains(idMaquina)){
            List<Integer> opcoesTecnico = new ArrayList<>();
            TecnicoDAO tecnicoDao = new TecnicoDAO();
            List<Tecnico> tecnicoList = tecnicoDao.listarTodosTecnicos();

            for (Tecnico tecnico : tecnicoList){
                System.out.println("------- Técnicos -------\n" +
                        " | ID: " + tecnico.getId() +
                        " | NOME: " + tecnico.getNome() +
                        " | ESPECIALIDADE: " + tecnico.getEspecialidade() +
                        "------------------------");
                opcoesTecnico.add(tecnico.getId());
            }

            System.out.println("Digite o ID do técnico para selecionar: ");
            int idTecnico = SC.nextInt();

            if (opcoesTecnico.contains(idTecnico)){
                OrdemManutencaoDAO ordemManutencaoDAO = new OrdemManutencaoDAO();
                OrdemManutencao ordemManutencao = new OrdemManutencao(idMaquina, idTecnico, LocalDate.now(), "PENDENTE");

                try {
                    ordemManutencaoDAO.inserirOrdemM(ordemManutencao);
                    maquinaDao.atualizarStatus(idMaquina, "EM_MANUTENCAO");
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Não existe esse ID! Digite outro ID do técnico.");
                ordemManutencao();
            }
        } else {
            System.out.println("Não existe esse ID! Digite outro ID da máquina.");
            ordemManutencao();
        }
    }

    private static void cadastrarPeca() {

        System.out.println("Digite nome da peça: ");
        String nome = SC.nextLine();

        System.out.println("Digite a quantidade de estoque de peças: ");
        double estoque = SC.nextDouble();

        if (!nome.isBlank() && estoque > 0){

            PecaDAO pecaDao = new PecaDAO();
            Peca peca = new Peca(nome, estoque);

            boolean pecaExiste = pecaDao.buscarExistencia(peca);
            if (!pecaExiste){
                pecaDao.inserirPeca(peca);
            } else {
                System.out.println("Peça já está cadastrado! Tente novamente.");
            }

        } else {
            System.out.println("ERRO! Insira dados válidos.");
        }
    }

    private static void cadastrarTecnico() {

        System.out.println("Digite nome do técnico: ");
        String nomeTecnico = SC.nextLine();

        System.out.println("Digite a especialidade do técnico: ");
        String especialidade = SC.nextLine();

        if (!nomeTecnico.isBlank() && !especialidade.isBlank()){
            Tecnico tecnico = new Tecnico(nomeTecnico, especialidade);
            TecnicoDAO tecnicoDao = new TecnicoDAO();

            boolean tecnicoExiste = tecnicoDao.buscarExistencia(tecnico);

            if(!tecnicoExiste){
                tecnicoDao.inserirTecnico(tecnico);
            } else {
                System.out.println("Técnico já está cadastrado! Tente novamente.");
            }

        } else {
            System.out.println("ERRO! Insira dados válidos.");
        }
    }

    private static void cadastrarMaquina() {

        System.out.println("Digite nome da máquina: ");
        String nome = SC.nextLine();

        System.out.println("Digite setor da máquina: ");
        String setor = SC.nextLine();

        if (!nome.isBlank() && !setor.isBlank()){
            Maquina maquina = new Maquina(nome, setor, "OPERACIONAL");
            MaquinaDAO maquinaDao = new MaquinaDAO();

            boolean maquinaExiste = maquinaDao.buscarExistencia(maquina);

            if (!maquinaExiste){
                maquinaDao.inserirMaquina(maquina);
            } else {
                System.out.println("Máquina já está cadastrado! Tente novamente.");

            }
        } else {
            System.out.println("ERRO! Insira dados válidos.");
        }
    }
}