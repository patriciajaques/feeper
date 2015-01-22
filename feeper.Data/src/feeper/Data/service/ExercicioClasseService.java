/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioClasseMarcacao;
import feeper.Data.entity.ExercicioClasse;
import feeper.Data.entity.Mensagem;
import feeper.Data.entity.MensagemCabecalho;
import feeper.Data.entity.MensagemLeitor;
import feeper.Data.entity.Pessoa;
import feeper.Data.model.ETipoLeitor;
import feeper.Data.model.ETipoMarcacao;
import feeper.Data.model.HibernateUtil;
import feeper.Data.model.Util;
import java.util.Date;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;

/**
 *
 * @author gilvani
 */
public class ExercicioClasseService extends HibernateUtil<ExercicioClasse> {

    public ExercicioClasseService() {
        super(ExercicioClasse.class);
    }

    public List<ExercicioClasse> getAllByIdExercicio(int idExercicio, int idAluno) {
        try {

            SQLQuery query = query("select * from ExercicioClasse where IdExercicio = :idExercicio and IdAluno = :idAluno").addEntity(ExercicioClasse.class);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAluno", idAluno);
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }
}
