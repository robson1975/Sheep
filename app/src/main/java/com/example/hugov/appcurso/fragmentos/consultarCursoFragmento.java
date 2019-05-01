package com.example.hugov.appcurso.fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hugov.appcurso.R;
import com.example.hugov.appcurso.entidades.Curso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link consultarCursoFragmento.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link consultarCursoFragmento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class consultarCursoFragmento extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    EditText campoCodigo;
    TextView campoNome, campoCategoria, campoProfessor;
    Button botaoConsultar;
    ImageView imgFoto;
    ProgressDialog progresso;
    RelativeLayout layoutRegistrar;
    RequestQueue request;
    JsonObjectRequest jsonObjectReq;

    private OnFragmentInteractionListener mListener;

    public consultarCursoFragmento() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment consultarCursoFragmento.
     */
    // TODO: Rename and change types and number of parameters
    public static consultarCursoFragmento newInstance(String param1, String param2) {
        consultarCursoFragmento fragment = new consultarCursoFragmento();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View vista = inflater.inflate(R.layout.fragment_consultar_curso_fragmento, container, false);

        campoCodigo = vista.findViewById(R.id.codigo);
        campoNome = vista.findViewById(R.id.txt_nome);
        campoCategoria = vista.findViewById(R.id.txt_categoria);
        campoProfessor = vista.findViewById(R.id.txt_professor);
        botaoConsultar = vista.findViewById(R.id.btnConsultarCurso);
        imgFoto = vista.findViewById(R.id.imagemId);


        request = Volley.newRequestQueue(getContext());

        botaoConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarWEBService();
            }
        });

        return vista;
    }


    private void carregarWEBService() {

        progresso = new ProgressDialog(getContext());
        progresso.setMessage("Buscando...");
        progresso.show();

        String url = "http://192.168.1.4/webservices/consultarCursoImg.php?codigo="+ campoCodigo.getText().toString();


        jsonObjectReq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectReq);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progresso.hide();
        Toast.makeText(getContext(), "Não foi possível efetuar a consulta " +error.toString() , Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        progresso.hide();
        //Toast.makeText(getContext(), "Mensagem: "+response, Toast.LENGTH_SHORT).show();

        Curso tabCurso = new Curso();
        JSONArray json = response.optJSONArray("curso");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);
            tabCurso.setNome(jsonObject.optString("nome"));
            tabCurso.setProfessor(jsonObject.optString("professor"));
            tabCurso.setCategoria(jsonObject.optString("categoria"));
            tabCurso.setDado(jsonObject.optString("imagem"));
        }catch (JSONException e){
            e.printStackTrace();
        }

        campoNome.setText("Nome: " + tabCurso.getNome());
        campoCategoria.setText("Categoria: " + tabCurso.getCategoria());
        campoProfessor.setText("Professor: " + tabCurso.getProfessor());

        if(tabCurso.getImagem()!=null){
            imgFoto.setImageBitmap(tabCurso.getImagem());
        }else{
            imgFoto.setImageResource(R.drawable.sem_foto);
        }


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
