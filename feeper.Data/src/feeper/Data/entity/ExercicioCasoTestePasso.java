/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.entity;

/**
 *
 * @author gilvani
 */
public class ExercicioCasoTestePasso {

    private Integer id;
    private Integer idCasoTeste;
    private String ObjectType;
    private String ObjectName;
    private String MethodName;
    private String ExpectedOutputType;
    private String ExpectedOutputName;
    private String ExpectedOutputValue;
    private ExercicioCasoTestePassoParametro[] inputParameters;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCasoTeste() {
        return idCasoTeste;
    }

    public void setIdCasoTeste(Integer idCasoTeste) {
        this.idCasoTeste = idCasoTeste;
    }

    public String getObjectType() {
        return ObjectType;
    }

    public void setObjectType(String ObjectType) {
        this.ObjectType = ObjectType;
    }

    public String getObjectName() {
        return ObjectName;
    }

    public void setObjectName(String ObjectName) {
        this.ObjectName = ObjectName;
    }

    public String getMethodName() {
        return MethodName;
    }

    public void setMethodName(String MethodName) {
        this.MethodName = MethodName;
    }

    public String getExpectedOutputType() {
        return ExpectedOutputType;
    }

    public void setExpectedOutputType(String ExpectedOutputType) {
        this.ExpectedOutputType = ExpectedOutputType;
    }

    public String getExpectedOutputName() {
        return ExpectedOutputName;
    }

    public void setExpectedOutputName(String ExpectedOutputName) {
        this.ExpectedOutputName = ExpectedOutputName;
    }

    public String getExpectedOutputValue() {
        return ExpectedOutputValue;
    }

    public void setExpectedOutputValue(String ExpectedOutputValue) {
        this.ExpectedOutputValue = ExpectedOutputValue;
    }

    public ExercicioCasoTestePassoParametro[] getInputParameters() {
        return inputParameters;
    }

    public void setInputParameters(ExercicioCasoTestePassoParametro[] inputParameters) {
        this.inputParameters = inputParameters;
    }
}
