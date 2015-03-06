/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.FilaNovaSenha;
import feeper.Data.entity.Pessoa;
import feeper.Data.model.HibernateUtil;
import feeper.Data.model.Util;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author fabioalves
 */
public class FilaNovaSenhaService extends HibernateUtil<FilaNovaSenha> {

    public FilaNovaSenhaService() {
        super(FilaNovaSenha.class);
    }

    public FilaNovaSenha getByChave(String chave) {

        List<FilaNovaSenha> data = getByColumn("Chave", chave);
        if (data.isEmpty()) {
            return null;

        } else {
            return data.get(0);
        }
    }

    public UUID insert(int idPessoa) {
        deleteByIdPessoa(idPessoa);

        UUID chave = UUID.randomUUID();

        FilaNovaSenha registro = new FilaNovaSenha();
        registro.setChave(chave.toString());
        registro.setDataCadastro(new Date());
        registro.setIdPessoa(idPessoa);
        if (insert(registro)) {
            return chave;
        }
        return null;
    }

    public void deleteByIdPessoa(int idPessoa) {

        List<FilaNovaSenha> lista = getByColumn("IdPessoa", idPessoa);
        for (FilaNovaSenha registro : lista) {
            delete(registro);
        }

    }

    public void deleteByChave(String chave) {

        List<FilaNovaSenha> data = getByColumn("Chave", chave);
        if (data.isEmpty()) {
            return;

        }
        delete(data.get(0));

    }

    public boolean validaPrazoChave(String chave) {
        if (chave.isEmpty()) {
            return false;
        }

        FilaNovaSenha registro = getByChave(chave);
        int prazoChave = 24;
        Date dataFila = registro.getDataCadastro();

        Calendar cal = Calendar.getInstance();
        cal.setTime(dataFila);
        cal.add(Calendar.HOUR, prazoChave);

        Date dataLimite = cal.getTime();
        Date dataAtual = new Date();

        int result = dataAtual.compareTo(dataLimite);
        return result > 0;
    }

    public boolean enviaEmailChave(int idPessoa) {
        try {

            PessoaService repoPessoa = new PessoaService();
            Pessoa pessoa = repoPessoa.getById(idPessoa);

            String linkChave = Util.serverUrl + "/senha/validarchave/" + insert(idPessoa);
            String link = "<a href=\"#LINK#\">#LINK#</a>".replace("#LINK#", linkChave);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, 24);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            StringBuilder html = new StringBuilder();
            html.append("<p>Prezado ").append(pessoa.getNome()).append(",</p><p>Você solicitou a troca de senha no <i>feeper</i>. O sistema gerou uma chave para realizar a troca da sua senha, veja os dados abaixo:</p><p>Validade: ");
            html.append(dateFormat.format(cal.getTime())).append("<br>Chave: ").append(link);

            return Util.sendMail(pessoa.getEmail(), "Troca de Senha", html.toString());

        } catch (Exception e) {
            return false;
        }
    }

}
