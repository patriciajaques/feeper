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
            case "ES_COMPARING_STRINGS_WITH_EQ":
            case "ES_COMPARING_PARAMETER_STRING_WITH_EQ":
            case "NP_BOOLEAN_RETURN_NULL":
            case "IC_SUPERCLASS_USES_SUBCLASS_DURING_INITIALIZATION":
            case "NP_CLONE_COULD_RETURN_NULL":
            case "NP_EQUALS_SHOULD_HANDLE_NULL_ARGUMENT":
            case "NP_TOSTRING_COULD_RETURN_NULL":
            case "NM_CLASS_NAMING_CONVENTION":
            case "NM_FIELD_NAMING_CONVENTION":
            case "NM_METHOD_NAMING_CONVENTION":
            case "OS_OPEN_STREAM":
            case "OS_OPEN_STREAM_EXCEPTION_PATH":
            case "RC_REF_COMPARISON_BAD_PRACTICE":
            case "RC_REF_COMPARISON_BAD_PRACTICE_BOOLEAN":
            case "BC_IMPOSSIBLE_CAST":
            case "BC_IMPOSSIBLE_DOWNCAST":
            case "BC_IMPOSSIBLE_DOWNCAST_OF_TOARRAY":
            case "EC_ARRAY_AND_NONARRAY":
            case "EC_BAD_ARRAY_COMPARE":
            case "EQ_ALWAYS_FALSE":
            case "EQ_ALWAYS_TRUE":
            case "IL_CONTAINER_ADDED_TO_ITSELF":
            case "IL_INFINITE_LOOP":
            case "IL_INFINITE_RECURSIVE_LOOP":
            case "NM_LCASE_TOSTRING":
            case "SA_FIELD_SELF_ASSIGNMENT":
            case "SA_FIELD_SELF_COMPARISON":
            case "SA_LOCAL_SELF_ASSIGNMENT_INSTEAD_OF_FIELD":
            case "SA_LOCAL_SELF_COMPARISON":
            case "SP_SPIN_ON_FIELD":
            case "UPM_UNCALLED_PRIVATE_METHOD":
            case "URF_UNREAD_FIELD":
            case "UUF_UNUSED_FIELD":
            case "DMI_HARDCODED_ABSOLUTE_FILENAME":
            case "RV_RETURN_VALUE_IGNORED_INFERRED":
            case "SA_LOCAL_SELF_ASSIGNMENT":
            case "SF_SWITCH_FALLTHROUGH":
                return true;
        }

        return false;
    }

    public void translateBug(BugInstance bug) {
        switch (bug.type) {

            case "ES_COMPARING_STRINGS_WITH_EQ":
                bug.message = bug.message.replace("ES: Comparison of String objects using == or != in", "Foi localizada uma compara&ccedil;&atilde;o de Strings com == ou != em") + "; Este tipo de opera&ccedil;&atilde;o pode causar um comportamento inesperado em sua solu&ccedil;&atilde;o";
                break;
            case "NM_METHOD_NAMING_CONVENTION":
                if (bug.message.contains("doesn&apos;t start with a lower case letter")) {
                    bug.message = bug.message.replace("Nm: The method name", "O nome do m&eacute;todo");
                    bug.message = bug.message.replace("doesn&apos;t start with a lower case letter", "deve come&ccedil;ar com letra min&uacute;scula");
                } else if (bug.message.contains("doesn&apos;t start with a upper case letter")) {
                    bug.message = bug.message.replace("Nm: The method name", "O nome do m&eacute;todo");
                    bug.message = bug.message.replace("doesn&apos;t start with a upper case letter", "deve come&ccedil;ar com letra mai&uacute;scula");
                }
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
            case "UUF_UNUSED_FIELD":
                bug.message = bug.message.replace("UuF: Unused field", "A seguinte propriedade foi declarada mas nunca foi utilizada");
                break;
            case "SA_LOCAL_SELF_ASSIGNMENT":
                String firstPart = "SA: Self assignment of ";
                String variableName = bug.message.substring(firstPart.length(), bug.message.indexOf(" ", firstPart.length()));

                if (variableName.startsWith("$")) {
                    bug.message = "Uma variável está recebendo seu próprio valor em " + bug.message.substring(firstPart.length() + variableName.length() + 3);
                } else {
                    bug.message = bug.message.replace("SA: Self assignment of ", "A vari&aacute;vel ");
                    bug.message = bug.message.replace(" in ", " está recebendo seu próprio valor em ");
                }
                bug.message += "; Este tipo de opera&ccedil;&atilde;o geralmente representa um erro de l&oacute;gica";
                break;
        }
    }

    //Nm: The method name Aluno.Teste() doesn't start with a lower case letter
}
