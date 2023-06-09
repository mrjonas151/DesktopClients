/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import model.AnimesModel;
import model.FilmesModel;
import model.SeriesModel;

/**
 *
 * @author jonas
 */
public class Control {
    
    
    static ControlArquivoTexto controller = new ControlArquivoTexto();
    
    public static boolean cadastrarSerie(String id, String nome, String idioma, String genero, int temporadas, String diretor, String plataforma, double avaliacao){
        SeriesModel novaSerie = new SeriesModel();
        
        novaSerie.setId(id);
        novaSerie.setNome(nome);
        novaSerie.setIdioma(idioma);
        novaSerie.setGenero(genero);
        novaSerie.setTemporadas(temporadas);
        novaSerie.setDiretor(diretor);
        novaSerie.setPlataforma(plataforma);
        novaSerie.setAvaliacao(avaliacao);
        
        
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ");
        sb.append(novaSerie.getId());
        sb.append("\nNOME: ");
        sb.append(novaSerie.getNome());
        sb.append("\nIDIOMA: ");
        sb.append(novaSerie.getIdioma());
        sb.append("\nGENERO: ");
        sb.append(novaSerie.getGenero());
        sb.append("\nTEMPORADAS: ");
        sb.append(novaSerie.getTemporadas());
        sb.append("\nDIRETOR: ");
        sb.append(novaSerie.getDiretor());
        sb.append("\nPLATAFORMA: ");
        sb.append(novaSerie.getPlataforma());
        sb.append("\nAVALIACAO: ");
        sb.append(novaSerie.getAvaliacao());
        sb.append("\n\n");
        
        ControlArquivoTexto arquivo = new ControlArquivoTexto();
        arquivo.setArquivo("Salvar");
        arquivo.setTexto(sb.toString());
        
        if(arquivo.escrever(true)){
            return true;
        }else{
            return false;
        }  
    }
    
    public static boolean removerSerie(int id){
        String conteudo = "Sem conteudo";
        
        String caminho = lerArquivo2();
        
        try {
            conteudo = Files.readString(Paths.get(caminho));
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
        }
        
        //String series = lerArquivo();
        String[] records = conteudo.split("\n\n");
        
        List<Map<String, String>> listaSeries = new ArrayList<>();
        
        
        for (String record : records) {
            Map<String, String> map = new HashMap<>();
            String[] linhas = record.split("\n");
            for (String linha : linhas) {
                String[] parts = linha.split(": ");
                String key = parts[0];
                String value = parts[1];
                map.put(key, value);
            }
            listaSeries.add(map);
        }
        int cont = 0;
        Iterator<Map<String, String>> iterador = listaSeries.iterator();
        while (iterador.hasNext()) {
            Map<String, String> registro = iterador.next();
            String idcomparador = registro.get("ID");
            if (idcomparador.equals(String.valueOf(id))) {
                iterador.remove(); //Remove o elemento usando o Iterator
                cont++;
            }
        }
        String resultado;
        StringBuilder rb = new StringBuilder();
        for (Map<String, String> record : listaSeries) {
            rb.append("ID: ");
            rb.append(record.get("ID"));
            rb.append("\n");
            rb.append("NOME: ");
            rb.append(record.get("NOME"));
            rb.append("\n");
            rb.append("DIRETOR: ");
            rb.append(record.get("DIRETOR"));
            rb.append("\n");
            rb.append("AVALIACAO: ");
            rb.append(record.get("AVALIACAO"));
            rb.append("\n");
            rb.append("TEMPORADAS: ");
            rb.append(record.get("TEMPORADAS"));
            rb.append("\n");
            rb.append("IDIOMA: ");
            rb.append(record.get("IDIOMA"));
            rb.append("\n");
            rb.append("GENERO: ");
            rb.append(record.get("GENERO"));
            rb.append("\n");
            rb.append("PLATAFORMA: ");
            rb.append(record.get("PLATAFORMA"));
            rb.append("\n");
            rb.append("\n");
        }
        resultado = rb.toString();
        
        try {
            FileOutputStream outputStream = new FileOutputStream(caminho);
            outputStream.write(resultado.getBytes());
            outputStream.close();
            System.out.println("Novos dados salvos com sucesso em cima do arquivo existente.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao salvar novos dados em cima do arquivo existente: " + e.getMessage());
        }
            
        if(cont!=0){
            return true;
        }else{
            return false;
        }
    }
    
