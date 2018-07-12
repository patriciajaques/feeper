/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisinos.feeper.feeper.CorretorJava.StaticTests;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author gilvani
 */
public class ClassResult {

    @XmlAttribute
    public String classname;
    
    public BugInstance[] BugInstance;
}
