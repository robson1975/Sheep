package com.example.hugov.appcurso.fragmentos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hugov.appcurso.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link registrarCursoFragmento.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link registrarCursoFragmento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class registrarCursoFragmento extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText campoNome, campoCodigo, campoCategoria, campoProfessor;
    Button botaoRegistrar, botaoFoto;
    ImageView imgFoto;
    ProgressDialog progresso;
    RelativeLayout layoutRegistrar;
    RequestQueue request;
    JsonObjectRequest jsonObjectReq;
    StringRequest stringRequest;


    private static final int COD_SELECIONA = 10;
    private static final int COD_FOTO = 20;
    private static final int COD_PERMISSOES = 100;

    private static final String PASTA_PRINCIPAL = "minhasImagensApp/";  //dir principal
    private static final String PASTA_IMAGEM = "imagens";  //PASTA ONDE FICARAM AS FOTOS
    private static final String DIRETORIO_IMAGEM = PASTA_PRINCIPAL + PASTA_IMAGEM;

    private String path;
    File fileImagem;
    Bitmap bitmap;

    private OnFragmentInteractionListener mListener;

    public registrarCursoFragmento() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment registrarCursoFragmento.
     */
    // TODO: Rename and change types and number of parameters
    public static registrarCursoFragmento newInstance(String param1, String param2) {
        registrarCursoFragmento fragment = new registrarCursoFragmento();
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

        View vista = inflater.inflate(R.layout.fragment_registrar_curso_fragmento, container, false);
        campoCodigo = vista.findViewById(R.id.campoCodigo);
        campoNome = vista.findViewById(R.id.campoNome);
        campoCategoria = vista.findViewById(R.id.campoCategoria);
        campoProfessor = vista.findViewById(R.id.campoProfessor);
        botaoRegistrar = vista.findViewById(R.id.btnRegistrar);
        imgFoto = vista.findViewById(R.id.imgFoto);
        botaoFoto = vista.findViewById(R.id.btnFoto);

        request = Volley.newRequestQueue(getContext());
        
        botaoRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarWEBService();
            }


        });
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarDialog();
            }


        });

        return vista;
    }

    private void carregarDialog() {
        final CharSequence[] opcoes = {"Tirar Foto", "Selecionar da Galeria", "Cancelar"};
        final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Escolha uma Opção");
        builder.setItems(opcoes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opcoes[i].equals("Tirar Foto")){
                    abrirCamera();
                }else{
                    if (opcoes[i].equals("Selecionar da Galeria")){
                        Intent intent=new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Selecione"),COD_SELECIONA);
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();
    }



    private void abrirCamera() {
        File meuFile = new File(Environment.getExternalStorageDirectory(), DIRETORIO_IMAGEM);
        boolean estaCriada = meuFile.exists();

        if(estaCriada == false){

            estaCriada = meuFile.mkdirs();
        }

        if(estaCriada == true){

            Long consecultivo = System.currentTimeMillis()/1000;
            String nome = consecultivo.toString()+".jpg";



            path = Environment.getExternalStorageDirectory() + File.separator + DIRETORIO_IMAGEM + File.separator + nome;  //caminho completo da imagem
            fileImagem = new File(path);
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagem));
            startActivityForResult(intent, COD_FOTO);

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case COD_SELECIONA:
                Uri tabCurso = data.getData();
                imgFoto.setImageURI(tabCurso);

                try {
                    bitmap=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),tabCurso);
                    imgFoto.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;

            case COD_FOTO:

               MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("Path",""+path);
                            }
                        });

                bitmap= BitmapFactory.decodeFile(path);
                imgFoto.setImageBitmap(bitmap);
                break;
        }
    }





    private void carregarWEBService() {

        progresso = new ProgressDialog(getContext());
        progresso.setMessage("Carregando...");
        progresso.show();

        String url = "http://192.168.1.4/webservices/registroImg.php?";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progresso.hide();

                if (response.trim().equalsIgnoreCase("registra")) {
                    campoCodigo.setText("");
                    campoNome.setText("");
                    campoCategoria.setText("");
                    campoProfessor.setText("");
                    Toast.makeText(getContext(), "Registrado com sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Registro não inserido", Toast.LENGTH_SHORT).show();
                    Log.i("RESPOSTA: ", "" + response);
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Erro ao Registrar", Toast.LENGTH_SHORT).show();
                progresso.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String codigo = campoCodigo.getText().toString();
                String nome = campoNome.getText().toString();
                String categoria = campoCategoria.getText().toString();
                String professor = campoProfessor.getText().toString();
                String imagem = converterImgString(bitmap);

                Map<String,String> parametros = new HashMap<>();
                parametros.put("codigo", codigo);
                parametros.put("nome", nome);
                parametros.put("categoria", categoria);
                parametros.put("professor", professor);
                parametros.put("imagem", imagem);


                return parametros;
            }

        };

        request.add(stringRequest);

    }

    private String converterImgString(Bitmap bitmap) {

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagemByte=array.toByteArray();
        String imagemString= Base64.encodeToString(imagemByte,Base64.DEFAULT);

        return imagemString;
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
