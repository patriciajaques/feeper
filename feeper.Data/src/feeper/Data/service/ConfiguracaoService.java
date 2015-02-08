/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ConfiguracaoSistema;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.SQLQuery;

/**
 *
 * @author gilvani
 */
public class ConfiguracaoService extends HibernateUtil<ConfiguracaoSistema> {

    public ConfiguracaoService() {
        super(ConfiguracaoSistema.class);
    }

    public ConfiguracaoSistema getConfiguracao() {
        
        SQLQuery query = query("select * from ConfiguracaoSistema order by ID desc limit 1").addEntity(ConfiguracaoSistema.class);

        List<ConfiguracaoSistema> data = query.list();

        if (data.isEmpty()) {
            ConfiguracaoSistema configuracao = new ConfiguracaoSistema();
            return configuracao;
        } else {
            return data.get(0);
        }
    }

    public void saveConfiguracao(ConfiguracaoSistema configuracao) {

        Integer configuracaoId = configuracao.getId();
        if (configuracaoId != null && configuracaoId > 0) {
            this.update(configuracao);
        } else {
            this.insert(configuracao);
        }
    }
}
