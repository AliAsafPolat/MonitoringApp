package com.example.cdtp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView txt_sera1_hedef;
    TextView txt_sera1_guncel;
    TextView txt_sera2_hedef;
    TextView txt_sera2_guncel;

    EditText editText_sera1_hedef;
    EditText editText_sera2_hedef;

    Button btn_sera1_hedef_guncelle;
    Button btn_sera2_hedef_guncelle;

    ImageView imageView_sera1_alert;
    ImageView imageView_sera2_alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_sera1_hedef = findViewById(R.id.txtSera1HedefSicaklik);
        txt_sera1_guncel = findViewById(R.id.txtSera1GuncelSicaklik);
        editText_sera1_hedef = findViewById(R.id.editTextSera1HedefSicaklik);

        txt_sera2_hedef = findViewById(R.id.txtSera2HedefSicaklik);
        txt_sera2_guncel = findViewById(R.id.txtSera2GuncelSicaklik);
        editText_sera2_hedef = findViewById(R.id.editTextSera2HedefSicaklik);

        imageView_sera1_alert = findViewById(R.id.imageViewSera1Alert);
        imageView_sera2_alert = findViewById(R.id.imageViewSera2Alert);

        imageView_sera1_alert.setVisibility(View.INVISIBLE);
        imageView_sera2_alert.setVisibility(View.INVISIBLE);

        btn_sera2_hedef_guncelle = findViewById(R.id.btnSera2HedefSicaklikGuncelle);
        btn_sera1_hedef_guncelle = findViewById(R.id.btnSera1HedefSicaklikGuncelle);
        //btn_sera2_hedef_guncelle = findViewById()
        btn_sera1_hedef_guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {
                            String yeniHedef  = editText_sera1_hedef.getText().toString();
                            String yeniSicaklik = HttpRequest.sera_sicaklik_ayarla(HttpRequest.SERA1_HEDEF,Integer.parseInt(yeniHedef));
                            txt_sera1_hedef.setText("Hedef Sicaklik : " + yeniSicaklik);
                            //Your code goes here
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }
        });


        btn_sera2_hedef_guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {
                            String yeniHedef  = editText_sera2_hedef.getText().toString();
                            String yeniSicaklik = HttpRequest.sera_sicaklik_ayarla(HttpRequest.SERA2_HEDEF,Integer.parseInt(yeniHedef));
                            txt_sera2_hedef.setText("Hedef Sicaklik : " + yeniSicaklik);
                            //Your code goes here
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }
        });

        Thread thread2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    while(true){
                        String guncelSicaklik1 = HttpRequest.sera_sicaklik_getir(HttpRequest.SERA1_GUNCEL);
                        txt_sera1_guncel.setText("Guncel Sicaklik : " + guncelSicaklik1);

                        String guncelSicaklik2 = HttpRequest.sera_sicaklik_getir(HttpRequest.SERA2_GUNCEL);
                        txt_sera2_guncel.setText("Guncel Sicaklik : " + guncelSicaklik2);


                        String sera1hedef = HttpRequest.sera_sicaklik_getir(HttpRequest.SERA1_HEDEF);
                        String sera2hedef = HttpRequest.sera_sicaklik_getir(HttpRequest.SERA2_HEDEF);

                        Log.d("Sicaklik", sera2hedef + " - " + guncelSicaklik2);
                        if(sera1hedef.equals(guncelSicaklik1)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    imageView_sera1_alert.setVisibility(View.VISIBLE);
                                }
                            });
                        }else{

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    imageView_sera1_alert.setVisibility(View.INVISIBLE);
                                }
                            });
                        }


                        if(sera2hedef.equals(guncelSicaklik2)){
                            runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView_sera2_alert.setVisibility(View.VISIBLE);
                            }
                        });

                        }else{
                            Log.d("Sicaklik", "Bu kola düstü");
                                runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("Sicaklik", "GUIde");
                                    imageView_sera2_alert.setVisibility(View.INVISIBLE);
                                }
                            });
                        }

                        Thread.sleep(2000);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Sicaklik", "Patladi : " + e.getCause());
                }
            }
        });

        thread2.start();

        // Uygulama başladığında hedef sıcaklıkları çeksin
        Thread thread3 = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    txt_sera1_hedef.setText("Hedef Sicaklik : " + HttpRequest.sera_sicaklik_getir(HttpRequest.SERA1_HEDEF));
                    txt_sera2_hedef.setText("Hedef Sicaklik : " + HttpRequest.sera_sicaklik_getir(HttpRequest.SERA2_HEDEF));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread3.start();


    }



}
