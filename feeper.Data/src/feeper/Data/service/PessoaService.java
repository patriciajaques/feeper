/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.Pessoa;
import feeper.Data.model.HibernateUtil;
import feeper.Data.model.StringResult;
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
        String htmlEmailComSenha = "<p>Olá #NOME#,</p>\n" +
                                    "<p>Você foi convidado pelo seu professor a utilizar o <i>feeper</i> como uma ferramenta para resolução de exercícios disponibilizados à sua turma.</p>\n" +
                                    "<p>\n" +
                                    "Utilize estes dados para acessá-lo:<br>\n" +
                                    "Endereço: <a href=\"http://feeper.jelasticlw.com.br\">http://feeper.jelasticlw.com.br</a><br>\n" +
                                    "Login: #EMAIL#<br>\n" +
                                    "Senha: #SENHA#<br>\n" +
                                    "</p>";
        String htmlEmailSemSenha = "<p>Olá #NOME#,</p>\n" +
                                    "<p>Você foi convidado pelo seu professor a utilizar o <i>feeper</i> como uma ferramenta para resolução de exercícios disponibilizados à sua turma.</p>\n" +
                                    "<p>\n" +
                                    "Utilize estes dados para acessá-lo:<br>\n" +
                                    "Endereço: <a href=\"http://feeper.jelasticlw.com.br\">http://feeper.jelasticlw.com.br</a><br>\n" +
                                    "Login: #EMAIL#<br><br>\n" +
                                    "<i>* Você já tem uma senha cadastrada. Caso você não recorde sua senha utilize este link para lembrar a senha:<br><a href=\"http://feeper.jelasticlw.com.br/senha/esqueciminhasenha\">http://feeper.jelasticlw.com.br/senha/esqueciminhasenha</a></i><br>\n" +
                                    "</p>";
        
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
                return Util.sendMail(aluno.getEmail(), "Bem vindo ao feeper!", htmlEmailComSenha);
                //return Util.sendMail("Bem vindo ao feeper!", htmlEmailComSenha);
            }
        }
        else
        {
            htmlEmailSemSenha = htmlEmailSemSenha.replaceAll("#NOME#", aluno.getNome());
            htmlEmailSemSenha = htmlEmailSemSenha.replaceAll("#EMAIL#", aluno.getEmail());
            return Util.sendMail(aluno.getEmail(), "Bem vindo ao feeper!", htmlEmailSemSenha);
            //return Util.sendMail("Bem vindo ao feeper!", htmlEmailSemSenha);
        }
        return true;
    }
    
    public boolean validaSenha(String senha, String repeteSenha, StringResult msgSaida)
    {
        //Caso tenha alguma validação na senha, adicionaremos aqui neste método
        if (!senha.equals(repeteSenha))
        {
            msgSaida.setResult("As senhas informadas não conferem.");
            return false;
        }
        if (senha.length() > 10 || senha.length() < 6)
        {
            msgSaida.setResult("O tamanho da senha é inválido. Deve conter de 6 a 10 caracteres.");
            return false;
        }
        if (!Util.validarFormatoSenha(senha))
        {
            msgSaida.setResult("O formato da senha é inválido. Deve conter pelo menos uma letra maiúscula, pelo menos uma letra minúscula e pelo menos um número.");
            return false;
        }

        return true;
    }
    
}