    public static boolean atualizarSerie(int id, String nome, String idioma, String genero, int temporadas, String diretor, String plataforma, double avaliacao){
        String conteudo = "Sem conteudo";
        
        String caminho = lerArquivo2();
        
        try {
            conteudo = Files.readString(Paths.get(caminho));
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
        }
        
        //String series = lerArquivo();
        String[] records = conteudo.split("\n\n");
        
        List<Map<String, String>> listaSeries = new ArrayList<>();
        Map<String, String> novoRegistro = new HashMap<>();
        
        for (String record : records) {
            Map<String, String> map = new HashMap<>();
            String[] linhas = record.split("\n");
            for (String linha : linhas) {
                String[] parts = linha.split(": ");
                String key = parts[0];
                String value = parts[1];
                map.put(key, value);
            }
            listaSeries.add(map);
        }
        SeriesModel novaSerie = new SeriesModel();
        
        Iterator<Map<String, String>> iterador = listaSeries.iterator();
        while (iterador.hasNext()) {
            Map<String, String> registro = iterador.next();
            String idcomparador = registro.get("ID");
            if (idcomparador.equals(String.valueOf(id))) {
                iterador.remove();
                novaSerie.setId(String.valueOf(id));
                novaSerie.setNome(nome);
                novaSerie.setAvaliacao(avaliacao);
                novaSerie.setDiretor(diretor);
                novaSerie.setGenero(genero);
                novaSerie.setPlataforma(plataforma);
                novaSerie.setIdioma(idioma);
                novaSerie.setTemporadas(temporadas);
            }
        }
        
        if(novaSerie.getId() != null){
            novoRegistro.put("ID", Integer.toString(id));
            novoRegistro.put("NOME", novaSerie.getNome());
            novoRegistro.put("DIRETOR", novaSerie.getDiretor());
            novoRegistro.put("AVALIACAO", Double.toString(novaSerie.getAvaliacao()));
            novoRegistro.put("TEMPORADAS", Integer.toString(novaSerie.getTemporadas()));
            novoRegistro.put("IDIOMA", novaSerie.getIdioma());
            novoRegistro.put("GENERO", novaSerie.getGenero());
            novoRegistro.put("PLATAFORMA", novaSerie.getPlataforma());
            listaSeries.add(novoRegistro);
        
            String resultado;
            StringBuilder rb = new StringBuilder();
            for (Map<String, String> record : listaSeries) {
                rb.append("ID: ");
                rb.append(record.get("ID"));
                rb.append("\n");
                rb.append("NOME: ");
                rb.append(record.get("NOME"));
                rb.append("\n");
                rb.append("DIRETOR: ");
                rb.append(record.get("DIRETOR"));
                rb.append("\n");
                rb.append("AVALIACAO: ");
                rb.append(record.get("AVALIACAO"));
                rb.append("\n");
                rb.append("TEMPORADAS: ");
                rb.append(record.get("TEMPORADAS"));
                rb.append("\n");
                rb.append("IDIOMA: ");
                rb.append(record.get("IDIOMA"));
                rb.append("\n");
                rb.append("GENERO: ");
                rb.append(record.get("GENERO"));
                rb.append("\n");
                rb.append("PLATAFORMA: ");
                rb.append(record.get("PLATAFORMA"));
                rb.append("\n");
                rb.append("\n");
            }
            resultado = rb.toString();

        try {
            FileOutputStream outputStream = new FileOutputStream(caminho);
            outputStream.write(resultado.getBytes());
            outputStream.close();
            System.out.println("Novos dados salvos com sucesso em cima do arquivo existente.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao salvar novos dados em cima do arquivo existente: " + e.getMessage());
        }
            
            return true;
        }else{
            return false;
        }
    }
    
