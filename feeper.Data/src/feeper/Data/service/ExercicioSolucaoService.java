/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ConfiguracaoSistema;
import feeper.Data.entity.ExercicioCasoTeste;
import feeper.Data.entity.ExercicioClasse;
import feeper.Data.entity.ExercicioSolucao;
import feeper.Data.entity.ExercicioSolucaoClasse;
import feeper.Data.entity.ExercicioSolucaoErro;
import feeper.Data.model.EErrorType;
import feeper.Data.model.EStatusSolucao;
import feeper.Data.model.HibernateUtil;
import feeper.Data.model.IntegerResult;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.hibernate.SQLQuery;

/**
 *
 * @author gilvani
 */
public class ExercicioSolucaoService extends HibernateUtil<ExercicioSolucao> {

    public static final String CODIGO_PADRAO_CLASSE = "/* package qualquer; // Não coloque nome no package */\n"
            + "\n"
            + "import java.util.*;\n"
            + "import java.lang.*;\n"
            + "import java.io.*;\n"
            + "\n"
            + "class #@#CLASSE#@#\n"
            + "{\n"
            + "   // Coloque aqui o seu código\n"
            + "}";

    public ExercicioSolucaoService() {
        super(ExercicioSolucao.class);
    }

    public ExercicioSolucao getLastByIdExercicio(int idExercicio, int idAluno) {
        try {

            SQLQuery query = query("select * from ExercicioSolucao where IdExercicio = :idExercicio and IdAluno = :idAluno order by ID desc limit 1").addEntity(ExercicioSolucao.class);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAluno", idAluno);

            ExercicioSolucao solucao = (ExercicioSolucao) query.list().get(0);

            ExercicioSolucaoErroService repoErros = new ExercicioSolucaoErroService();
            solucao.setErros(repoErros.getAllByIdSolucao(solucao.getId()));

            return solucao;
        } catch (Exception e) {
            return null;
        }
    }

    public List<ExercicioSolucao> getByIdExercicio(int idExercicio, int idAluno) {
        try {

            SQLQuery query = query("select * from ExercicioSolucao where IdExercicio = :idExercicio and IdAluno = :idAluno order by ID desc").addEntity(ExercicioSolucao.class);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAluno", idAluno);

            List<ExercicioSolucao> data = query.list();

            ExercicioSolucaoErroService repoErros = new ExercicioSolucaoErroService();
            for (ExercicioSolucao solucao : data) {
                solucao.setErros(repoErros.getAllByIdSolucao(solucao.getId()));
            }

            return data;

        } catch (Exception e) {
            return null;
        }
    }

    public void salvarSolucao(int idAluno, int idExercicio, IntegerResult idSolucao) {

        ExercicioSolucao solucao = new ExercicioSolucao();
        solucao.setDataCadastro(new Date());
        solucao.setIdAluno(idAluno);
        solucao.setIdExercicio(idExercicio);
        solucao.setIdStatus(EStatusSolucao.AGUARDANDO);

        if (insert(solucao)) {
            idSolucao.setResult(solucao.getId());

            //realiza uma cópia das classes do exercício para a solução para manter um histórico
            ExercicioClasseService repoClassesExercicio = new ExercicioClasseService();
            ExercicioSolucaoClasseService repoClassesSolucao = new ExercicioSolucaoClasseService();

            List<ExercicioClasse> classesExercicio = repoClassesExercicio.getAllByIdExercicio(idExercicio, idAluno);

            for (ExercicioClasse classe : classesExercicio) {

                ExercicioSolucaoClasse classeSolucao = new ExercicioSolucaoClasse();
                classeSolucao.setIdSolucao(solucao.getId());
                classeSolucao.setNomeClasse(classe.getNomeClasse());
                classeSolucao.setCodigo(classe.getCodigo());
                repoClassesSolucao.insert(classeSolucao);
            }
        }
    }

    public ExercicioSolucao enviarCorrecao(int idSolucao) {

        ExercicioSolucao solucao = this.getById(idSolucao);
        int idAluno = solucao.getIdAluno();
        int idExercicio = solucao.getIdExercicio();

        ExercicioSolucaoClasseService repoClassesSolucao = new ExercicioSolucaoClasseService();
        solucao.setClasses(repoClassesSolucao.getbyIdSolucao(solucao.getId()));

        ExercicioCasoTesteService repoTestes = new ExercicioCasoTesteService();
        solucao.setTestes(repoTestes.getByIdExercicio(idExercicio, true));

        try {
            ConfiguracaoService confService = new ConfiguracaoService();
            ConfiguracaoSistema conf = confService.getConfiguracao();
            String serverURL = conf.getEnderecoSistemaCorretorJava();
            String xmlData = this.solucaoToXML(solucao);

            URL url = new URL(serverURL);
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/xml");
            connection.setConnectTimeout(6000000);
            connection.setReadTimeout(6000000);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(xmlData);
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
            solucao = this.solucaoFromXML(builder.toString());

            if (solucao.getErros() != null) {
                ExercicioSolucaoErroService repoErrosSolucao = new ExercicioSolucaoErroService();

                for (ExercicioSolucaoErro erro : solucao.getErros()) {

                    repoErrosSolucao.insert(erro);
                }
            }
            this.update(solucao);

        } catch (Exception ex) {
            solucao.setIdStatus(EStatusSolucao.ERRO_COMPILACAO);
            solucao.setErrosCount(1);
            this.update(solucao);

            ExercicioSolucaoErro erro = new ExercicioSolucaoErro();
            erro.setIdSolucao(solucao.getId());
            erro.setIdCasoTeste(-1);
            erro.setErrorType(EErrorType.COMPILACAO);
            erro.setLinhaErro(-1);
            erro.setMensagemErro(ex.getMessage());

            ExercicioSolucaoErroService repoErrosSolucao = new ExercicioSolucaoErroService();
            repoErrosSolucao.insert(erro);
        }

        return solucao;
    }

    private String solucaoToXML(ExercicioSolucao solucao) throws JAXBException {

        for (ExercicioSolucaoClasse classe : solucao.getClasses()) {
            classe.setCodigo(classe.getCodigo().replaceAll("\n", "#n").replaceAll("\r", "#r"));
        }
        JAXBContext context = JAXBContext.newInstance(ExercicioSolucao.class);
        Marshaller m = context.createMarshaller();
        StringWriter sw = new StringWriter();
        m.marshal(solucao, sw);

        return sw.toString();
    }

    private ExercicioSolucao solucaoFromXML(String xmlString) throws JAXBException {

        StringReader reader = new StringReader(xmlString);
        JAXBContext jaxbContext = JAXBContext.newInstance(ExercicioSolucao.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (ExercicioSolucao) jaxbUnmarshaller.unmarshal(reader);
    }
}
