package com.ufrpe.autothentixclient.usuario.gui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.ufrpe.autothentixclient.R;
import com.ufrpe.autothentixclient.infra.BlockChain;
import com.ufrpe.autothentixclient.infra.Bloco;
import com.ufrpe.autothentixclient.infra.GuiUtil;
import com.ufrpe.autothentixclient.infra.SharedPreferencesServices;
import com.ufrpe.autothentixclient.usuario.dominio.Documento;
import com.ufrpe.autothentixclient.usuario.gui.AsyncResposta;
import com.ufrpe.autothentixclient.usuario.gui.EditDocServicoActivity;
import com.ufrpe.autothentixclient.usuario.gui.LoadScreen;
import com.ufrpe.autothentixclient.usuario.gui.ViewDocActivity;
import com.ufrpe.autothentixclient.usuario.service.ConexaoServidor;
import com.ufrpe.autothentixclient.usuario.service.UsuarioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ufrpe.autothentixclient.usuario.dominio.TagBundleEnum.DOC_ID;
import static com.ufrpe.autothentixclient.usuario.dominio.TagBundleEnum.DOC_JSON;
import static com.ufrpe.autothentixclient.usuario.dominio.TagBundleEnum.DOC_NAME_TITLE;
import static com.ufrpe.autothentixclient.usuario.dominio.TagBundleEnum.URL_PREVIEW;

/**
 * Classe que exibirá @see {@link android.support.v7.widget.CardView} com os dados do @see {@link com.ufrpe.autothentixclient.usuario.dominio.Documento}
 * em um @see {@link DocumentoRecyclerAdapter}.
 */
public class DocumentoFragment extends Fragment implements RecyclerViewOnClickListenerhack, AsyncResposta {
    private RecyclerView mRecyclerView;
    private List<Documento> mList = new ArrayList<>();
    private DocumentoRecyclerAdapter adapter;
    private int posicaoDoc;
    UsuarioService usuarioService = new UsuarioService();
    ConexaoServidor conexaoServidor;

    //Setando o RecyclerView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        mRecyclerView = view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            /*@Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager mLinearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                DocumentoRecyclerAdapter adapter = (DocumentoRecyclerAdapter) mRecyclerView.getAdapter();

                if(mList.size() == mLinearLayoutManager.findLastCompletelyVisibleItemPosition() + 1){
                    List<Documento> listAux = redeServices.exibirPosts();

                    addNewItens(listAux);

                }
            }*/
        });

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        adapter = new DocumentoRecyclerAdapter(getActivity(), mList);
        adapter.setRecyclerViewOnClickListenerhack(this);
        mRecyclerView.setAdapter(adapter);

        updateDocList();

        return view;
    }

    /**
     * Método que executa ação ao clicar em um elemento da @see {@link RecyclerView.ViewHolder}
     * @param view - View que recebeu o click.
     * @param position - Posição da view que recebeu o click.
     */
    @Override
    public void onClickListener(View view, final int position) {
        switch (view.getId()){
            case R.id.iv_context_menu:
                myPopupMenu(view, position);
                break;
            case -1:
                openDoc(position);
                break;
        }
    }

    private void setPosicaoDoc(int novaPosicao){
        posicaoDoc = novaPosicao;
    }

    private int getPosicaoDoc(){
        return posicaoDoc;
    }

    private void openDoc(int position) {
        try{
            Intent intent = new Intent(getActivity(), ViewDocActivity.class);
            String json = usuarioService.criarJsonObjeto(mList.get(position));
            intent.putExtra(URL_PREVIEW.getValue(), json);
            intent.putExtra(DOC_NAME_TITLE.getValue(), mList.get(position).getNomedoc());
            startActivity(intent);
        } catch (Exception e){
            GuiUtil.myToastShort(getActivity(), e);
            Log.e(getString(R.string.log_screen_doc_on_click_listener),e.getMessage());
        }
    }

    private void myPopupMenu(View view, final int position){
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.inflate(R.menu.recyclerview_docs_options);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_edit:
                        editDoc(position);
                        break;
                    case R.id.action_delete:
                        deleteDoc(position);
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void editDoc(int position) {
        Documento documento = mList.get(position);
        String jsonDoc = usuarioService.criarJsonObjeto(documento);
        Intent intent = new Intent(getActivity(),EditDocServicoActivity.class);
        intent.putExtra(DOC_JSON.getValue(),jsonDoc);
        intent.putExtra(DOC_ID.getValue(),documento.getId());
        startActivity(intent);
    }

    private void deleteDoc(int position) {
        connectToServer();
        Documento documento = mList.get(position);
        String jsonDocApagar = usuarioService.criarJsonObjeto(documento);
        SharedPreferencesServices sharedPreferencesServices = new SharedPreferencesServices(getContext());
        String token = sharedPreferencesServices.getTokenPreferences();
        setPosicaoDoc(position);

        usuarioService.setJSONDOC(jsonDocApagar);
        usuarioService.deletarDocumento(documento.getId(),conexaoServidor,token);
        GuiUtil.myToastShort(getContext(), "Deletar " + Integer.toString(position));
    }

    @Override
    public void onLongPressClickListener(View view, int position) {
        /*DocumentoRecyclerAdapter adapter = (DocumentoRecyclerAdapter) mRecyclerView.getAdapter();
        adapter.removeListItem(position);
        */
        GuiUtil.myToastShort(getActivity(), "Click longo Item: " + position);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferencesServices sharedPreferencesServices = new SharedPreferencesServices(getActivity());
        String token = sharedPreferencesServices.getTokenPreferences();
        boolean needUpdateDocList = sharedPreferencesServices.getNeedUpdateDocList();

        if(needUpdateDocList) {
            sharedPreferencesServices.noNeedUpdateDocList();
            updateDocList(token);
        }
    }

    private void updateDocList(String token) {
        connectToServer();
        usuarioService.listarDocs(conexaoServidor, token);
    }

    private void updateDocList() {
        SharedPreferencesServices sharedPreferencesServices = new SharedPreferencesServices(getActivity());
        String token = sharedPreferencesServices.getTokenPreferences();
        updateDocList(token);
    }
    private void addNewItens(List<Documento> listAux){
        for (int i = 0; i < listAux.size(); i++){
            adapter.addListItem(listAux.get(i), mList.size());
        }
    }

    @Override
    public void processFinish(String output) {
        LoadScreen.loadOut(getContext(), (LinearLayout) getActivity().findViewById(R.id.progressBarLayout));
        SharedPreferencesServices sharedPreferencesServices = new SharedPreferencesServices(getActivity());
        if(!Objects.equals(output, "{\"data\":\"1 Transação adicionada na fila\"}") && !Objects.equals(output,"{\"data\":\"Documento deletado \"}")) {
            List<Documento> listAux = usuarioService.docServerJsontoObject(output);
            adapter.clearList();
            addNewItens(listAux);
        }
        if (Objects.equals(output,"{\"data\":\"Documento deletado \"}")){
            adapter.removeListItem(getPosicaoDoc());
            String jsonDocDeletar = usuarioService.getJSONDOC();
            String token = sharedPreferencesServices.getTokenPreferences();
            connectToServer();
            usuarioService.inserirBloco(jsonDocDeletar,conexaoServidor, token, "deletar");
        }
    }

    @Override
    public void processStart() {
        LoadScreen.loadOn(getContext(), (LinearLayout) getActivity().findViewById(R.id.progressBarLayout));
    }

    private void connectToServer(){
        conexaoServidor = new ConexaoServidor();
        conexaoServidor.delegate = this;
    }
}