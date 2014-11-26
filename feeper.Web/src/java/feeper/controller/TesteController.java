/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import feeper.Data.service.PessoaService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author gilvani
 */
@Controller
@RequestMapping(value = "/teste")
public class TesteController extends ApplicationController {

    public TesteController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {

        try {
            JSONObject jsonObject = new JSONObject().put("Json", "Funcionou este bagulho? Ou não?");
            System.out.println(jsonObject);

            URL url = new URL("http://localhost:8084/feeper.CorretorJava/rest/efetuaCorrecao");
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(jsonObject.toString());
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while (in.readLine() != null) {
            }

            System.out.println("\nREST Service Invoked Successfully..");
            in.close();
        } catch (Exception e) {
            System.out.println("\nError while calling REST Service");
            System.out.println(e);
        }

        return "ok";
    }
}
