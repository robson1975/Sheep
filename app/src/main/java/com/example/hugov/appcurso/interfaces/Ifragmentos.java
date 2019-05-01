package com.example.hugov.appcurso.interfaces;

import com.example.hugov.appcurso.fragmentos.bemVindoFragmento;
import com.example.hugov.appcurso.fragmentos.consultarCursoFragmento;
import com.example.hugov.appcurso.fragmentos.consultarCursoUrl;
import com.example.hugov.appcurso.fragmentos.consultarListaCursoImagem;
import com.example.hugov.appcurso.fragmentos.consultarListaCursoImagemUrl;
import com.example.hugov.appcurso.fragmentos.consultarListaCursosFragmento;
import com.example.hugov.appcurso.fragmentos.desenvolvedorFramento;
import com.example.hugov.appcurso.fragmentos.registrarCursoFragmento;

public interface Ifragmentos extends
        bemVindoFragmento.OnFragmentInteractionListener,
        consultarCursoFragmento.OnFragmentInteractionListener,
        consultarListaCursosFragmento.OnFragmentInteractionListener,
        desenvolvedorFramento.OnFragmentInteractionListener,
        registrarCursoFragmento.OnFragmentInteractionListener,
        consultarListaCursoImagemUrl.OnFragmentInteractionListener,
        consultarListaCursoImagem.OnFragmentInteractionListener,
        consultarCursoUrl.OnFragmentInteractionListener



{
}
