/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisinos.feeper.corretorjava.StaticTests;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gilvani
 */
public class Filter_Translator {

    public List<BugInstance> FilterAndTranslate(ClassResult source) {

        List<BugInstance> bugs = new ArrayList<>();

        for (BugInstance bug : source.BugInstance) {

            if (this.isBugShowed(bug)) {
                this.translateBug(bug);
                bugs.add(bug);
            }
        }

        return bugs;
    }

    public boolean isBugShowed(BugInstance bug) {

        switch (bug.type) {
            case "UUF_UNUSED_FIELD":
                return true;
            case "ES_COMPARING_STRINGS_WITH_EQ":
                return true;
            case "NM_FIELD_NAMING_CONVENTION":
                return true;
        }

        return false;
    }

    public void translateBug(BugInstance bug) {
        switch (bug.type) {
            case "UUF_UNUSED_FIELD":
                bug.message = bug.message.replace("UuF: Unused field", "A seguinte propriedade foi declarada mas nunca foi utilizada");
                break;
            case "ES_COMPARING_STRINGS_WITH_EQ":
                bug.message = bug.message.replace("ES: Comparison of String objects using == or != in", "Foi localizada uma compara&ccedil;&atilde;o de Strings com == ou != em") + "; Este tipo de opera&ccedil;&atilde;o pode causar um comportamento inesperado em sua solu&ccedil;&atilde;o";
                break;
            case "NM_FIELD_NAMING_CONVENTION":
                if (bug.message.contains("doesn&apos;t start with a lower case letter")) {
                    bug.message = bug.message.replace("Nm: The field name", "O nome da propriedade");
                    bug.message = bug.message.replace("doesn&apos;t start with a lower case letter", "deve come&ccedil;ar com letra min&uacute;scula");
                } else if (bug.message.contains("doesn&apos;t start with a upper case letter")) {
                    bug.message = bug.message.replace("Nm: The field name", "O nome da propriedade");
                    bug.message = bug.message.replace("doesn&apos;t start with a upper case letter", "deve come&ccedil;ar com letra mai&uacute;scula");
                }
                break;
        }
    }
}
