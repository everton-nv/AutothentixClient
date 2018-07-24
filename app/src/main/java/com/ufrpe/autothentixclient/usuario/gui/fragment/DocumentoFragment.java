//package com.ufrpe.autothentixclient.usuario.gui.fragment;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.ufrpe.autothentixclient.R;
//import com.ufrpe.autothentixclient.infra.GuiUtil;
//import com.ufrpe.plushealth.business.MainServices;
//import com.ufrpe.plushealth.business.SharedPreferencesServices;
//import com.ufrpe.plushealth.gui.SelectedAppointment;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.ufrpe.plushealth.domain.EnumScreenName.APPOINTMENT;
//
///**
// * Classe que exibirá @see {@link android.support.v7.widget.CardView} com os dados do @see {@link com.ufrpe.autothentixclient.usuario.dominio.Documento}
// * em um @see {@link DocumentoRecyclerAdapter}.
// */
//
//public class DocumentoFragment extends Fragment implements RecyclerViewOnClickListenerhack {
//    private RecyclerView mRecyclerView;
//    private List<Documento> mList = new ArrayList<>();
//    private DocumentoRecyclerAdapter adapter;
//    private MainServices mainServices;
//    private SharedPreferencesServices sharedPreferencesServices;
//
//    //Setando o RecyclerView
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_post, container, false);
//
//        mRecyclerView = view.findViewById(R.id.rv_list);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            /*@Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                LinearLayoutManager mLinearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
//                PostRecyclerAdapter adapter = (PostRecyclerAdapter) mRecyclerView.getAdapter();
//
//                if(mList.size() == mLinearLayoutManager.findLastCompletelyVisibleItemPosition() + 1){
//                    List<Post> listaAux = redeServices.exibirPosts();
//
//                    for (int i = 0; i < listaAux.size(); i++){
//                        adapter.addListItem(listaAux.get(i), mList.size() );
//
//                    }
//
//                }
//            }*/
//        });
//
//        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
//        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(mLinearLayoutManager);
//
//        mainServices = new MainServices(getActivity());
//        sharedPreferencesServices = new SharedPreferencesServices(getActivity());
//
//        if(sharedPreferencesServices.getDoctorPreferences()) {
//            try{
//                mList = mainServices.getAppointmentsDoctorLogged();
//            }catch (Exception e) {
//                GuiUtil.myToast(getActivity(), e);
//                Log.e("AppointFragDoc", e.getMessage());
//            }
//        } else {
//            try{
//                mList = mainServices.getAppointmentsPatientLogged();
//            }catch (Exception e) {
//                GuiUtil.myToast(getActivity(), e);
//                Log.e("AppointFragPat", e.getMessage());
//            }
//        }
//
//        adapter = new DocumentoRecyclerAdapter(getActivity(), mList);
//        adapter.setRecyclerViewOnClickListenerhack(this);
//        mRecyclerView.setAdapter(adapter);
//
//        return view;
//    }
//
//    /**
//     * Método que executa ação ao clicar em um elemento da @see {@link RecyclerView.ViewHolder}
//     * @param view
//     * @param position
//     */
//
//    @Override
//    public void onClickListener(View view, int position) {
//        /*Intent intent;
//        Appointment appointment = mList.get(position);
//        */
//        try{
//            Intent intent = new Intent(getActivity(), SelectedAppointment.class);
//            intent.putExtra(APPOINTMENT.getValue(), mList.get(position).getId());
//            startActivity(intent);
//            //noinspection ConstantConditions
//            getActivity().finish();
//        } catch (Exception e){
//            GuiUtil.myToastShort(getActivity(), e);
//            Log.e("onClickListener",e.getMessage());
//        }
//
//        /*if(sharedPreferencesServices.getDoctorPreferences()) {
//            GuiUtil.myToastShort(getActivity(), "Médico escolheu Item: " + position);
//        } else {
//            GuiUtil.myToastShort(getActivity(), "Paciente escolheu Item: " + position);
//        }*/
//    }
//
//    //Click longo
//    @Override
//    public void onLongPressClickListener(View view, int position) {
//        /*DocumentoRecyclerAdapter adapter = (DocumentoRecyclerAdapter) mRecyclerView.getAdapter();
//        adapter.removeListItem(position);
//        */
//        if(sharedPreferencesServices.getDoctorPreferences()) {
//            GuiUtil.myToastShort(getActivity(), "Médico Click longo Item: " + position);
//        } else {
//            GuiUtil.myToastShort(getActivity(), "Paciente Click longo Item: " + position);
//        }
//    }
//}
