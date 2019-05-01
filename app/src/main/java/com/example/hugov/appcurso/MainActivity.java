package com.example.hugov.appcurso;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hugov.appcurso.fragmentos.bemVindoFragmento;
import com.example.hugov.appcurso.fragmentos.consultarCursoFragmento;
import com.example.hugov.appcurso.fragmentos.consultarCursoUrl;
import com.example.hugov.appcurso.fragmentos.consultarListaCursoImagem;
import com.example.hugov.appcurso.fragmentos.consultarListaCursoImagemUrl;
import com.example.hugov.appcurso.fragmentos.consultarListaCursosFragmento;
import com.example.hugov.appcurso.fragmentos.desenvolvedorFramento;
import com.example.hugov.appcurso.fragmentos.registrarCursoFragmento;
import com.example.hugov.appcurso.interfaces.Ifragmentos;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Ifragmentos {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Fragment meuFragmento = new bemVindoFragmento();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, meuFragmento).commit();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment meuFragmento = null;
        boolean fragmentoSelec = false;

        if (id == R.id.nav_inicio) {
            meuFragmento = new bemVindoFragmento();
            fragmentoSelec = true;
        } else if (id == R.id.nav_registro) {
            meuFragmento = new registrarCursoFragmento();
            fragmentoSelec = true;
        } else if (id == R.id.nav_consulta) {
            meuFragmento = new consultarCursoFragmento();
            fragmentoSelec = true;
        } else if (id == R.id.nav_consulta_url) {
            meuFragmento = new consultarCursoUrl();
            fragmentoSelec = true;
        } else if (id == R.id.nav_consulta_lista) {
            meuFragmento = new consultarListaCursosFragmento();
            fragmentoSelec = true;
        } else if (id == R.id.nav_consulta_cursos_img) {
            meuFragmento = new consultarListaCursoImagem();
            fragmentoSelec = true;
        }else if (id == R.id.nav_consulta_img_url) {
            meuFragmento = new consultarListaCursoImagemUrl();
            fragmentoSelec = true;
        }else if (id == R.id.nav_desenvolvedor) {
            meuFragmento = new desenvolvedorFramento();
            fragmentoSelec = true;
        }

        if(fragmentoSelec==true){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, meuFragmento).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
