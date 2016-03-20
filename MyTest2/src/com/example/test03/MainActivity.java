package com.example.test03;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	// �����ؼ� 
	public EditText et_url; 
	public TextView tv_ie; 
	public Button bt1;
	public Button bt2;
	// ��·������ 
	public NetWorkUtils netWorkUtils; 
	private Handler handler; 
	public String content; 
	public static final int TEXT = 1; 
	//���ݿ�
	@Override 
	protected void onCreate(Bundle savedInstanceState) { 
	super.onCreate(savedInstanceState); 
	setContentView(R.layout.activity_main); 
	// ��ȡet_url���� 
	et_url = (EditText) findViewById(R.id.et_url); 
	tv_ie = (TextView) findViewById(R.id.tv_ie); 
	bt1=(Button)findViewById(R.id.btn1);
	bt2=(Button)findViewById(R.id.btn2);
	bt1.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			CreateListener();
			InsertListener();
		}

		//�������ݵķ���
		private void InsertListener() {
			// TODO Auto-generated method stub
			StuDBHelper dbHelper = new StuDBHelper(MainActivity.this,"stu_db",null,1);  
			//�õ�һ����д�����ݿ�  
			SQLiteDatabase db =dbHelper.getWritableDatabase();  
			  
			//����ContentValues���� //key:������value:������ֵ   
			ContentValues cv = new ContentValues();  
			//��ContentValues���������ݣ���-ֵ��ģʽ  
			cv.put("id", 1);  
			cv.put("info", content);  
			//����insert�����������ݲ������ݿ�  
			db.insert("stu_table", null, cv);  
			//�ر����ݿ�  
			db.close();  
		}

		//�������ݿ�ķ���  
		private void CreateListener() {
			// TODO Auto-generated method stub
			//����StuDBHelper����  
			StuDBHelper dbHelper = new StuDBHelper(MainActivity.this,"stu_db",null,1);  
			//�õ�һ���ɶ���SQLiteDatabase����  
			SQLiteDatabase db =dbHelper.getReadableDatabase();  
			
		}
	});
	bt2.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			QueryListener();
		}

		//��ѯ���ݵķ���  
		private void QueryListener() {
			// TODO Auto-generated method stub
			StuDBHelper dbHelper = new StuDBHelper(MainActivity.this,"stu_db",null,1);  
			//�õ�һ����д�����ݿ�  
			SQLiteDatabase db =dbHelper.getReadableDatabase();  
			//����1������   
			//����2��Ҫ����ʾ����   
			//����3��where�Ӿ�   
			//����4��where�Ӿ��Ӧ������ֵ   
			//����5�����鷽ʽ   
			//����6��having����   
			//����7������ʽ   
			Cursor cursor = db.query("stu_table", new String[]{"id","info"}, "id=?", new String[]{"1"}, null, null, null);  
			while(cursor.moveToNext()){  
			String info = cursor.getString(cursor.getColumnIndex("info"));    
			tv_ie.setText(info);  
			}  
			//�ر����ݿ�  
			db.close();  
		}
	});
	// ʵ���� 
	netWorkUtils = new NetWorkUtils(this); 
	
	  
	//�������ݿ�ķ���  
	class UpdateListener implements OnClickListener{  
	  
	@Override  
	public void onClick(View v) {  
	// ���ݿ�汾�ĸ���,��ԭ����1��Ϊ2  
	StuDBHelper dbHelper = new StuDBHelper(MainActivity.this,"stu_db",null,2);  
	SQLiteDatabase db =dbHelper.getReadableDatabase();  
	}   
	}  
	  
	    
	  
	
	
	// ʵ������������� 
	handler = new Handler() { 
	public void handleMessage(Message msg) { 
	super.handleMessage(msg); 
	switch (msg.what) { 
	case TEXT: 
	tv_ie.setText(content);// ������ʾ���ı� 
	break; 
	default: 
	break; 
	} 
	} 
	}; 
	} 
	@Override 
	public boolean onCreateOptionsMenu(Menu menu) { 
	// Inflate the menu; this adds items to the action bar if it is present. 
	getMenuInflater().inflate(R.menu.main, menu); 
	return true; 
	} 
	public void sendHttp(View v) { 
	// �������� 
	boolean flag = netWorkUtils.setActiveNetWork(); 
	if (flag) { 
	// run���� ִ����� ����߳̾������� 
	// ���߳� 
	new Thread(new Runnable() { 
	@Override 
	public void run() { 
	send(); 
	handler.sendEmptyMessage(TEXT); 
	} 
	}).start(); 
	} 
	} 
	// ����������� 
	@SuppressLint("NewApi") 
	public void send() { 

	// ��ȡurl��path·�� 
	String path = et_url.getText().toString(); 
	if (path.isEmpty()) { 
	Toast.makeText(MainActivity.this, "���� ��url��ַ����Ϊ��", 
	Toast.LENGTH_LONG).show(); 
	} else { 
	content = HttpUtils.sendGet(path); 
	} 



	/*// �������� 
	netWorkUtils.setActiveNetWork(); 
	// ��ȡurl��path·�� 
	String path = et_url.getText().toString(); 
	if (path.isEmpty()) { 
	Toast.makeText(MainActivity.this, "���� ��url��ַ����Ϊ��", 
	Toast.LENGTH_LONG).show(); 
	} else { 
	try { 
	// ���÷��ʵ�url 
	URL url = new URL(path); 
	// ������ 
	HttpURLConnection httpURLConnection = (HttpURLConnection) url 
	.openConnection(); 
	// �����������Ϣ 
	httpURLConnection.setRequestMethod("GET"); 
	// �жϷ������Ƿ���Ӧ�ɹ� 
	if (httpURLConnection.getResponseCode() == 200) { 
	// ��ȡ��Ӧ������������ 
	InputStream is = httpURLConnection.getInputStream(); 
	// �ֽ������ 
	ByteArrayOutputStream bops = new ByteArrayOutputStream(); 
	// ��ȡ���ݵĻ����� 
	byte buffer[] = new byte[1024]; 
	// ��ȡ���ȼ�¼ 
	int len = 0; 
	// ѭ����ȡ 
	while ((len = is.read(buffer)) != -1) { 
	bops.write(buffer, 0, len); 
	} 
	// �Ѷ�ȡ������ת����byte���� 
	byte data[] = bops.toByteArray(); 
	// ��ת�����ַ��� 
	content = new String(data); 
	} else { 
	Toast.makeText(MainActivity.this, "��������Ӧ����", 
	Toast.LENGTH_LONG).show(); 
	} 
	} catch (MalformedURLException e) { 
	e.printStackTrace(); 
	} catch (IOException e) { 
	e.printStackTrace(); 
	} 
	}*/ 
	} 

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
