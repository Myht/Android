package com.example.Myht;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
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
	// 网路操作类
	public NetWorkUtils netWorkUtils;
	private Handler handler;
	public String content;
	public static final int TEXT = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取et_url对象
		et_url = (EditText) findViewById(R.id.et_url);
		tv_ie = (TextView) findViewById(R.id.tv_ie);
		bt1 = (Button) findViewById(R.id.btn1);

//		 直接存储为txt格式文本(保存路径为:/storage/sdcard/html2.txt)
		bt1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String f1 = Environment.getExternalStorageDirectory()
						+ File.separator + "html2.txt";
				File file = new File(f1);
				System.out.println(file);
				try {
					if (!file.exists()) {
						file.createNewFile();
					}
					FileWriter pw = new FileWriter(file, true);
					pw.write(content);
					pw.flush();
					pw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		 //下面是运用sqlite数据库，把网页存储到sqlite里去
//		 bt1.setOnClickListener(new OnClickListener() {
//		
//		 @Override
//		 public void onClick(View arg0) {
//		 // TODO Auto-generated method stub
//		 CreateListener();
//		 InsertListener();
//		 QueryListener();
//		 }
//		
//		 // 插入数据的方法
//		 private void InsertListener() {
//		 // TODO Auto-generated method stub
//		 StuDBHelper dbHelper = new StuDBHelper(MainActivity.this,
//		 "stu_db", null, 1);
//		 // 得到一个可写的数据库
//		 SQLiteDatabase db = dbHelper.getWritableDatabase();
//		
//		 // 生成ContentValues对象 //key:列名，value:想插入的值
//		 ContentValues cv = new ContentValues();
//		 // 往ContentValues对象存放数据，键-值对模式
//		 cv.put("id", 1);
//		 cv.put("info", content);
//		 // 调用insert方法，将数据插入数据库
//		 db.insert("stu_table", null, cv);
//		 // 关闭数据库
//		 db.close();
//		 }
//		
//		 // 创建数据库的方法
//		 private void CreateListener() {
//		 // TODO Auto-generated method stub
//		 // 创建StuDBHelper对象
//		 StuDBHelper dbHelper = new StuDBHelper(MainActivity.this,
//		 "stu_db", null, 1);
//		 // 得到一个可读的SQLiteDatabase对象
//		 SQLiteDatabase db = dbHelper.getReadableDatabase();
//		
//		 }
//		// 查询数据的方法
//				 private void QueryListener() {
//				 // TODO Auto-generated method stub
//				 StuDBHelper dbHelper = new StuDBHelper(MainActivity.this,
//				 "stu_db", null, 1);
//				 // 得到一个可写的数据库
//				 SQLiteDatabase db = dbHelper.getReadableDatabase();
//				 Cursor cursor = db.query("stu_table", new String[] { "id",
//				 "info" }, "id=?", new String[] { "1" }, null, null,
//				 null);
//				 while (cursor.moveToNext()) {
//				 String info = cursor.getString(cursor
//				 .getColumnIndex("info"));
//				 Toast.makeText(MainActivity.this, info,Toast.LENGTH_LONG).show();
////				 tv_ie.setText(info);
//				 }
//				 // 关闭数据库
//				 db.close();
//				 }
//		 });
		// 实例化
		netWorkUtils = new NetWorkUtils(this);


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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}