    public static String lerSeriePorId(int id ){
        String conteudo = "Sem conteudo";
        
        String caminho = lerArquivo2();
        
        try {
            conteudo = Files.readString(Paths.get(caminho));
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
        }
        
        //String series = lerArquivo();
        String[] records = conteudo.split("\n\n");
        
        List<Map<String, String>> listaSeries = new ArrayList<>();
        
        for (String record : records) {
            Map<String, String> map = new HashMap<>();
            String[] linhas = record.split("\n");
            for (String linha : linhas) {
                String[] parts = linha.split(": ");
                String key = parts[0];
                String value = parts[1];
                map.put(key, value);
            }
            listaSeries.add(map);
        }
        for (Map<String, String> record : listaSeries) {
            System.out.println(record);
            String idcomparador = record.get("ID");
            if (idcomparador.equals(String.valueOf(id))) {
                System.out.println("id encontrado!!");
                StringBuilder sb = new StringBuilder();
                sb.append("ID: ");
                sb.append(record.get("ID"));
                sb.append("\n");
                sb.append("NOME: ");
                sb.append(record.get("NOME"));
                sb.append("\n");
                sb.append("DIRETOR: ");
                sb.append(record.get("DIRETOR"));
                sb.append("\n");
                sb.append("AVALIACAO: ");
                sb.append(record.get("AVALIACAO"));
                sb.append("\n");
                sb.append("TEMPORADAS: ");
                sb.append(record.get("TEMPORADAS"));
                sb.append("\n");
                sb.append("IDIOMA: ");
                sb.append(record.get("IDIOMA"));
                sb.append("\n");
                sb.append("GENERO: ");
                sb.append(record.get("GENERO"));
                sb.append("\n");
                sb.append("PLATAFORMA: ");
                sb.append(record.get("PLATAFORMA"));
                sb.append("\n");
                sb.append("\n");
                String resultado = sb.toString();
                System.out.println(resultado);
                return resultado;
            }
        }
        
        
       return "ID nao encontrado!!";
    }
    
    public static SeriesModel lerSeriePorId2(int id ){
        SeriesModel serie = new SeriesModel();
        
        String conteudo = "Sem conteudo";
        
        String caminho = lerArquivo2();
        
        try {
            conteudo = Files.readString(Paths.get(caminho));
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
        }
        
        //String series = lerArquivo();
        String[] records = conteudo.split("\n\n");
        
        List<Map<String, String>> listaSeries = new ArrayList<>();
        
        for (String record : records) {
            Map<String, String> map = new HashMap<>();
            String[] linhas = record.split("\n");
            for (String linha : linhas) {
                String[] parts = linha.split(": ");
                String key = parts[0];
                String value = parts[1];
                map.put(key, value);
            }
            listaSeries.add(map);
        }
        for (Map<String, String> record : listaSeries) {
            
            System.out.println(record);
            String idcomparador = record.get("ID");
            if (idcomparador.equals(String.valueOf(id))) {
                serie.setId(record.get("ID"));
                serie.setNome(record.get("NOME"));
                serie.setAvaliacao(Double.parseDouble(record.get("AVALIACAO")));
                serie.setDiretor(record.get("DIRETOR"));
                serie.setGenero(record.get("GENERO"));
                serie.setIdioma(record.get("IDIOMA"));
                serie.setPlataforma(record.get("PLATAFORMA"));
                serie.setTemporadas(Integer.parseInt(record.get(("TEMPORADAS"))));
                        
                return serie;
            }
        }
        
        
       return null;
    }
    
    public static boolean cadastrarFilme(String id, String nome, int anoLancamento, int duracao){
        FilmesModel novoFilme = new FilmesModel();
        
        novoFilme.setId(id);
        novoFilme.setNome(nome);
        novoFilme.setDuracao(duracao);
        novoFilme.setAnoLancamento(anoLancamento);
        
        
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ");
        sb.append(novoFilme.getId());
        sb.append("\nNOME: ");
        sb.append(novoFilme.getNome());
        sb.append("\nDURACAO: ");
        sb.append(novoFilme.getDuracao());
        sb.append("\nANO LANCAMENTO: ");
        sb.append(novoFilme.getAnoLancamento());
        sb.append("\n\n");
        
        ControlArquivoTexto arquivo = new ControlArquivoTexto();
        arquivo.setArquivo("Salvar");
        arquivo.setTexto(sb.toString());
        
        if(arquivo.escrever(true)){
            return true;
        }else{
            return false;
        }  
    }
    
