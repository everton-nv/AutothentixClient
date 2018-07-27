package com.ufrpe.autothentixclient.usuario.gui.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ufrpe.autothentixclient.R;
import com.ufrpe.autothentixclient.usuario.dominio.Documento;

import java.util.List;

/**
 * Classe que personaliza o @see {@link RecyclerView.Adapter} para exibição
 * dos @see {@link Documento}.
 */

public class DocumentoRecyclerAdapter extends RecyclerView.Adapter<DocumentoRecyclerAdapter.MyViewHolder> {
    private List<Documento> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerhack mRecyclerViewOnClickListenerhack;

    /**
     * Construtor - Recebe a @see {@link List} com os @see {@link Documento} para usar no @see {@link LayoutInflater}.
     * @param context - Contexto da aplicação.
     * @param list - Lista com os @see {@link Documento}.
     */

    public DocumentoRecyclerAdapter(Context context, List<Documento> list) {
        mList = list;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Cria os Itens da Lista (até alguns a mais do que a tela comporta)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = mLayoutInflater.inflate(R.layout.item_document_cardview, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setRecyclerViewOnClickListenerhack(RecyclerViewOnClickListenerhack r){
        mRecyclerViewOnClickListenerhack = r;
    }

    /**
     * Método adiciona um @see {@link Documento} a lista.
     * @param documento - @see {@link Documento}.
     * @param position - Posição.
     */

    public void addListItem(Documento documento, int position){
        mList.add(documento);
        notifyItemInserted(position);
    }

    /**
     * Método remove um @see {@link Documento} da lista.
     * @param position - Posição.
     */

    public void removeListItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    //Autaliza os itens da lista (Os que não estão visíveis)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Documento documento;
        documento = mList.get(position);

        holder.nomeDoc.setText(documento.getNomedoc());

    }

    /**
     * Classe que personaliza o @see {@link RecyclerView.ViewHolder}
     */

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView nomeDoc;

        public MyViewHolder(final View itemView) {
            super(itemView);

            nomeDoc = itemView.findViewById(R.id.txtNomeDoc);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerhack != null){
                mRecyclerViewOnClickListenerhack.onClickListener(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(mRecyclerViewOnClickListenerhack != null){
                mRecyclerViewOnClickListenerhack.onLongPressClickListener(v, getAdapterPosition());
                return true;
            }
            return false;
        }
    }
}
