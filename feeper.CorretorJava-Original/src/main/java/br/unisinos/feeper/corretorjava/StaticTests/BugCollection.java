/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisinos.feeper.corretorjava.StaticTests;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gilvani
 */
@XmlRootElement(name = "BugCollection")
public class BugCollection {

    public ClassResult[] file;
}
