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
	    
	    // 定义底部导航栏的三个布局
		private RelativeLayout chat_layout;
		private RelativeLayout address_layout;
		private RelativeLayout me_layout;
		// 定义底部导航栏中的ImageView与TextView
		private ImageView chat_image;
		private ImageView address_image;
		private ImageView me_image;
		private TextView chat_text;
		private TextView address_text;
		private TextView me_text;
		// 定义要用的颜色值
		private int whirt = 0xFFFFFFFF;
		private int gray = 0xFF7597B3;
		private int blue = 0xFF0AB2FB;

		// 定义FragmentManager对象
		FragmentManager fManager;
	  
	    @Override  
	    protected void onCreate(Bundle savedInstanceState)  
	    {  
	    	super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			//fManager = getSupportFragmentManager();
			initViews(); 
	  
	        // 初始化控件和声明事件  
	        //mTabWeixin = (Button) findViewById(R.id.tab_bottom_weixin);  
	        //mTabFriend = (Button) findViewById(R.id.tab_bottom_friend); 
	        //mTabList=(Button) findViewById(R.id.tab_bottom_list);
	        //mTabWeixin.setOnClickListener(this);  
	        //mTabFriend.setOnClickListener(this); 
	        //mTabList.setOnClickListener(this); 
	  
	        // 设置默认的Fragment  
	        setDefaultFragment();  
	    }  
	    
	 // 完成组件的初始化
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
	        // 开启Fragment事务  
	        FragmentTransaction transaction = fm.beginTransaction();  
	  
	        switch (v.getId())  
	        {  
	        case R.id.chat_layout:  
	            if (mWeixin == null)  
	            {  
	                mWeixin = new ContentFragment();  
	            }  
	            // 使用当前Fragment的布局替代id_content的控件  
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
	        // 事务提交  
	        transaction.commit();  
	    }  
	  
	}  