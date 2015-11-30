package com.example.gonzalo.pspdescargarweb;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by ivan on 10/22/2015.
 */
public class Adaptador extends BaseAdapter {

        private final Activity actividad;
        private final List<String > lista;

        public Adaptador(Activity actividad, List<String> lista) {

            super();
            this.actividad = actividad;
            this.lista = lista;
        }
        public View getView(int position, View convertView,
                            ViewGroup parent) {

            LayoutInflater inflater = actividad.getLayoutInflater();
            View view = inflater.inflate(R.layout.item, null, true);
            TextView tv = (TextView)view.findViewById(R.id.tvitem);

            tv.setText(lista.get(position));

            return view;
        }

    @Override
    public int getCount() {
        return lista.size();
    }

    public Object getItem(int arg0) {
            return null;
        }
        public long getItemId(int position) {
            return position;

    }
}
