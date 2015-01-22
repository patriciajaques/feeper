/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioClasse;
import feeper.Data.entity.ExercicioSolucao;
import feeper.Data.entity.ExercicioSolucaoClasse;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;

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
            return query.list();
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Object> getDetailsByIdExercicio(int idExercicio, int idAluno) {
        try {
            
            SQLQuery query = query("select "
                    + "  ES.ID, "
                    + "  PA.ID AS IdAluno, "
                    + "  PA.Nome AS Aluno, "
                    + "  E.ID AS IdExercicio, "
                    + "  E.Nome AS Exercicio, "
                    + "  GET_TIMEDURATION(ES.DataCadastro) AS DataCadastroString, "
                    + "  ES.DataCadastro, "
                    + "  SS.ID AS IdStatus, "
                    + "  SS.Nome AS Status "
                    + "from  "
                    + "  ExercicioSolucao ES "
                    + "  inner join Pessoa PA "
                    + "  on PA.ID = ES.IdAluno "
                    + "  and PA.Ativo = 1 "
                    + "  inner join Exercicio E "
                    + "  on E.ID = ES.IdExercicio "
                    + "  and E.Ativo = 1 "
                    + "  inner join StatusSolucao SS "
                    + "  on SS.ID = ES.IdStatus "
                    + "where  "
                    + "  ES.IdExercicio = :idExercicio "
                    + "  and ES.IdAluno = :idAluno "
                    + "order by ES.ID desc");
            
            query.addScalar("ID", IntegerType.INSTANCE);
            query.addScalar("IdAluno", IntegerType.INSTANCE);
            query.addScalar("Aluno", StringType.INSTANCE);
            query.addScalar("IdExercicio", IntegerType.INSTANCE);
            query.addScalar("Exercicio", StringType.INSTANCE);
            query.addScalar("DataCadastroString", StringType.INSTANCE);
            query.addScalar("DataCadastro", TimestampType.INSTANCE);
            query.addScalar("IdStatus", IntegerType.INSTANCE);
            query.addScalar("Status", StringType.INSTANCE);
            
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAluno", idAluno);
            return query.list();
            
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
    
    public void enviarCorrecao(int idSolucao) {
        
        ExercicioSolucao solucao = this.getById(idSolucao);
        int idAluno = solucao.getIdAluno();
        int idExercicio = solucao.getIdExercicio();
        
        ExercicioSolucaoClasseService repoClassesSolucao = new ExercicioSolucaoClasseService();
        solucao.setClasses(repoClassesSolucao.getbyIdSolucao(solucao.getId()));
        
        ExercicioCasoTesteService repoTestes = new ExercicioCasoTesteService();
        solucao.setTestes(repoTestes.getByIdExercicio(idExercicio));
        
        try {
            String serverURL = "http://localhost:8084/feeper.CorretorJava/rest/efetuaCorrecao";
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
            this.update(solucao);
            
        } catch (Exception ex) {
            solucao.setIdStatus(EStatusSolucao.ERRO_COMPILACAO);
            this.update(solucao);
        }
    }
    
    private String solucaoToXML(ExercicioSolucao solucao) throws JAXBException {
        
        JAXBContext context = JAXBContext.newInstance(ExercicioSolucao.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        
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