    public static boolean removerFilme(int id){
        String conteudo = "Sem conteudo";
        
        String caminho = lerArquivo2();
        
        try {
            conteudo = Files.readString(Paths.get(caminho));
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
        }
        
        //String filmes = lerArquivo();
        String[] records = conteudo.split("\n\n");
        
        List<Map<String, String>> listaFilmes = new ArrayList<>();
        
        
        for (String record : records) {
            Map<String, String> map = new HashMap<>();
            String[] linhas = record.split("\n");
            for (String linha : linhas) {
                String[] parts = linha.split(": ");
                String key = parts[0];
                String value = parts[1];
                map.put(key, value);
            }
            listaFilmes.add(map);
        }
        int cont = 0;
        Iterator<Map<String, String>> iterador = listaFilmes.iterator();
        while (iterador.hasNext()) {
            Map<String, String> registro = iterador.next();
            String idcomparador = registro.get("ID");
            if (idcomparador.equals(String.valueOf(id))) {
                iterador.remove(); 
                cont++;
            }
        }
        String resultado;
        StringBuilder rb = new StringBuilder();
        for (Map<String, String> record : listaFilmes) {
            rb.append("ID: ");
            rb.append(record.get("ID"));
            rb.append("\n");
            rb.append("NOME: ");
            rb.append(record.get("NOME"));
            rb.append("\n");
            rb.append("ANO LANCAMENTO: ");
            rb.append(record.get("ANO LANCAMENTO"));
            rb.append("\n");
            rb.append("DURACAO: ");
            rb.append(record.get("DURACAO"));
            rb.append("\n");
            rb.append("\n");
        }
        resultado = rb.toString();
        
        try {
            FileOutputStream outputStream = new FileOutputStream(caminho);
            outputStream.write(resultado.getBytes());
            outputStream.close();
            System.out.println("Novos dados salvos com sucesso em cima do arquivo existente.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao salvar novos dados em cima do arquivo existente: " + e.getMessage());
        }
            
        if(cont!=0){
            return true;
        }else{
            return false;
        }
    }
    
    public static boolean atualizarFilme(String id, String nome, int anoLancamento, int duracao){
        String conteudo = "Sem conteudo";
        
        String caminho = lerArquivo2();
        
        try {
            conteudo = Files.readString(Paths.get(caminho));
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
        }
        
        //String filmes = lerArquivo();
        String[] records = conteudo.split("\n\n");
        
        List<Map<String, String>> listaFilmes = new ArrayList<>();
        Map<String, String> novoRegistro = new HashMap<>();
        
        for (String record : records) {
            Map<String, String> map = new HashMap<>();
            String[] linhas = record.split("\n");
            for (String linha : linhas) {
                String[] parts = linha.split(": ");
                String key = parts[0];
                String value = parts[1];
                map.put(key, value);
            }
            listaFilmes.add(map);
        }
        FilmesModel novoFilme = new FilmesModel();
        
        Iterator<Map<String, String>> iterador = listaFilmes.iterator();
        while (iterador.hasNext()) {
            Map<String, String> registro = iterador.next();
            String idcomparador = registro.get("ID");
            if (idcomparador.equals(String.valueOf(id))) {
                iterador.remove();
                novoFilme.setId(String.valueOf(id));
                novoFilme.setNome(nome);
                novoFilme.setDuracao(duracao);
                novoFilme.setAnoLancamento(anoLancamento);
            }
        }
        
        if(novoFilme.getId() != null){
            novoRegistro.put("ID", (id));
            novoRegistro.put("NOME", novoFilme.getNome());
            novoRegistro.put("ANO LANCAMENTO", Integer.toString(novoFilme.getAnoLancamento()));
            novoRegistro.put("DURACAO", Integer.toString(novoFilme.getDuracao()));
            listaFilmes.add(novoRegistro);
        
            String resultado;
            StringBuilder rb = new StringBuilder();
            for (Map<String, String> record : listaFilmes) {
                rb.append("ID: ");
                rb.append(record.get("ID"));
                rb.append("\n");
                rb.append("NOME: ");
                rb.append(record.get("NOME"));
                rb.append("\n");
                rb.append("ANO LANCAMENTO: ");
                rb.append(record.get("ANO LANCAMENTO"));
                rb.append("\n");
                rb.append("DURACAO: ");
                rb.append(record.get("DURACAO"));
                rb.append("\n");
                rb.append("\n");
            }
            resultado = rb.toString();

        try {
            FileOutputStream outputStream = new FileOutputStream(caminho);
            outputStream.write(resultado.getBytes());
            outputStream.close();
            System.out.println("Novos dados salvos com sucesso em cima do arquivo existente.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao salvar novos dados em cima do arquivo existente: " + e.getMessage());
        }
            
            return true;
        }else{
            return false;
        }
    }
    
