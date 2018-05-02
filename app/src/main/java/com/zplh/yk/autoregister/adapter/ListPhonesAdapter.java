package com.zplh.yk.autoregister.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zplh.yk.autoregister.R;
import com.zplh.yk.autoregister.bean.PhoneBean;

import java.util.List;

/**
 * Created by yong hao zeng on 2018/4/27/027.
 */
public class ListPhonesAdapter extends BaseAdapter {
    List<PhoneBean> datas;
    ClickListener clickListener;

    public ListPhonesAdapter(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public ListPhonesAdapter setDatas(List<PhoneBean> datas) {
        this.datas = datas;
        return this;
    }

    @Override
    public int getCount() {
        return datas!=null?datas.size():0;
    }

    @Override
    public Object getItem(int position) {
        return new Object();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null);
        PhoneBean phoneBean = datas.get(position);
        switch (phoneBean.getData().getState()) {
            case 1://通过
                 inflate.findViewById(R.id.tv_bt).setVisibility(View.GONE);
                ((TextView) inflate.findViewById(R.id.tv_state)).setText("注册成功");
                 break;
            case 2:// 未执行

                break;
            case 3:// 错误
                inflate.findViewById(R.id.tv_bt).setVisibility(View.VISIBLE);
                inflate.setOnClickListener(v -> {
                    clickListener.onClick(phoneBean,position);
                });
                ((TextView) inflate.findViewById(R.id.tv_state)).setText(phoneBean.getMsg());
                break;
        }

        return inflate;
    }

    public interface ClickListener{
        void onClick(PhoneBean phoneBean,int position);
    }


}
