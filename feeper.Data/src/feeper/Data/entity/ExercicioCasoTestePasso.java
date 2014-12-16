/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.entity;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author gilvani
 */
public class ExercicioCasoTestePasso {

    @XmlAttribute
    public Integer id;
    @XmlAttribute
    public Integer idCasoTeste;
    @XmlAttribute
    public String ObjectType;
    @XmlAttribute
    public String ObjectName;
    @XmlAttribute
    public String MethodName;
    @XmlAttribute
    public String ExpectedOutputType;
    @XmlAttribute
    public String ExpectedOutputName;
    @XmlAttribute
    public String ExpectedOutputValue;
    
    public ExercicioCasoTestePassoParametro[] inputParameters;
}