    public static String lerFilmePorId(int id ){
        String conteudo = "Sem conteudo";
        
        String caminho = lerArquivo2();
        
        try {
            conteudo = Files.readString(Paths.get(caminho));
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
        }
        
        //String filmes = lerArquivo();
        String[] records = conteudo.split("\n\n");
        
        List<Map<String, String>> listaFilmes = new ArrayList<>();
        
        for (String record : records) {
            Map<String, String> map = new HashMap<>();
            String[] linhas = record.split("\n");
            for (String linha : linhas) {
                String[] parts = linha.split(": ");
                String key = parts[0];
                String value = parts[1];
                map.put(key, value);
            }
            listaFilmes.add(map);
        }
        for (Map<String, String> record : listaFilmes) {
            System.out.println(record);
            String idcomparador = record.get("ID");
            if (idcomparador.equals(String.valueOf(id))) {
                System.out.println("id encontrado!!");
                StringBuilder sb = new StringBuilder();
                sb.append("ID: ");
                sb.append(record.get("ID"));
                sb.append("\n");
                sb.append("NOME: ");
                sb.append(record.get("NOME"));
                sb.append("\n");
                sb.append("ANO LANCAMENTO: ");
                sb.append(record.get("ANO LANCAMENTO"));
                sb.append("\n");
                sb.append("DURACAO: ");
                sb.append(record.get("DURACAO"));
                sb.append("\n");
                sb.append("\n");
                String resultado = sb.toString();
                System.out.println(resultado);
                return resultado;
            }
        }
        
        
       return "ID nao encontrado!!";
    }
    
    public static FilmesModel lerFilmePorId2(int id ){
        FilmesModel filme = new FilmesModel();
        
        String conteudo = "Sem conteudo";
        
        String caminho = lerArquivo2();
        
        try {
            conteudo = Files.readString(Paths.get(caminho));
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
        }
        
        //String filmes = lerArquivo();
        String[] records = conteudo.split("\n\n");
        
        List<Map<String, String>> listaFilmes = new ArrayList<>();
        
        for (String record : records) {
            Map<String, String> map = new HashMap<>();
            String[] linhas = record.split("\n");
            for (String linha : linhas) {
                String[] parts = linha.split(": ");
                String key = parts[0];
                String value = parts[1];
                map.put(key, value);
            }
            listaFilmes.add(map);
        }
        for (Map<String, String> record : listaFilmes) {
            
            System.out.println(record);
            String idcomparador = record.get("ID");
            if (idcomparador.equals(String.valueOf(id))) {
                filme.setId(record.get("ID"));
                filme.setNome(record.get("NOME"));
                filme.setAnoLancamento(Integer.parseInt(record.get("ANO LANCAMENTO")));
                filme.setDuracao(Integer.parseInt(record.get("DURACAO")));
                        
                return filme;
            }
        }
        
        
       return null;
    }
    
    public static boolean cadastrarAnime(String id, String nome, int temporadas, String dublagemOriginal){
        AnimesModel novoAnime = new AnimesModel();
        
        novoAnime.setId(id);
        novoAnime.setNome(nome);
        novoAnime.setTemporadas(temporadas);
        novoAnime.setDublagemOriginal(dublagemOriginal);
        
        
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ");
        sb.append(novoAnime.getId());
        sb.append("\nNOME: ");
        sb.append(novoAnime.getNome());
        sb.append("\nTEMPORADAS: ");
        sb.append(novoAnime.getTemporadas());
        sb.append("\nDUBLAGEM ORIGINAL: ");
        sb.append(novoAnime.getDublagemOriginal());
        sb.append("\n\n");
        
        ControlArquivoTexto arquivo = new ControlArquivoTexto();
        arquivo.setArquivo("Salvar");
        arquivo.setTexto(sb.toString());
        
        if(arquivo.escrever(true)){
            return true;
        }else{
            return false;
        }
    }
    
