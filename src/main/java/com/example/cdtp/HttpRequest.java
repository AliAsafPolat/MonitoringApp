package com.example.cdtp;

import android.os.AsyncTask;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
public class HttpRequest {
    // Localde çalışan servera istek atılacak portları belirtir.
    public static final String SERA1_HEDEF = "http://25.38.180.210:8080/client/hedef/1";
    public static final String SERA2_HEDEF = "http://25.38.180.210:8080/client/hedef/2";
    public static final String SERA1_GUNCEL = "http://25.38.180.210:8080/client/guncel/1";
    public static final String SERA2_GUNCEL = "http://25.38.180.210:8080/client/guncel/2";
/*
    public static void main(String[] args) {
        String res;
        res = sera_sicaklik_getir(SERA1_HEDEF);
        System.out.println("Sera 1 - Hedef Sicaklik Degeri : " + res);

        res = sera_sicaklik_getir(SERA1_GUNCEL);
        System.out.println("Sera 1 - Guncel Sicaklik Degeri : " + res);

        res = sera_sicaklik_getir(SERA2_HEDEF);
        System.out.println("Sera 2 - Hedef Sicaklik Degeri : " + res);

        res = sera_sicaklik_getir(SERA2_GUNCEL);
        System.out.println("Sera 2 - Guncel Sicaklik Degeri : " + res);

        sera_sicaklik_ayarla(SERA1_HEDEF,133);
    }*/

    /**
     * Verilen seranın sicaklik bilgisini getirir. (hedef-guncel) ikisi de.
     * @param SeraBilgi hangi seranın hangi bilgisinin getirileceğini belirler.
     * @return seranin sicaklik değeri
     */
    public static String sera_sicaklik_getir(String SeraBilgi){
        String val=null; // Sorguların dönüş değerlerini tutar.

        switch (SeraBilgi){
            case SERA1_HEDEF:
                val = executeGetRequest(SERA1_HEDEF);
                break;

            case SERA2_HEDEF:
                val = executeGetRequest(SERA2_HEDEF);
                break;

            case SERA1_GUNCEL:
                val = executeGetRequest(SERA1_GUNCEL);
                break;

            case SERA2_GUNCEL:
                val = executeGetRequest(SERA2_GUNCEL);
                break;
        }
        return val;
    }

    /**
     * Verilen seranın sicaklik bilgilerini günceller.
     * @param SeraBilgi Hangi seranın sıcaklık bilgisi güncellenecek.
     * @param sicaklik Kaç dereceye ayarlanmak isteniyor.
     * @return İşlemler başarı ile gerçekleşirse ayarlanan sıcaklık değeri döner.
     */
    public static String sera_sicaklik_ayarla( String SeraBilgi, int sicaklik){
        String val=null; // Sorguların dönüş değerlerini tutar.
        int res;         // Dondurulecek sıcaklık değerini tutar.
        switch (SeraBilgi){
            case SERA1_HEDEF:
                val = executePostRequest(SERA1_HEDEF, Integer.toString(sicaklik));
                break;

            case SERA2_HEDEF:
                val = executePostRequest(SERA2_HEDEF, Integer.toString(sicaklik));
                break;

            case SERA1_GUNCEL:
                val = executePostRequest(SERA1_GUNCEL, Integer.toString(sicaklik));
                break;

            case SERA2_GUNCEL:
                val = executePostRequest(SERA2_GUNCEL, Integer.toString(sicaklik));
                break;
        }

        // Eğer sorgular başarı ile tamamlanmadıysa bu bilgiyi dön.
        if (val == null)
            return null;

        return val;
    }

    /**
     * Localde çalışan serverda sicaklik değerlerini değiştirmek için kullanılır.
     * @param targetURL Hangi seranın(sera1,sera2) hangi değerini(hedef,guncel) değiştireceği bilgisi.
     * @param sicaklikDegeri Yeni sicaklik bilgisi
     * @return Atanan sicaklik degeri dondurulur.
     */
    public static String executePostRequest(String targetURL, String sicaklikDegeri) {
        HttpURLConnection connection = null;
        try {
            //Create connection
            targetURL = targetURL + "/" + sicaklikDegeri;
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Seralardan sicaklik değerini okumak için kullanılır.
     * @param targetURL Hangi seradan(sera1,sera2) hangi değerin(guncel, hedef) okunacağı bilgisi.
     * @return Okunan sicaklik degeri dondurulur.
     */
    public static String executeGetRequest(String targetURL) {

        HttpURLConnection connection = null;

        try {
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }



}
