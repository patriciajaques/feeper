/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.Pessoa;
import feeper.Data.model.HibernateUtil;
import feeper.Data.model.Util;
import org.hibernate.SQLQuery;

/**
 *
 * @author
 * fabioalves
 */
public class PessoaService extends HibernateUtil<Pessoa> {
    
    public PessoaService() {
        super(Pessoa.class);
    }
    
    public Pessoa validaLoginSenha(String email, String senha)
    {
        try {
            senha = Util.criptoMD5(senha);
            
            SQLQuery query = query("SELECT * FROM Pessoa WHERE Email = :email AND Senha = :senha AND Ativo = 1").addEntity(Pessoa.class);
            query.setString("email", email);
            query.setString("senha", senha);
            
            return (Pessoa)query.list().get(0);
        } catch (Exception e) {
            return null;
        }
    }
    
    public Pessoa validaLogin(String email)
    {
        try {
            SQLQuery query = query("SELECT * FROM Pessoa WHERE Email = :email AND Ativo = 1").addEntity(Pessoa.class);
            query.setString("email", email);
            
            return (Pessoa)query.list().get(0);
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean enviarTrocaSenha(Pessoa pessoa)
    {
        return false;
    }
    
    public boolean enviarConvite(Pessoa aluno)
    {
        String htmlEmailComSenha = "<!DOCTYPE html>\n" +
                                    "<html>\n" +
                                    "<head>\n" +
                                    "	<style>\n" +
                                    "		body { \n" +
                                    "			background-color: #eeeeee;\n" +
                                    "			margin: 40px;\n" +
                                    "			font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif;\n" +
                                    "			font-size: 14px;\n" +
                                    "			color: #333333;\n" +
                                    "			font-weight: 300;\n" +
                                    "			line-height: 1.25;\n" +
                                    "		}\n" +
                                    "		.logo {\n" +
                                    "			padding: 15px;\n" +
                                    "			background-color: #ffffff;\n" +
                                    "			border: 1px solid #428BCA;\n" +
                                    "			width: 250px;\n" +
                                    "		}\n" +
                                    "	</style>\n" +
                                    "</head>\n" +
                                    "<body>\n" +
                                    "	<div class=\"logo\">\n" +
                                    "		<img src=\"http://feeper.jelasticlw.com.br/resources/img/logo_p.png\" style=\"margin-left: 80px; margin-right: 80px;\">\n" +
                                    "	</div>\n" +
                                    "	<p>Olá #NOME#,</p>\n" +
                                    "	<p>Você foi convidado pelo seu professor a utilizar o <i>feeper</i> como uma ferramenta para resolução de exercícios disponibilizados à sua turma.</p>\n" +
                                    "	<p>\n" +
                                    "		Utilize estes dados para acessá-lo:<br>\n" +
                                    "		Endereço: <a href=\"http://feeper.jelasticlw.com.br\">http://feeper.jelasticlw.com.br</a><br>\n" +
                                    "		Login: #EMAIL#<br>\n" +
                                    "		Senha: #SENHA#<br>\n" +
                                    "	</p>\n" +
                                    "	<p>Atenciosamente,<br>Equipe <i>feeper</i></p>\n" +
                                    "	\n" +
                                    "</body>\n" +
                                    "</html>";
        String htmlEmailSemSenha = "<!DOCTYPE html>\n" +
                                    "<html>\n" +
                                    "<head>\n" +
                                    "	<style>\n" +
                                    "		body { \n" +
                                    "			background-color: #eeeeee;\n" +
                                    "			margin: 40px;\n" +
                                    "			font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif;\n" +
                                    "			font-size: 14px;\n" +
                                    "			color: #333333;\n" +
                                    "			font-weight: 300;\n" +
                                    "			line-height: 1.25;\n" +
                                    "		}\n" +
                                    "		.logo {\n" +
                                    "			padding: 15px;\n" +
                                    "			background-color: #ffffff;\n" +
                                    "			border: 1px solid #428BCA;\n" +
                                    "			width: 250px;\n" +
                                    "		}\n" +
                                    "	</style>\n" +
                                    "</head>\n" +
                                    "<body>\n" +
                                    "	<div class=\"logo\">\n" +
                                    "		<img src=\"http://feeper.jelasticlw.com.br/resources/img/logo_p.png\" style=\"margin-left: 80px; margin-right: 80px;\">\n" +
                                    "	</div>\n" +
                                    "	<p>Olá #NOME#,</p>\n" +
                                    "	<p>Você foi convidado pelo seu professor a utilizar o <i>feeper</i> como uma ferramenta para resolução de exercícios disponibilizados à sua turma.</p>\n" +
                                    "	<p>\n" +
                                    "		Utilize estes dados para acessá-lo:<br>\n" +
                                    "		Endereço: <a href=\"http://feeper.jelasticlw.com.br\">http://feeper.jelasticlw.com.br</a><br>\n" +
                                    "		Login: #EMAIL#<br><br>\n" +
                                    "		<i>* Você já tem uma senha cadastrada. Caso você não recorde sua senha utilize este link para lembrar a senha: <a href=\"http://feeper.jelasticlw.com.br/login/esqueciminhasenha\">http://feeper.jelasticlw.com.br/login/esqueciminhasenha</a></i><br>\n" +
                                    "	</p>\n" +
                                    "	<p>Atenciosamente,<br>Equipe <i>feeper</i></p>\n" +
                                    "	\n" +
                                    "</body>\n" +
                                    "</html>";
        
        if (aluno.getSenha().isEmpty())
        {
            String novaSenha = Util.gerarSenha(8);
            String novaSenhaCripto = Util.criptoMD5(novaSenha);
            aluno.setSenha(novaSenhaCripto);
            if (update(aluno))
            {
                htmlEmailComSenha = htmlEmailComSenha.replaceAll("#NOME#", aluno.getNome());
                htmlEmailComSenha = htmlEmailComSenha.replaceAll("#EMAIL#", aluno.getEmail());
                htmlEmailComSenha = htmlEmailComSenha.replaceAll("#SENHA#", novaSenha);
                //Util.sendMail(aluno.getEmail(), "Bem vindo ao feeper!", htmlEmailComSenha);
            }
        }
        else
        {
            htmlEmailSemSenha = htmlEmailSemSenha.replaceAll("#NOME#", aluno.getNome());
            htmlEmailSemSenha = htmlEmailSemSenha.replaceAll("#EMAIL#", aluno.getEmail());
            //Util.sendMail(aluno.getEmail(), "Bem vindo ao feeper!", htmlEmailSemSenha);
        }
    }
    
}