    public static boolean removerAnime(int id){
        String conteudo = "Sem conteudo";
        
        String caminho = lerArquivo2();
        
        try {
            conteudo = Files.readString(Paths.get(caminho));
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
        }
        
        //String animes = lerArquivo();
        String[] records = conteudo.split("\n\n");
        
        List<Map<String, String>> listaAnimes = new ArrayList<>();
        
        
        for (String record : records) {
            Map<String, String> map = new HashMap<>();
            String[] linhas = record.split("\n");
            for (String linha : linhas) {
                String[] parts = linha.split(": ");
                String key = parts[0];
                String value = parts[1];
                map.put(key, value);
            }
            listaAnimes.add(map);
        }
        int cont = 0;
        Iterator<Map<String, String>> iterador = listaAnimes.iterator();
        while (iterador.hasNext()) {
            Map<String, String> registro = iterador.next();
            String idcomparador = registro.get("ID");
            if (idcomparador.equals(String.valueOf(id))) {
                iterador.remove(); 
                cont++;
            }
        }
        String resultado;
        StringBuilder rb = new StringBuilder();
        for (Map<String, String> record : listaAnimes) {
            rb.append("ID: ");
            rb.append(record.get("ID"));
            rb.append("\n");
            rb.append("NOME: ");
            rb.append(record.get("NOME"));
            rb.append("\n");
            rb.append("TEMPORADAS: ");
            rb.append(record.get("TEMPORADAS"));
            rb.append("\n");
            rb.append("DUBLAGEM ORIGINAL: ");
            rb.append(record.get("DUBLAGEM ORIGINAL"));
            rb.append("\n");
            rb.append("\n");
        }
        resultado = rb.toString();
        
        try {
            FileOutputStream outputStream = new FileOutputStream(caminho);
            outputStream.write(resultado.getBytes());
            outputStream.close();
            System.out.println("Novos dados salvos com sucesso em cima do arquivo existente.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao salvar novos dados em cima do arquivo existente: " + e.getMessage());
        }
            
        if(cont!=0){
            return true;
        }else{
            return false;
        }
    }
    
    public static boolean atualizarAnime(String id, String nome, int temporadas, String dublagemOriginal){
        String conteudo = "Sem conteudo";
        
        String caminho = lerArquivo2();
        
        try {
            conteudo = Files.readString(Paths.get(caminho));
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
        }
        
        //String animes = lerArquivo();
        String[] records = conteudo.split("\n\n");
        
        List<Map<String, String>> listaAnimes = new ArrayList<>();
        Map<String, String> novoRegistro = new HashMap<>();
        
        for (String record : records) {
            Map<String, String> map = new HashMap<>();
            String[] linhas = record.split("\n");
            for (String linha : linhas) {
                String[] parts = linha.split(": ");
                String key = parts[0];
                String value = parts[1];
                map.put(key, value);
            }
            listaAnimes.add(map);
        }
        AnimesModel novoAnime = new AnimesModel();
        
        Iterator<Map<String, String>> iterador = listaAnimes.iterator();
        while (iterador.hasNext()) {
            Map<String, String> registro = iterador.next();
            String idcomparador = registro.get("ID");
            if (idcomparador.equals(String.valueOf(id))) {
                iterador.remove();
                novoAnime.setId(String.valueOf(id));
                novoAnime.setNome(nome);
                novoAnime.setTemporadas(temporadas);
                novoAnime.setDublagemOriginal(dublagemOriginal);
            }
        }
        
        if(novoAnime.getId() != null){
            novoRegistro.put("ID", (id));
            novoRegistro.put("NOME", novoAnime.getNome());
            novoRegistro.put("TEMPORADAS", Integer.toString(novoAnime.getTemporadas()));
            novoRegistro.put("DUBLAGEM ORIGINAL", (novoAnime.getDublagemOriginal()));
            listaAnimes.add(novoRegistro);
        
            String resultado;
            StringBuilder rb = new StringBuilder();
            for (Map<String, String> record : listaAnimes) {
                rb.append("ID: ");
                rb.append(record.get("ID"));
                rb.append("\n");
                rb.append("NOME: ");
                rb.append(record.get("NOME"));
                rb.append("\n");
                rb.append("TEMPORADAS: ");
                rb.append(record.get("TEMPORADAS"));
                rb.append("\n");
                rb.append("DUBLAGEM ORIGINAL: ");
                rb.append(record.get("DUBLAGEM ORIGINAL"));
                rb.append("\n");
                rb.append("\n");
            }
            resultado = rb.toString();

        try {
            FileOutputStream outputStream = new FileOutputStream(caminho);
            outputStream.write(resultado.getBytes());
            outputStream.close();
            System.out.println("Novos dados salvos com sucesso em cima do arquivo existente.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao salvar novos dados em cima do arquivo existente: " + e.getMessage());
        }
            
            return true;
        }else{
            return false;
        }
    }
    
