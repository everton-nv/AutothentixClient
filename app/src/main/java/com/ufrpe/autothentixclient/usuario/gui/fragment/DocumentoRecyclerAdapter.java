//package com.ufrpe.autothentixclient.usuario.gui.fragment;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.ufrpe.autothentixclient.R;
//import com.ufrpe.plushealth.business.PersonServices;
//import com.ufrpe.plushealth.business.SharedPreferencesServices;
//import com.ufrpe.plushealth.domain.Appointment;
//import com.ufrpe.plushealth.domain.Person;
//import com.ufrpe.plushealth.infra.FormataData;
//
//import java.util.List;
//
//import static com.ufrpe.plushealth.domain.EnumSchedule.MANHA;
//import static com.ufrpe.plushealth.domain.EnumSchedule.MANHADB;
//import static com.ufrpe.plushealth.domain.EnumSchedule.TARDE;
//import static com.ufrpe.plushealth.domain.EnumStatusAppointment.CANCELADA;
//import static com.ufrpe.plushealth.domain.EnumStatusAppointment.MARCADA;
//import static com.ufrpe.plushealth.domain.EnumStatusAppointment.REALIZADA;
//
///**
// * Classe que personaliza o @see {@link RecyclerView.Adapter} para exibição
// * dos @see {@link Documento}.
// */
//
//public class DocumentoRecyclerAdapter extends RecyclerView.Adapter<DocumentoRecyclerAdapter.MyViewHolder> {
//    private List<Documento> mList;
//    private LayoutInflater mLayoutInflater;
//    private RecyclerViewOnClickListenerhack mRecyclerViewOnClickListenerhack;
//    private final int SELECT_START_DATE = 0;
//    private final int SELECT_END_DATE = 8;
//    private final int SELECT_START_HOUR = 8;
//
//
//    /**
//     * Construtor - Recebe a @see {@link List} com os @see {@link Documento} para usar no @see {@link LayoutInflater}.
//     * @param context - Contexto da aplicação.
//     * @param list - Lista com os @see {@link Documento}.
//     */
//
//    public DocumentoRecyclerAdapter(Context context, List<Documento> list) {
//        mList = list;
//        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    //Cria os Itens da Lista (até alguns a mais do que a tela comporta)
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view;
//        view = mLayoutInflater.inflate(R.layout.item_appointment_for_doctor_cardview, parent, false);
//        MyViewHolder myViewHolder = new MyViewHolder(view);
//
//        return myViewHolder;
//    }
//
//    @Override
//    public int getItemCount() {
//        return mList.size();
//    }
//
//    public void setRecyclerViewOnClickListenerhack(RecyclerViewOnClickListenerhack r){
//        mRecyclerViewOnClickListenerhack = r;
//    }
//
//    /**
//     * Método adiciona um @see {@link Documento} a lista.
//     * @param documento - @see {@link Documento}.
//     * @param position - Posição.
//     */
//
//    public void addListItem(Documento documento, int position){
//        mList.add(documento);
//        notifyItemInserted(position);
//    }
//
//    /**
//     * Atualiza item da lista quando o usuário segue ou dessegue ao click no botão no @see {@link android.support.v7.widget.CardView}.
//     * @param position - Posição.
//     */
//
//    public void atualizarSeguir(int position){
//        notifyItemChanged(position);
//    }
//
//    /**
//     * Método remove um @see {@link Documento} da lista.
//     * @param position - Posição.
//     */
//
//    public void removeListItem(int position){
//        mList.remove(position);
//        notifyItemRemoved(position);
//    }
//
//    //Autaliza os itens da lista (Os que não estão visíveis)
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        Person person;
//        person = personServices.getPersonByDoctorID(mList.get(position).getIdDoctor());
//
//        String todayDate = FormataData.formataDataHoje();
//        String dateHour = mList.get(position).getDate();
//        String date = dateHour.substring(SELECT_START_DATE,SELECT_END_DATE);
//        String hour = dateHour.substring(SELECT_START_HOUR);
//
//        String formatedDate = FormataData.formatarDataHoraDataBaseParaExibicao(date);
//        String formatedSchedule;
//        String status = mList.get(position).getStatus();
//
//        if(hour.equals(MANHADB.getValue())){
//            formatedSchedule = MANHA.getValue();
//        } else {
//            formatedSchedule = TARDE.getValue();
//        }
//
//        holder.txtDoctor.setText(person.getName());
//        holder.txtDate.setText(formatedDate);
//        holder.txtStatus.setText(status);
//        holder.txtSchedule.setText(formatedSchedule);
//
//        if(status.equals(MARCADA.getValue())){
//            holder.txtStatus.setTextColor(mLayoutInflater.getContext().getResources()
//                                            .getColor(R.color.color_text_green_dark));
//        } else if(status.equals(CANCELADA.getValue())){
//            holder.txtStatus.setTextColor(mLayoutInflater.getContext().getResources()
//                    .getColor(R.color.color_text_red_dark));
//        } else if(status.equals(REALIZADA.getValue())){
//            holder.txtStatus.setTextColor(mLayoutInflater.getContext().getResources()
//                    .getColor(R.color.color_text_purple));
//        } else {
//            holder.txtStatus.setTextColor(mLayoutInflater.getContext().getResources()
//                    .getColor(R.color.color_text_orange_dark));
//        }
//
//        if(todayDate.compareToIgnoreCase(date) > 0){
//            holder.txtDoctor.setTextColor(mLayoutInflater.getContext().getResources()
//                            .getColor(R.color.color_text_off));
//            holder.txtDate.setTextColor(mLayoutInflater.getContext().getResources()
//                    .getColor(R.color.color_text_off));
//            holder.txtSchedule.setTextColor(mLayoutInflater.getContext().getResources()
//                    .getColor(R.color.color_text_off));
//        }
//    }
//
//    /**
//     * Classe que personaliza o @see {@link RecyclerView.ViewHolder}
//     */
//
//    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
//        public TextView txtDoctor, txtDate, txtStatus, txtSchedule;
//
//        public MyViewHolder(final View itemView) {
//            super(itemView);
//
//            txtDoctor = itemView.findViewById(R.id.txtDoctor);
//            txtDate     = itemView.findViewById(R.id.txtDate);
//            txtStatus   = itemView.findViewById(R.id.txtStatus);
//            txtSchedule = itemView.findViewById(R.id.txtSchedule);
//
//            itemView.setOnClickListener(this);
//            itemView.setOnLongClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            if(mRecyclerViewOnClickListenerhack != null){
//                mRecyclerViewOnClickListenerhack.onClickListener(v, getPosition());
//            }
//        }
//
//        @Override
//        public boolean onLongClick(View v) {
//            if(mRecyclerViewOnClickListenerhack != null){
//                mRecyclerViewOnClickListenerhack.onLongPressClickListener(v, getPosition());
//                return true;
//            }
//            return false;
//        }
//    }
//}
