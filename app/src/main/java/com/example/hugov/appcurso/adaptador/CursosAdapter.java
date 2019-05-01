package com.example.hugov.appcurso.adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hugov.appcurso.R;
import com.example.hugov.appcurso.entidades.Curso;

import java.util.List;

public class CursosAdapter extends RecyclerView.Adapter<CursosAdapter.CursosHolder> {

    List<Curso>listaCursos;

    public CursosAdapter(List<Curso> listaCursos) {
        this.listaCursos = listaCursos;
    }

    @NonNull
    @Override
    public CursosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_cursos, parent, false);
        RecyclerView.LayoutParams layoutParams =
                new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

        vista.setLayoutParams(layoutParams);
        return new CursosHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull CursosHolder holder, int position) {
        holder.txtCodigo.setText(listaCursos.get(position).getCodigo().toString());
        holder.txtNome.setText(listaCursos.get(position).getNome().toString());
        holder.txtCategoria.setText(listaCursos.get(position).getCategoria().toString());
        holder.txtProfessor.setText(listaCursos.get(position).getProfessor().toString());
    }

    @Override
    public int getItemCount() {
        return listaCursos.size();
    }

    public class CursosHolder extends RecyclerView.ViewHolder {

        TextView txtNome,txtCodigo,txtProfessor, txtCategoria;

        public CursosHolder(View itemView) {
            super(itemView);
            txtNome= (TextView) itemView.findViewById(R.id.txtNome);
            txtCodigo= (TextView) itemView.findViewById(R.id.txtCodigo);
            txtProfessor= (TextView) itemView.findViewById(R.id.txtProfessor);
            txtCategoria= (TextView) itemView.findViewById(R.id.txtCategoria);
        }
    }
}