    public static String lerAnimePorId(int id ){
        String conteudo = "Sem conteudo";
        
        String caminho = lerArquivo2();
        
        try {
            conteudo = Files.readString(Paths.get(caminho));
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
        }
        
        //String animes = lerArquivo();
        String[] records = conteudo.split("\n\n");
        
        List<Map<String, String>> listaAnimes = new ArrayList<>();
        
        for (String record : records) {
            Map<String, String> map = new HashMap<>();
            String[] linhas = record.split("\n");
            for (String linha : linhas) {
                String[] parts = linha.split(": ");
                String key = parts[0];
                String value = parts[1];
                map.put(key, value);
            }
            listaAnimes.add(map);
        }
        for (Map<String, String> record : listaAnimes) {
            System.out.println(record);
            String idcomparador = record.get("ID");
            if (idcomparador.equals(String.valueOf(id))) {
                System.out.println("id encontrado!!");
                StringBuilder sb = new StringBuilder();
                sb.append("ID: ");
                sb.append(record.get("ID"));
                sb.append("\n");
                sb.append("NOME: ");
                sb.append(record.get("NOME"));
                sb.append("\n");
                sb.append("TEMPORADAS: ");
                sb.append(record.get("TEMPORADAS"));
                sb.append("\n");
                sb.append("DUBLAGEM ORIGINAL: ");
                sb.append(record.get("DUBLAGEM ORIGINAL"));
                sb.append("\n");
                sb.append("\n");
                String resultado = sb.toString();
                System.out.println(resultado);
                return resultado;
            }
        }
        
        
       return "ID nao encontrado!!";
    }
    
    public static AnimesModel lerAnimePorId2(int id ){
        AnimesModel anime = new AnimesModel();
        String conteudo = "Sem conteudo";
        
        String caminho = lerArquivo2();
        
        try {
            conteudo = Files.readString(Paths.get(caminho));
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
        }
        
        //String animes = lerArquivo();
        String[] records = conteudo.split("\n\n");
        
        List<Map<String, String>> listaAnimes = new ArrayList<>();
        
        for (String record : records) {
            Map<String, String> map = new HashMap<>();
            String[] linhas = record.split("\n");
            for (String linha : linhas) {
                String[] parts = linha.split(": ");
                String key = parts[0];
                String value = parts[1];
                map.put(key, value);
            }
            listaAnimes.add(map);
        }
        for (Map<String, String> record : listaAnimes) {
            
            System.out.println(record);
            String idcomparador = record.get("ID");
            if (idcomparador.equals(String.valueOf(id))) {
                anime.setId(record.get("ID"));
                anime.setNome(record.get("NOME"));
                anime.setTemporadas(Integer.parseInt(record.get("TEMPORADAS")));
                anime.setDublagemOriginal((record.get("DUBLAGEM ORIGINAL")));
                        
                return anime;
            }
        }
        
        
       return null;
    }
    
    public static String lerArquivo(){
        String leitura = "Não foi possivel ler o arquivo";
        controller.setArquivo("CHECAR");
        if(controller.ler()) {
            leitura = (controller.getTexto());
        }
        return leitura;
    }
    
    public static String lerArquivo2(){
        String leitura = "Não foi possivel ler o arquivo";
        controller.setArquivo("CHECAR");
        if(controller.ler()) {
            leitura = (controller.getPath());
        }
        
        return leitura;
    }
    
//    public static void main(String[] args) {
//        //atualizarSerie(1);
//        
//    }
}
