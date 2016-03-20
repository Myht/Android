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

	// 声明控件 
	public EditText et_url; 
	public TextView tv_ie; 
	public Button bt1;
	public Button bt2;
	// 网路操作类 
	public NetWorkUtils netWorkUtils; 
	private Handler handler; 
	public String content; 
	public static final int TEXT = 1; 
	//数据库
	@Override 
	protected void onCreate(Bundle savedInstanceState) { 
	super.onCreate(savedInstanceState); 
	setContentView(R.layout.activity_main); 
	// 获取et_url对象 
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

		//插入数据的方法
		private void InsertListener() {
			// TODO Auto-generated method stub
			StuDBHelper dbHelper = new StuDBHelper(MainActivity.this,"stu_db",null,1);  
			//得到一个可写的数据库  
			SQLiteDatabase db =dbHelper.getWritableDatabase();  
			  
			//生成ContentValues对象 //key:列名，value:想插入的值   
			ContentValues cv = new ContentValues();  
			//往ContentValues对象存放数据，键-值对模式  
			cv.put("id", 1);  
			cv.put("info", content);  
			//调用insert方法，将数据插入数据库  
			db.insert("stu_table", null, cv);  
			//关闭数据库  
			db.close();  
		}

		//创建数据库的方法  
		private void CreateListener() {
			// TODO Auto-generated method stub
			//创建StuDBHelper对象  
			StuDBHelper dbHelper = new StuDBHelper(MainActivity.this,"stu_db",null,1);  
			//得到一个可读的SQLiteDatabase对象  
			SQLiteDatabase db =dbHelper.getReadableDatabase();  
			
		}
	});
	bt2.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			QueryListener();
		}

		//查询数据的方法  
		private void QueryListener() {
			// TODO Auto-generated method stub
			StuDBHelper dbHelper = new StuDBHelper(MainActivity.this,"stu_db",null,1);  
			//得到一个可写的数据库  
			SQLiteDatabase db =dbHelper.getReadableDatabase();  
			//参数1：表名   
			//参数2：要想显示的列   
			//参数3：where子句   
			//参数4：where子句对应的条件值   
			//参数5：分组方式   
			//参数6：having条件   
			//参数7：排序方式   
			Cursor cursor = db.query("stu_table", new String[]{"id","info"}, "id=?", new String[]{"1"}, null, null, null);  
			while(cursor.moveToNext()){  
			String info = cursor.getString(cursor.getColumnIndex("info"));    
			tv_ie.setText(info);  
			}  
			//关闭数据库  
			db.close();  
		}
	});
	// 实例化 
	netWorkUtils = new NetWorkUtils(this); 
	
	  
	//更新数据库的方法  
	class UpdateListener implements OnClickListener{  
	  
	@Override  
	public void onClick(View v) {  
	// 数据库版本的更新,由原来的1变为2  
	StuDBHelper dbHelper = new StuDBHelper(MainActivity.this,"stu_db",null,2);  
	SQLiteDatabase db =dbHelper.getReadableDatabase();  
	}   
	}  
	  
	    
	  
	
	
	// 实例化这个处理者 
	handler = new Handler() { 
	public void handleMessage(Message msg) { 
	super.handleMessage(msg); 
	switch (msg.what) { 
	case TEXT: 
	tv_ie.setText(content);// 设置显示的文本 
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
	// 设置网络 
	boolean flag = netWorkUtils.setActiveNetWork(); 
	if (flag) { 
	// run方法 执行完毕 这个线程就消耗了 
	// 子线程 
	new Thread(new Runnable() { 
	@Override 
	public void run() { 
	send(); 
	handler.sendEmptyMessage(TEXT); 
	} 
	}).start(); 
	} 
	} 
	// 发送请求操作 
	@SuppressLint("NewApi") 
	public void send() { 

	// 获取url的path路径 
	String path = et_url.getText().toString(); 
	if (path.isEmpty()) { 
	Toast.makeText(MainActivity.this, "访问 的url地址不能为空", 
	Toast.LENGTH_LONG).show(); 
	} else { 
	content = HttpUtils.sendGet(path); 
	} 



	/*// 设置网络 
	netWorkUtils.setActiveNetWork(); 
	// 获取url的path路径 
	String path = et_url.getText().toString(); 
	if (path.isEmpty()) { 
	Toast.makeText(MainActivity.this, "访问 的url地址不能为空", 
	Toast.LENGTH_LONG).show(); 
	} else { 
	try { 
	// 设置访问的url 
	URL url = new URL(path); 
	// 打开请求 
	HttpURLConnection httpURLConnection = (HttpURLConnection) url 
	.openConnection(); 
	// 设置请求的信息 
	httpURLConnection.setRequestMethod("GET"); 
	// 判断服务器是否响应成功 
	if (httpURLConnection.getResponseCode() == 200) { 
	// 获取响应的输入流对象 
	InputStream is = httpURLConnection.getInputStream(); 
	// 字节输出流 
	ByteArrayOutputStream bops = new ByteArrayOutputStream(); 
	// 读取数据的缓存区 
	byte buffer[] = new byte[1024]; 
	// 读取长度记录 
	int len = 0; 
	// 循环读取 
	while ((len = is.read(buffer)) != -1) { 
	bops.write(buffer, 0, len); 
	} 
	// 把读取的内容转换成byte数组 
	byte data[] = bops.toByteArray(); 
	// 把转换成字符串 
	content = new String(data); 
	} else { 
	Toast.makeText(MainActivity.this, "服务器响应错误", 
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
