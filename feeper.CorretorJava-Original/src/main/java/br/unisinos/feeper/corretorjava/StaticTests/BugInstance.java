/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisinos.feeper.corretorjava.StaticTests;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author gilvani
 */
public class BugInstance {
    
    @XmlAttribute
    public String type;
    @XmlAttribute
    public String priority;
    @XmlAttribute
    public String message;
    @XmlAttribute
    public int line;
}
