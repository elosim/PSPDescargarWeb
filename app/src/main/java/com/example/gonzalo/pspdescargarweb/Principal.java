package com.example.gonzalo.pspdescargarweb;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Principal extends AppCompatActivity {

    private android.widget.Button btDescargar;
    private android.widget.ScrollView scrollView;
    private List<String> s = new ArrayList<>();
    private ListView lv;
    private Adaptador adp;
    private Activity activity;
    private String pag;
    List <Bitmap> bmps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        activity = this;
        this.scrollView = (ScrollView) findViewById(R.id.scrollView);
//        this.etWeb = (EditText) findViewById(R.id.etWeb);
        this.btDescargar = (Button) findViewById(R.id.btDescargar);
        this.lv = (ListView)findViewById(R.id.lv);

        pag="http://imagenesbonitas.bosquedefantasias.com/";


        adp = new Adaptador(this, s);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choose(position);
            }
        });
    }

    public void metodo(View v){
        Tarea t = new Tarea();
        t.execute(pag);
    }

    public void choose(int pos){

        Intent i = new Intent(this,VerFoto.class);
        i.putExtra("img", bmps.get(pos));
        i.putExtra("pag",s.get(pos).toString());
        startActivity(i);
    }
    public List<String> descargar(String urlpagina){
        try {
            URL url = new URL(urlpagina);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String linea;
            List<String>  out = new ArrayList<>();
            while((linea = in.readLine())!= null){
                if(linea.contains("<img src=")){
                    linea=linea.substring(linea.indexOf("<img src=")+10);
                    linea=linea.substring(0,linea.indexOf('"'));
                    if(linea.indexOf("/")==0) {
                        linea= pag + linea.substring(1);

                    }
                    out.add(linea.trim());
                }
            }
            in.close();
            return out;
        } catch (IOException ex) {}

        return null;
    }

    public boolean guardarImagen(List<String> nombreArchivo){

        try{
            int i=0;
            for(String arch : nombreArchivo) {

                    URL url = new URL(arch);
                    InputStream in = url.openStream();
                    OutputStream out = new FileOutputStream(getExternalFilesDir(null) + "/" + "archivo"+i+".jpg");
                    byte[] buffer = new byte[2048];
                    int longitud;
                    while ((longitud = in.read(buffer)) != -1) {
                        out.write(buffer, 0, longitud);
                    }
                    in.close();
                    out.close();
                i++;
            }
            return true;
        }catch (MalformedURLException ex){
            return false;

        }catch (IOException e){
            return false;
        }
    }
    public class Tarea extends AsyncTask<String, Integer, List<String>> {
        private ProgressBar pb;

        @Override
        protected List<String> doInBackground(String... params) {

            s = descargar(params[0]);

            for(int i=0;i<=1000;i++){
                if(i%5==0){
                    publishProgress(i);
                }
            }

            URL urlimg = null;
            bmps=new ArrayList<>();

            try {

                for(String a : s) {
                    urlimg = new URL(a);
                    bmps.add( BitmapFactory.decodeStream(urlimg.openConnection().getInputStream()));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            guardarImagen(s);
            return s;
        }
        protected void onProgressUpdate(Integer... values){
            super.onProgressUpdate(values);

            pb.setProgress(values[values.length-1]);
        }
        protected void onPreExecute(){
            super.onPreExecute();

            pb = (ProgressBar)findViewById(R.id.pb);
            pb.setProgress(0);
            pb.setMax(100);
        }
        protected void onPostExecute(List<String> time){

            lv = (ListView)findViewById(R.id.lv);
            adp = new Adaptador(activity, time);
            lv.setAdapter(adp);
            lv.setTag(time);
            pb.setProgress(100);
        }
    }


}
