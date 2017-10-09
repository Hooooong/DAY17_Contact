package com.hooooong.contact;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hooooong.contact.model.Contact;

import java.util.List;

/**
 * Created by Android Hong on 2017-09-26.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.Holder> {

    List<Contact> contactList;

    public MainAdapter() {
    }

    public void addData(List<Contact> contactList){
        this.contactList = contactList;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setTextId(contactList.get(position).getId());
        holder.setTextName(contactList.get(position).getName());
        holder.setTextPhoneNumber(contactList.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView textId, textName, textPhoneNumber;
        private ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            initView(itemView);
            imageView.setOnClickListener(new View.OnClickListener() {
                /**
                 * LINT 구문 오류를 컴파일러에게 체크하지 마라고 알려주는 것
                 *
                 * @param v
                 */
                @SuppressWarnings("MissingPermission")
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + textPhoneNumber.getText().toString()));
                    v.getContext().startActivity(intent);
                }
            });
        }

        public void setTextId(int id) {
            textId.setText(id+"");
        }

        public void setTextName(String name) {
            textName.setText(name);
        }

        public void setTextPhoneNumber(String phone) {
            textPhoneNumber.setText(phone);
        }

        public void initView(View initView){
            textId = (TextView) itemView.findViewById(R.id.textId);
            textName = (TextView) itemView.findViewById(R.id.textName);
            textPhoneNumber = (TextView) itemView.findViewById(R.id.textPhoneNumber);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
