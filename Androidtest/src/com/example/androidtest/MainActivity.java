package com.example.androidtest;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener  
	{  
	  
	    private ContentFragment mWeixin;  
	    private FriendFragment mFriend; 
	    private TabFirst mList;
	    
	    // ����ײ�����������������
		private RelativeLayout chat_layout;
		private RelativeLayout address_layout;
		private RelativeLayout me_layout;
		// ����ײ��������е�ImageView��TextView
		private ImageView chat_image;
		private ImageView address_image;
		private ImageView me_image;
		private TextView chat_text;
		private TextView address_text;
		private TextView me_text;
		// ����Ҫ�õ���ɫֵ
		private int whirt = 0xFFFFFFFF;
		private int gray = 0xFF7597B3;
		private int blue = 0xFF0AB2FB;

		// ����FragmentManager����
		FragmentManager fManager;
	  
	    @Override  
	    protected void onCreate(Bundle savedInstanceState)  
	    {  
	    	super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			//fManager = getSupportFragmentManager();
			initViews(); 
	  
	        // ��ʼ���ؼ��������¼�  
	        //mTabWeixin = (Button) findViewById(R.id.tab_bottom_weixin);  
	        //mTabFriend = (Button) findViewById(R.id.tab_bottom_friend); 
	        //mTabList=(Button) findViewById(R.id.tab_bottom_list);
	        //mTabWeixin.setOnClickListener(this);  
	        //mTabFriend.setOnClickListener(this); 
	        //mTabList.setOnClickListener(this); 
	  
	        // ����Ĭ�ϵ�Fragment  
	        setDefaultFragment();  
	    }  
	    
	 // �������ĳ�ʼ��
		public void initViews() {
			chat_image = (ImageView) findViewById(R.id.chat_image);
			address_image = (ImageView) findViewById(R.id.address_image);
			me_image = (ImageView) findViewById(R.id.me_image);
			chat_text = (TextView) findViewById(R.id.chat_text);
			address_text = (TextView) findViewById(R.id.address_text);
			me_text = (TextView) findViewById(R.id.me_text);
			chat_layout = (RelativeLayout) findViewById(R.id.chat_layout);
			address_layout = (RelativeLayout) findViewById(R.id.address_layout);
			me_layout = (RelativeLayout) findViewById(R.id.me_layout);
			chat_layout.setOnClickListener(this);
			address_layout.setOnClickListener(this);
			me_layout.setOnClickListener(this);
		}

	  
	    private void setDefaultFragment()  
	    {  
	        FragmentManager fm = getFragmentManager();  
	        FragmentTransaction transaction = fm.beginTransaction();  
	        mList = new TabFirst();  
	        transaction.replace(R.id.id_content, mList);  
	        transaction.commit();  
	    } 
	  
	   
	   public void onClick(View v)  
	    {  
	        FragmentManager fm = getFragmentManager();  
	        // ����Fragment����  
	        FragmentTransaction transaction = fm.beginTransaction();  
	  
	        switch (v.getId())  
	        {  
	        case R.id.chat_layout:  
	            if (mWeixin == null)  
	            {  
	                mWeixin = new ContentFragment();  
	            }  
	            // ʹ�õ�ǰFragment�Ĳ������id_content�Ŀؼ�  
	            transaction.replace(R.id.id_content, mWeixin);  
	            break;  
	        case R.id.me_layout:  
	            if (mFriend == null)  
	            {  
	                mFriend = new FriendFragment();  
	            }  
	            transaction.replace(R.id.id_content, mFriend);  
	            break;  
	        case R.id.address_layout:
	        	 if (mList == null)  
		            {  
	        		 mList = new TabFirst();  
		            }  
		            transaction.replace(R.id.id_content, mList); 
	        	break;
	        }  
	        // transaction.addToBackStack();  
	        // �����ύ  
	        transaction.commit();  
	    }  
	  
	}  