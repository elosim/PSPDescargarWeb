package com.example.gonzalo.pspdescargarweb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class VerFoto extends AppCompatActivity {
    private ImageView imgv;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_foto);
        Bitmap bmps = getIntent().getParcelableExtra("img");
        imgv = (ImageView) findViewById(R.id.ivVer);
        tv = (TextView)findViewById(R.id.tvVer);

        if(bmps!=null) {

            imgv.setImageBitmap(bmps);
            tv.setText(getIntent().getExtras().getString("pag"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ver_foto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
